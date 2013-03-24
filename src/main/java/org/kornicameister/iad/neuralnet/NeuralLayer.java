package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.impl._NeuralLayer;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * {@link NeuralLayer} represents set of neurons
 * laying on the same layer in network, where
 * network itself is built from layers.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralLayer extends _NeuralLayer implements NeuralProcessable {
    public NeuralLayer() {
    }

    public NeuralLayer(Neuron... neurons) {
        this.neurons.addAll(Arrays.asList(neurons));
    }

    @Override
    public void feedBackward() {
        ListIterator<Neuron> neuronListIterator = this.neurons.listIterator(this.neurons.size());
        while (neuronListIterator.hasPrevious()) {
            Neuron neuron = neuronListIterator.previous();
            neuron.feedBackward();
        }
    }

    @Override
    public void feedForward() {
        for (Neuron neuron : this.neurons) {
            neuron.feedForward();
        }
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        for (Neuron neuron : this.neurons) {
            neuron.initByRandom(lower, higher);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NeuralLayer{");
        sb.append("neurons=").append(neurons.size());
        sb.append('}');
        return sb.toString();
    }
}
