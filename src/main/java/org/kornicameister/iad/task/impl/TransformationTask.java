package org.kornicameister.iad.task.impl;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;
import org.kornicameister.iad.task.DefaultTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TransformationTask extends DefaultTask {
    private static final Logger LOGGER = Logger.getLogger(TransformationTask.class);
    private String dataPath;
    private List<Double[]> dataList = new LinkedList<>();

    @Override
    protected void doTask() {
        int training = 0;
        while (training < epochs) {
            final Long startTime = System.nanoTime();
            Double error = 0.0;
            for (final Double[] data : this.dataList) {
                this.network.setSignal(data);
                this.network.setExpectedOutput(data);
                this.network.process();
                this.network.learn();
            }
            for (Double[] data : this.dataList) {
                this.network.setSignal(data);
                this.network.setExpectedOutput(data);
                this.network.process();
                error += network.computeError();
            }
            error = error / this.dataList.size();
            this.errors.add(error);
            if (training % 100 == 0) {
                final Long endTime = System.nanoTime() - startTime;
                LOGGER.info(String.format("Epoch %d >>> computing finished, time=%dms, error=[%.3f%% / %.15f]", training, TimeUnit.NANOSECONDS.toMillis(endTime), (error * 100.0), error));
            }
            training += 1;
        }
        for (Double[] data : this.dataList) {
            this.network.setSignal(data);
            this.network.process();
            final List<Double> clone = new ArrayList<>(Arrays.asList(this.network.getOutput().clone()));
            final Double tmp = clone.remove(0);
            this.result.add(new Pair<>(tmp, clone.toArray(new Double[clone.size()])));
        }
    }

    @Override
    protected void buildNetwork() {
        final Double lower = this.range.getKey();
        final Double higher = this.range.getValue();
        final double biasWeight = 1.0;

        final NeuralLayer outputLayer = this.getLayer(lower, higher, biasWeight, this.neurons[2], this.neurons[1], 2, true);
        final NeuralLayer hiddenLayer = this.getLayer(lower, higher, biasWeight, this.neurons[1], this.neurons[0], 1, false);
        final NeuralLayer inputLayer = this.getLayer(lower, higher, biasWeight, this.neurons[0], 4, 0, false);

        inputLayer.setUpperLayer(hiddenLayer);
        hiddenLayer.setUpperLayer(outputLayer);

        this.network = new NeuralNetwork(4, inputLayer, hiddenLayer, outputLayer);
    }

    @Override
    protected void readData() throws FileNotFoundException {
        File dir = new File(this.dataDir);
        if (dir.isDirectory()) {
            File testSet = new File(String.format("%s/%s", this.dataDir, this.dataPath));

            LOGGER.info(String.format("Data files [%s]", testSet.getName()));

            Scanner scanner = new Scanner(testSet);
            while (scanner.hasNext()) {
                this.dataList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

        } else {
            LOGGER.fatal(String.format("%s is not directory", dir));
        }
    }

    @Override
    protected void load(final String propertiesPath) throws IOException {
        super.load(propertiesPath);
        this.dataPath = this.properties.getProperty("transformation.data");
    }
}
