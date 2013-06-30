package org.kornicameister.iad.task.impl;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.networks.ClassificationNeuralNetwork;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;
import org.kornicameister.iad.task.DefaultTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ClassificationTask extends DefaultTask {
    public static final String SPACE = " ";
    private static final Logger LOGGER = Logger.getLogger(ClassificationTask.class);
    private String testDataPath;
    private String trainPath;
    private String classificationErrorPath;
    private Integer inputsCount;
    private Integer outputsCount;
    private List<DataChunk> testDataList = new LinkedList<>();
    private List<DataChunk> trainDataList = new LinkedList<>();
    private Integer[] columns;
    private List<Double> classificationError = new LinkedList<>();

    @Override
    protected void saveResult() throws FileNotFoundException {
        super.saveResult();
        this.saveClassificationError();
    }

    @Override
    protected void doTask() {
        final ClassificationNeuralNetwork neuralNetwork = (ClassificationNeuralNetwork) this.network;
        Integer training = 0;
        while (training <= epochs) {
            final Long startTime = System.nanoTime();
            Double error = 0.0;
            Double validClassificationsError = 0d;

            for (final DataChunk data : this.trainDataList) {
                neuralNetwork.setSignal(data.getSignal());
                neuralNetwork.setExpectedOutput(data.getOutput());
                neuralNetwork.process();
                neuralNetwork.learn();
            }
            for (final DataChunk data : this.testDataList) {
                neuralNetwork.setSignal(data.getSignal());
                neuralNetwork.setExpectedOutput(data.getOutput());
                neuralNetwork.process();
                error += network.computeError();

                if (neuralNetwork.isClassificationValid()) {
                    validClassificationsError++;
                }
            }

            final JoinedError joinedError = this.persistErrors(error, validClassificationsError);
            error = joinedError.getError();
            validClassificationsError = joinedError.getClassificationError();

            if (training % 1000 == 0) {
                final Long endTime = System.nanoTime() - startTime;
                LOGGER.info(String.format("Epoch %d >>> computing finished\n\t>>\ttime=%dms\n\t>>\terror=[%.3f%% / %.15f]\n\t>>\tclassificationHealth=[%.3f%%]", training, TimeUnit.NANOSECONDS.toMillis(endTime), (error * 100.0), error, validClassificationsError));
            }
            training += 1;
        }
        for (final DataChunk data : this.testDataList) {
            neuralNetwork.setSignal(data.getSignal());
            neuralNetwork.process();
            result.add(new Pair<>(data.getKey().doubleValue(), neuralNetwork.getOutput().clone()));
        }
    }

    @Override
    protected void buildNetwork() {
        final Double lower = this.range.getKey();
        final Double higher = this.range.getValue();
        final double biasWeight = 1.0;

        final NeuralLayer outputLayer = this.getLayer(lower, higher, biasWeight, this.outputsCount, this.neurons[1], 2, true);
        final NeuralLayer hiddenLayer = this.getLayer(lower, higher, biasWeight, this.neurons[1], 1, 1, false);
        final NeuralLayer inputLayer = this.getLayer(lower, higher, biasWeight, 1, this.inputsCount, 0, false);

        inputLayer.setUpperLayer(hiddenLayer);
        hiddenLayer.setUpperLayer(outputLayer);

        this.network = new ClassificationNeuralNetwork(this.outputsCount, inputLayer, hiddenLayer, outputLayer);
    }

    @Override
    protected void readData() throws FileNotFoundException {
        File dir = new File(this.dataDir);
        if (dir.isDirectory()) {
            final File testSet = new File(String.format("%s/%s", this.dataDir, this.testDataPath));
            final File dataSet1 = new File(String.format("%s/%s", this.dataDir, this.trainPath));
            String[] line;

            LOGGER.info(String.format("Data files [%s, %s]", testSet.getName(), dataSet1.getName()));

            Scanner scanner = new Scanner(testSet);
            while (scanner.hasNext()) {
                line = scanner.nextLine().split(SPACE);
                this.testDataList.add(new DataChunk(line, this.columns, this.outputsCount));
            }
            scanner.close();

            scanner = new Scanner(dataSet1);
            while (scanner.hasNext()) {
                line = scanner.nextLine().split(SPACE);
                this.trainDataList.add(new DataChunk(line, this.columns, this.outputsCount));
            }
            scanner.close();

        } else {
            LOGGER.fatal(String.format("%s is not directory", dir));
        }
    }

    @Override
    protected void load(final String propertiesPath) throws IOException {
        super.load(propertiesPath);
        this.testDataPath = this.properties.getProperty("classification.test");
        this.trainPath = this.properties.getProperty("classification.train");
        this.inputsCount = Integer.valueOf(this.properties.getProperty("classification.inputs"));
        this.outputsCount = Integer.valueOf(this.properties.getProperty("classification.outputs"));
        this.classificationErrorPath = this.properties.getProperty("classification.out.err");
        this.columns = this.readColumnsFromProperties();
    }

    private Integer[] readColumnsFromProperties() {
        final String[] columnsSplit = this.properties.getProperty("classification.columns").split(",");
        final Integer[] columns = new Integer[columnsSplit.length];
        for (int i = 0, columnsSplitLength = columnsSplit.length; i < columnsSplitLength; i++) {
            final String col = columnsSplit[i];
            columns[i] = Integer.valueOf(col);
        }
        return columns;
    }

    private JoinedError persistErrors(final Double error, final Double validClassificationsError) {
        final double errorComputed = error / this.testDataList.size();
        final double validClassificationErrorComputed = (validClassificationsError / this.testDataList.size()) * 100.0;
        this.errors.add(errorComputed);
        this.classificationError.add(validClassificationErrorComputed);
        return new JoinedError(errorComputed, validClassificationErrorComputed);
    }

    private void saveClassificationError() throws FileNotFoundException {
        final PrintWriter errWriter = new PrintWriter(new File(String.format("%s/%s", this.dataDir, this.classificationErrorPath)));
        int it = 0;
        for (Double err : this.classificationError) {
            errWriter.print(it++);
            errWriter.print(" ");
            errWriter.print(err);
            errWriter.println();
        }
        errWriter.flush();
        errWriter.close();
    }

    private class DataChunk {
        Integer key;
        Double[] signal;
        Double[] output;

        public DataChunk(final String[] line, final Integer[] columns, final Integer outputs) {
            this.key = this.parseKey(line);
            this.signal = this.parseSignal(line, columns);
            this.output = this.parseOutput(outputs);
        }

        private Double[] parseOutput(final Integer outputs) {
            final Double[] output = ArraysUtils.newDoubleArray(outputs);
            output[this.key - 1] = 1.0;
            return output;
        }

        private Double[] parseSignal(final String[] line, final Integer[] columns) {
            final Double[] data = new Double[columns.length];
            for (int i = 0; i < data.length; i++) {
                data[i] = Double.valueOf(line[columns[i]]);
            }
            return data;
        }

        private Integer parseKey(final String[] line) {
            return Integer.valueOf(line[line.length - 1]);
        }

        private Integer getKey() {
            return key;
        }

        private Double[] getSignal() {
            return signal;
        }

        private Double[] getOutput() {
            return output;
        }

        @Override
        public String toString() {
            return "DataChunk{" +
                    "key=" + key +
                    ", signal=" + Arrays.toString(signal) +
                    ", output=" + Arrays.toString(output) +
                    "} " + super.toString();
        }
    }

    private class JoinedError {
        private final double error;
        private final double classificationError;

        public JoinedError(final double errorComputed, final double validClassificationErrorComputed) {
            this.error = errorComputed;
            this.classificationError = validClassificationErrorComputed;
        }

        private double getError() {
            return error;
        }

        private double getClassificationError() {
            return classificationError;
        }

        @Override
        public String toString() {
            return "JoinedError{" +
                    "error=" + error +
                    ", classificationError=" + classificationError +
                    "} " + super.toString();
        }
    }
}
