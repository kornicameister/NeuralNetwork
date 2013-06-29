package org.kornicameister.iad.neuralnet.backend;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.impl.Neuron;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
abstract public class _NeuralLayer implements NeuralProcessable {
    private final static Logger LOGGER = Logger.getLogger(_NeuralLayer.class);
    protected final List<Neuron> neurons = new LinkedList<>();
    protected _NeuralLayer upperLayer;
    protected _NeuralLayer lowerLayer;

    protected _NeuralLayer() {
        super();
    }

    protected _NeuralLayer(final _NeuralLayer upperLayer, final _NeuralLayer lowerLayer, final Neuron... neurons) {
        super();
        this.upperLayer = upperLayer;
        this.lowerLayer = lowerLayer;
        this.setNeurons(neurons);
    }

    public _NeuralLayer setUpperLayer(final _NeuralLayer upperLayer) {
        this.upperLayer = upperLayer;
        upperLayer.setLowerLayer(this);
        return this;
    }

    public _NeuralLayer setLowerLayer(final _NeuralLayer lowerLayer) {
        this.lowerLayer = lowerLayer;
        lowerLayer.setUpperLayer(this);
        return this;
    }

    public Neuron getNeuron(final int i) {
        return this.neurons.get(i);
    }

    public Neuron removeNeuron(final int i) {
        return this.neurons.remove(i);
    }

    public boolean removeNeuron(final Neuron neuron) {
        return this.neurons.remove(neuron);
    }

    public List<Neuron> getNeurons() {
        return this.neurons;
    }

    public _NeuralLayer setNeurons(final Neuron... neurons) {
        for (Neuron neuron : neurons) {
            if (!this.addNeuron(neuron) && LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Failed to add neuron >>> %s", neuron));
            }
        }
        return this;
    }

    public boolean addNeuron(final Neuron neuron) {
        return this.neurons.add(neuron);
    }

    public void clearNeurons() {
        this.neurons.clear();
    }

    public int getSize() {
        return this.neurons.size();
    }

    @Override
    public String toString() {
        return "_NeuralLayer{" +
                "neurons=" + neurons +
                ", upperLayer=" + upperLayer +
                ", lowerLayer=" + lowerLayer +
                ", isInputLayer=" + this.isInputLayer() +
                ", isHiddenLayer=" + this.isHiddenLayer() +
                ", isOutputLayer=" + this.isOutputLayer() +
                "} " + super.toString();
    }

    public Boolean isHiddenLayer() {
        return !(this.isInputLayer() && this.isOutputLayer());
    }

    public Boolean isOutputLayer() {
        return this.upperLayer == null && this.lowerLayer != null;
    }

    public Boolean isInputLayer() {
        return this.lowerLayer == null && this.upperLayer != null;
    }
}
