package org.kornicameister.iad.task;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.joor.Reflect;
import org.kornicameister.iad.neuralnet.factory.NeuralFactory;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class DefaultTask implements Task {
    private static final Logger LOGGER = Logger.getLogger(DefaultTask.class);
    protected List<Double> errors = new LinkedList<>();
    protected List<Pair<Double, Double[]>> result = new LinkedList<>();
    protected NeuralNetwork network = null;
    protected Properties properties = null;
    protected Integer epochs;
    protected Integer layers;
    protected Integer[] neurons;
    protected Function[] functions;
    protected Pair<Double, Double> range;
    protected Double learningFactor;
    protected Double momentumRate;
    protected String dataDir;
    protected String outPath;
    protected String errPath;
    protected Boolean bias;
    protected Integer outputSize;
    protected Integer inputSize;

    @Override
    public final void compute(final String propertiesPath) {

        try {
            this.load(propertiesPath);
            this.readData();
        } catch (IOException e) {
            LOGGER.fatal(String.format("Failed to initialize the task %s", this.getClass().getSimpleName()), e);
            return;
        }

        this.buildNetwork();
        this.doTask();

        try {
            this.saveResult();
        } catch (FileNotFoundException fnfe) {
            LOGGER.fatal(String.format("Failed to save the task's %s results", this.getClass().getSimpleName()), fnfe);
        }
    }

    protected void saveResult() throws FileNotFoundException {
        this.saveErrorResultToFile();
        this.saveDataResultToFile();
    }

    private void saveErrorResultToFile() throws FileNotFoundException {
        final PrintWriter errWriter = new PrintWriter(new File(String.format("%s/%s", this.dataDir, this.errPath)));
        int it = 0;
        for (Double err : this.errors) {
            errWriter.print(it++);
            errWriter.print(" ");
            errWriter.print(err);
            errWriter.println();
        }
        errWriter.flush();
        errWriter.close();
    }

    private void saveDataResultToFile() throws FileNotFoundException {
        final PrintWriter dataWriter = new PrintWriter(new File(String.format("%s/%s", this.dataDir, this.outPath)));
        for (Pair<Double, Double[]> result : this.result) {
            dataWriter.print(result.getKey());
            dataWriter.print(" ");
            for (Double res : result.getValue()) {
                dataWriter.print(res);
                dataWriter.print(" ");
            }
            dataWriter.println();
        }
        dataWriter.flush();
        dataWriter.close();
    }

    protected abstract void doTask();

    protected void buildNetwork() {
        final Double lower = this.range.getKey();
        final Double higher = this.range.getValue();
        final double biasWeight = 1.0;

        final NeuralLayer outputLayer = this.getLayer(lower, higher, biasWeight, this.neurons[2], this.neurons[1], 2, true);
        final NeuralLayer hiddenLayer = this.getLayer(lower, higher, biasWeight, this.neurons[1], this.neurons[0], 1, false);
        final NeuralLayer inputLayer = this.getLayer(lower, higher, biasWeight, this.neurons[0], this.inputSize, 0, false);

        inputLayer.setUpperLayer(hiddenLayer);
        hiddenLayer.setUpperLayer(outputLayer);

        this.network = new NeuralNetwork(this.outputSize, inputLayer, hiddenLayer, outputLayer);
    }

    protected NeuralLayer getLayer(final Double lower,
                                   final Double higher,
                                   final double biasWeight,
                                   final Integer neurons,
                                   final Integer neuronSize,
                                   final Integer layer,
                                   final Boolean output) {
        if (this.bias) {
            return NeuralFactory.Layers.newLayer(lower, higher, biasWeight, neurons, neuronSize, this.functions[layer], this.momentumRate, this.learningFactor, output);
        } else {
            return NeuralFactory.Layers.newLayer(lower, higher, neurons, neuronSize, this.functions[layer], this.momentumRate, this.learningFactor, output);
        }
    }

    protected abstract void readData() throws FileNotFoundException;

    /**
     * Reads properties from the given path. Properties contains information
     * about task's constants, data files, train files and network structure
     *
     * @param propertiesPath path to properties file
     * @throws IOException if file couldn't have been read
     */
    protected void load(final String propertiesPath) throws IOException {
        this.properties = new Properties();
        this.properties.load(new BufferedReader(new FileReader(new File(propertiesPath))));

        LOGGER.info(String.format("Task properties=%s", this.properties));

        this.epochs = Integer.valueOf(this.properties.getProperty("task.epochs"));
        this.outputSize = Integer.valueOf(this.properties.getProperty("task.output"));
        this.inputSize = Integer.valueOf(this.properties.getProperty("task.input"));
        this.layers = Integer.valueOf(this.properties.getProperty("task.layers"));
        this.momentumRate = Double.valueOf(this.properties.getProperty("task.momentum"));
        this.learningFactor = Double.valueOf(this.properties.getProperty("task.learningFactor"));
        this.dataDir = this.properties.getProperty("task.dataDir");
        this.outPath = this.properties.getProperty("task.out.data");
        this.errPath = this.properties.getProperty("task.out.err");
        this.bias = Boolean.valueOf(this.properties.getProperty("task.bias", "false"));
        this.neurons = this.readNeuronsInLayers();
        this.functions = this.readFunctionsInLayers();
        this.range = this.readRandomRange();
    }

    private Integer[] readNeuronsInLayers() {
        final String[] neuronsSplit = this.properties.getProperty("task.neurons").split(",");
        Integer[] neurons = new Integer[neuronsSplit.length];
        for (int i = 0, neuronsSplitLength = neuronsSplit.length; i < neuronsSplitLength; i++) {
            String nn = neuronsSplit[i];
            neurons[i] = Integer.valueOf(nn);
        }
        return neurons;
    }

    /**
     * By using reflection, this methods resolves functions to be used on each layer.
     * Each objects is assigned to corresponding layer by its position in the array.
     *
     * @return array of {@link Function} objects.
     */
    private Function[] readFunctionsInLayers() {
        final String[] functionsSplit = this.properties.getProperty("task.functions").split(",");
        final Function[] functions = new Function[functionsSplit.length];
        for (int i = 0, functionsSplitLength = functionsSplit.length; i < functionsSplitLength; i++) {
            String funcStr = functionsSplit[i];
            final String className = funcStr.substring(0, funcStr.indexOf("("));
            final String[] args = funcStr.substring(funcStr.indexOf("(") + 1, funcStr.indexOf(")")).split(";");
            Function function = null;
            if (args.length == 1) {
                function = Reflect.on(className).create(Double.valueOf(args[0])).get();
            } else if (args.length == 2) {
                function = Reflect.on(className).create(Double.valueOf(args[0]), Double.valueOf(args[1])).get();
            }
            functions[i] = function;
        }
        return functions;
    }

    private Pair<Double, Double> readRandomRange() {
        final String[] randomSplit = this.properties.getProperty("task.random").split(",");
        return new Pair<>(Double.valueOf(randomSplit[0]), Double.valueOf(randomSplit[1]));
    }
}
