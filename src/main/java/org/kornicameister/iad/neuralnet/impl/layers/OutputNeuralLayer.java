package org.kornicameister.iad.neuralnet.impl.layers;

import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.Neuron;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class OutputNeuralLayer extends NeuralLayer {

    public OutputNeuralLayer() {
        super();
    }

    public OutputNeuralLayer(final NeuralLayer lowerLayer, final Neuron... neurons) {
        super(null, lowerLayer, neurons);
    }
}
