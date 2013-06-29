package org.kornicameister.iad.neuralnet.impl;

import org.kornicameister.iad.neuralnet.Neuron;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class _NeuralLayer {
    protected final List<Neuron> neurons = new LinkedList<>();
    protected final Boolean topLayer;

    public _NeuralLayer(Boolean topLayer) {
        this.topLayer = topLayer;
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
}
