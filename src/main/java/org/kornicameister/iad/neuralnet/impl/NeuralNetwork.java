package org.kornicameister.iad.neuralnet.impl;


import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.backend._NeuralNetwork;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuralNetwork extends _NeuralNetwork {
    private static final Logger LOGGER = Logger.getLogger(NeuralNetwork.class);
    private Double[] expectedOutput;
    private Double[] output;

    public NeuralNetwork(final Integer size, final NeuralLayer... layers) {
        super(layers);
        this.expectedOutput = ArraysUtils.newDoubleArray(size);
        this.output = ArraysUtils.newDoubleArray(size);
    }

    public Double computeError() {
        Double error = 0d;
        for (int i = 0; i < output.length; i++) {
            error += Math.pow(this.output[i] - this.expectedOutput[i], 2);
        }
        return error / 2.0;
    }

    public NeuralProcessable teach() {
        NeuralLayer layer = this.getOutputLayer();
        do {
            layer.teach(this.expectedOutput);
            layer = layer.getLowerLayer();
        } while (layer != null);
        for (NeuralLayer neuralLayer : this.layerList) {
            neuralLayer.update();
        }
        return this;
    }

    private NeuralLayer getOutputLayer() {
        return this.layerList.get(this.layerList.size() - 1);
    }

    @Override
    public NeuralProcessable process() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("FF >>> Computing an output of the network");
        }
        final Long startTime = System.nanoTime();
        for (final NeuralLayer neuralLayer : this.layerList) {
            neuralLayer.process();
        }
        final NeuralLayer topLayer = this.getOutputLayer();
        final Double[] topOutput = topLayer.getOutput();
        System.arraycopy(topOutput, 0, this.output, 0, topOutput.length);
        final Long endTime = System.nanoTime() - startTime;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info(String.format("FF >>> Computing an output of the network is finished\n\ttime=%d\n\toutput=%s",
                    TimeUnit.NANOSECONDS.toMillis(endTime),
                    Arrays.toString(this.output)));
        }
        return this;
    }

    @Override
    public NeuralProcessable setSignal(Double... signal) {
        final NeuralLayer layer = this.layerList.get(0);
        if (layer.isInputLayer()) {
            layer.setSignal(signal);
        }
        return this;
    }

    @Override
    public Double[] getOutput() {
        return this.output;
    }

    @Override
    public Integer getSize() {
        return this.output.length;
    }

    public void setExpectedOutput(final Double... expectedOutput) {
        this.expectedOutput = expectedOutput.clone();
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "expectedOutput=" + Arrays.toString(expectedOutput) +
                ", output=" + Arrays.toString(output) +
                "} " + super.toString();
    }
}
