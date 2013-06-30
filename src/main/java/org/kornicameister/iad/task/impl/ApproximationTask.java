package org.kornicameister.iad.task.impl;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.factory.NeuralFactory;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;
import org.kornicameister.iad.neuralnet.impl.Neuron;
import org.kornicameister.iad.neuralnet.impl.layers.OutputNeuralLayer;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;
import org.kornicameister.iad.task.DefaultTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ApproximationTask extends DefaultTask {
    private static final Logger LOGGER = Logger.getLogger(ApproximationTask.class);
    private String testDataPath;
    private String train1DataPath;
    private String train2DataPath;
    private List<Double[]> testDataList = new ArrayList<>();
    private List<Double[]> dataOneList = new ArrayList<>();
    private List<Double[]> dataTwoList = new ArrayList<>();
    private Integer neuronsInHidden;

    @Override
    protected void doTask() {
        int training = 0;
        while ((training++) < epochs) {
            LOGGER.info(String.format("Epoch %d >>> computing...", training - 1));
            final Long startTime = System.nanoTime();
            Double error = 0.0;
            for (final Double[] data : this.dataOneList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.feedForward();
                this.network.feedBackward();
            }
            for (final Double[] data : this.dataTwoList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.feedForward();
                this.network.feedBackward();
            }
            for (Double[] data : this.testDataList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.feedForward();
                error += network.computeError();
            }
            error = error / this.testDataList.size();
            this.errors.add(error);
            final Long endTime = System.nanoTime() - startTime;
            LOGGER.info(String.format("Epoch %d >>> computing finished, time=%dms, error=%f", training - 1, TimeUnit.NANOSECONDS.toMillis(endTime), error));
        }
        for (Double[] data : this.testDataList) {
            network.setSignal(data[0]);
            network.feedForward();
            result.add(new Pair<>(data[0], network.getOutput()[0]));
        }
    }

    @Override
    public void buildNetwork() {
        final Double lower = this.range.getKey();
        final Double higher = this.range.getValue();
        final double biasWeight = 1.0;
        final List<Neuron> hiddenNeurons = new LinkedList<>();


        for (int it = 0; it < this.neuronsInHidden; it++) {
            hiddenNeurons.add(NeuralFactory.Neurons.newBiasNeuron(
                    this.functions[1],
                    this.momentumRate,
                    this.learningFactor,
                    biasWeight,
                    ArraysUtils.newRandomDoubleArray(1, lower, higher))
            );
        }


        final OutputNeuralLayer outputLayer = new OutputNeuralLayer(NeuralFactory.Neurons.newBiasNeuron(
                this.functions[1],
                this.momentumRate,
                this.learningFactor,
                biasWeight,
                ArraysUtils.newRandomDoubleArray(this.neuronsInHidden, lower, higher)));
        final NeuralLayer hiddenLayer = NeuralFactory.Layers.newInputLayer(outputLayer, hiddenNeurons.toArray(new Neuron[hiddenNeurons.size()]));

        this.network = new NeuralNetwork(1, hiddenLayer, outputLayer);
    }

    @Override
    public void readData() throws FileNotFoundException {
        File dir = new File(this.dataDir);
        if (dir.isDirectory()) {
            File testSet = new File(String.format("%s/%s", this.dataDir, this.testDataPath));
            File dataSet1 = new File(String.format("%s/%s", this.dataDir, this.train1DataPath));
            File dataSet2 = new File(String.format("%s/%s", this.dataDir, this.train2DataPath));

            LOGGER.info(String.format("Data files [%s, %s, %s]", testSet.getName(), dataSet1.getName(), dataSet2.getName()));

            Scanner scanner = new Scanner(testSet);
            while (scanner.hasNext()) {
                testDataList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

            scanner = new Scanner(dataSet1);
            while (scanner.hasNext()) {
                dataOneList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

            scanner = new Scanner(dataSet2);
            while (scanner.hasNext()) {
                dataTwoList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

        } else {
            LOGGER.fatal(String.format("%s is not directory", dir));
        }
    }

    @Override
    protected void load(final String propertiesPath) throws IOException {
        super.load(propertiesPath);
        this.neuronsInHidden = this.neurons[this.neurons.length - 2];
        this.testDataPath = this.properties.getProperty("approx.test");
        this.train1DataPath = this.properties.getProperty("approx.train.1");
        this.train2DataPath = this.properties.getProperty("approx.train.2");
    }
}
