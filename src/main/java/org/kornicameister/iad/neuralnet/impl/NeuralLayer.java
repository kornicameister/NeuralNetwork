package org.kornicameister.iad.neuralnet.impl;

import com.google.common.base.Preconditions;
import org.kornicameister.iad.neuralnet.backend._NeuralLayer;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuralLayer extends _NeuralLayer {

    public NeuralLayer() {
        super();
    }

    public NeuralLayer(final NeuralLayer upperLayer, final NeuralLayer lowerLayer, Neuron... neurons) {
        super(upperLayer, lowerLayer, neurons);
    }

    public NeuralLayer getUpperLayer() {
        return (NeuralLayer) this.upperLayer;
    }

    public NeuralLayer getLowerLayer() {
        return (NeuralLayer) this.lowerLayer;
    }

    public List<Neuron> getUpperNeurons() {
        Preconditions.checkState(!this.isOutputLayer(), "Can not get upper neurons, because this layer is already at the top");
        return this.upperLayer.getNeurons();
    }

    public List<Neuron> getLowerNeurons() {
        Preconditions.checkState(!this.isInputLayer(), "Can not get upper neurons, because this layer is already at the top");
        return this.lowerLayer.getNeurons();
    }

    @Override
    public void feedBackward(final double delta) {

    }

    @Override
    public void feedForward(final Double[] signal) {

    }
}
