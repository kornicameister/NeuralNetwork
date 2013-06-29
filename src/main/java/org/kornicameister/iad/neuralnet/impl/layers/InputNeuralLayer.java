package org.kornicameister.iad.neuralnet.impl.layers;

import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.Neuron;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InputNeuralLayer extends NeuralLayer {

    public InputNeuralLayer() {
        super();
    }

    public InputNeuralLayer(final NeuralLayer higherLayer, final Neuron... neurons) {
        super(higherLayer, null, neurons);
    }

}
