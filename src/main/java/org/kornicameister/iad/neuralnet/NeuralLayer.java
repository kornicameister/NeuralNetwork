package org.kornicameister.iad.neuralnet;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.core.NeuralProcessable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link NeuralLayer} represents set of neurons
 * laying on the same layer in network, where
 * network itself is built from layers.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralLayer implements NeuralProcessable {
    private final static Logger LOGGER = Logger.getLogger(NeuralLayer.class);
    private List<Neuron> neurons = new LinkedList<>();

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
            LOGGER.info(String.format("Teaching neuron %s", neuron));
            neuron.feedBackward();
        }
    }

    @Override
    public void feedForward() {
        for (Neuron neuron : this.neurons) {
            LOGGER.info(String.format("Processing neuron %s", neuron));
            neuron.feedForward();
        }
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        for (Neuron neuron : this.neurons) {
            neuron.initByRandom(lower, higher);
        }
    }

    public boolean addNeuron(Neuron neuron) {
        return this.neurons.add(neuron);
    }

    public Neuron getNeuron(int i) {
        return this.neurons.get(i);
    }

    public Neuron removeNeuron(int i) {
        return this.neurons.remove(i);
    }

    public boolean removeNeuron(Object o) {
        return this.neurons.remove(o);
    }

    public List<Neuron> getNeurons() {
        return this.neurons;
    }

    public void clearNeurons() {
        this.neurons.clear();
    }

    public int getSize() {
        return this.neurons.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NeuralLayer{");
        sb.append("neurons=").append(neurons.size());
        sb.append('}');
        return sb.toString();
    }
}
