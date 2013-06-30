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
    private String trainPath;
    private List<Double[]> testDataList = new ArrayList<>();
    private List<Double[]> trainDataList = new ArrayList<>();
    private Integer neuronsInHidden;

    @Override
    protected void doTask() {
        int training = 0;
        while ((training++) < epochs) {
            LOGGER.info(String.format("Epoch %d >>> computing...", training - 1));
            final Long startTime = System.nanoTime();
            Double error = 0.0;
            for (final Double[] data : this.trainDataList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.process();
                this.network.teach();
            }
            for (Double[] data : this.testDataList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.process();
                error += network.computeError();
            }
            error = error / this.testDataList.size();
            this.errors.add(error);
            final Long endTime = System.nanoTime() - startTime;
            LOGGER.info(String.format("Epoch %d >>> computing finished, time=%dms, error=[%.3f%% / %.15f]", training - 1, TimeUnit.NANOSECONDS.toMillis(endTime), (error * 100.0), error));
        }
        for (Double[] data : this.testDataList) {
            network.setSignal(data[0]);
            network.process();
            result.add(new Pair<>(data[0], network.getOutput()[0]));
        }
    }

    @Override
    public void buildNetwork() {
        final Double lower = this.range.getKey();
        final Double higher = this.range.getValue();
        final double biasWeight = 1.0;

        final OutputNeuralLayer outputLayer = this.getOutputNeuralLayer(lower, higher, biasWeight);
        final NeuralLayer hiddenLayer = this.getHiddenLayer(lower, higher, biasWeight);
        final NeuralLayer inputLayer = this.getInputLayer(lower, higher, biasWeight);

        inputLayer.setUpperLayer(hiddenLayer);
        hiddenLayer.setUpperLayer(outputLayer);

        this.network = new NeuralNetwork(1, inputLayer, hiddenLayer, outputLayer);
    }

    @Override
    public void readData() throws FileNotFoundException {
        File dir = new File(this.dataDir);
        if (dir.isDirectory()) {
            File testSet = new File(String.format("%s/%s", this.dataDir, this.testDataPath));
            File dataSet1 = new File(String.format("%s/%s", this.dataDir, this.trainPath));

            LOGGER.info(String.format("Data files [%s, %s]", testSet.getName(), dataSet1.getName()));

            Scanner scanner = new Scanner(testSet);
            while (scanner.hasNext()) {
                testDataList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

            scanner = new Scanner(dataSet1);
            while (scanner.hasNext()) {
                trainDataList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
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
        this.trainPath = this.properties.getProperty("approx.train");
    }

    private OutputNeuralLayer getOutputNeuralLayer(final Double lower, final Double higher, final double biasWeight) {
        return new OutputNeuralLayer(NeuralFactory.Neurons.newBiasNeuron(
                this.functions[2],
                this.momentumRate,
                this.learningFactor,
                biasWeight,
                ArraysUtils.newRandomDoubleArray(this.neuronsInHidden, lower, higher)));
    }

    private NeuralLayer getHiddenLayer(final Double lower, final Double higher, final double biasWeight) {
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
        return new NeuralLayer(hiddenNeurons.toArray(new Neuron[hiddenNeurons.size()]));
    }

    private NeuralLayer getInputLayer(final Double lower, final Double higher, final double biasWeight) {
        return new NeuralLayer(NeuralFactory.Neurons.newBiasNeuron(
                this.functions[0],
                this.momentumRate,
                this.learningFactor,
                biasWeight,
                ArraysUtils.newRandomDoubleArray(1, lower, higher)));
    }
}
