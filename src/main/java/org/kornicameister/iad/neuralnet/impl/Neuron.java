package org.kornicameister.iad.neuralnet.impl;

import org.kornicameister.iad.neuralnet.backend._Neuron;
import org.kornicameister.iad.neuralnet.data.NeuronConstants;
import org.kornicameister.iad.neuralnet.data.NeuronData;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Neuron extends _Neuron {

    public Neuron(final NeuronConstants constants, final NeuronData values) {
        super(constants, values);
    }

    @Override
    public void feedBackward(final double delta) {

    }

    @Override
    public void feedForward(final Double[] signal) {

    }
}
