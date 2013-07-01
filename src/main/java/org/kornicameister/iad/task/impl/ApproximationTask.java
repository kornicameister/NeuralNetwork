package org.kornicameister.iad.task.impl;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.kornicameister.iad.task.DefaultTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    protected String testDataPath;
    protected String trainPath;
    protected List<Double[]> testDataList = new ArrayList<>();
    protected List<Double[]> trainDataList = new ArrayList<>();
    protected Integer neuronsInHidden;

    @Override
    protected void doTask() {
        int training = 0;
        while (training < epochs) {
            final Long startTime = System.nanoTime();
            Double error = 0.0;
            for (final Double[] data : this.trainDataList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.process();
                this.network.learn();
            }
            for (Double[] data : this.testDataList) {
                this.network.setSignal(data[0]);
                this.network.setExpectedOutput(data[1]);
                this.network.process();
                error += network.computeError();
            }
            error = error / this.testDataList.size();
            this.errors.add(error);
            if (training % 100 == 0) {
                final Long endTime = System.nanoTime() - startTime;
                LOGGER.info(String.format("Epoch %d >>> computing finished, time=%dms, error=[%.3f%% / %.15f]", training, TimeUnit.NANOSECONDS.toMillis(endTime), (error * 100.0), error));
            }
            training += 1;
        }
        for (Double[] data : this.testDataList) {
            this.network.setSignal(data[0]);
            this.network.process();
            this.result.add(new Pair<>(data[0], this.network.getOutput().clone()));
        }
    }

    @Override
    protected void readData() throws FileNotFoundException {
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
}
