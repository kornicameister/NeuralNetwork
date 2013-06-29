package org.kornicameister.iad.neuralnet.impl;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.backend._NeuralNetwork;
import org.kornicameister.iad.neuralnet.impl.layers.InputNeuralLayer;
import org.kornicameister.iad.neuralnet.impl.layers.OutputNeuralLayer;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuralNetwork extends _NeuralNetwork {
    private static final Logger LOGGER = Logger.getLogger(NeuralNetwork.class);
    private Double[] expectedResult;
    private Double[] result;

    public NeuralNetwork(final InputNeuralLayer inputNeuralLayer,
                         final OutputNeuralLayer outputNeuralLayer,
                         final NeuralLayer... hiddenLayers) {
        super(inputNeuralLayer, outputNeuralLayer, hiddenLayers);
    }

    public Double computeError() {
        Double error = 0d;
        for (int i = 0; i < result.length; i++) {
            error += Math.pow(this.result[i] - this.expectedResult[i], 2);
        }
        return error / 2.0;
    }

    public void feedBackward() {
        this.feedBackward(0d);
    }

    @Override
    public void feedBackward(final double delta) {
        //top layer error

    }

    @Override
    public void feedForward(final Double[] signal) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Out >>> Computing an output of the network");
        }
        for (NeuralLayer neuralLayer : this.hiddenLayers) {

        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Out >>> Computing an output of the network is finished");
        }
    }
}
