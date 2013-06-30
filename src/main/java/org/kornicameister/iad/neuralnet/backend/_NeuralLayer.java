package org.kornicameister.iad.neuralnet.backend;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralBackPropagation;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.impl.Neuron;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
abstract public class _NeuralLayer
        implements NeuralProcessable, NeuralBackPropagation {
    private final static Logger LOGGER = Logger.getLogger(_NeuralLayer.class);
    private static Integer LAYER_ID = 0;
    protected final Integer layerId;
    protected final List<Neuron> neurons = new LinkedList<>();
    protected _NeuralLayer upperLayer;
    protected _NeuralLayer lowerLayer;

    protected _NeuralLayer(final _NeuralLayer upperLayer, final _NeuralLayer lowerLayer, final Neuron... neurons) {
        this(neurons);
        this.upperLayer = upperLayer;
        this.lowerLayer = lowerLayer;
    }

    protected _NeuralLayer(final Neuron... neurons) {
        super();
        this.layerId = LAYER_ID++;
        this.setNeurons(neurons);
    }

    public Neuron getNeuron(final int i) {
        return this.neurons.get(i);
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

    @Override
    public Integer getSize() {
        return this.neurons.size();
    }

    public _NeuralLayer getUpperLayer() {
        return upperLayer;
    }

    public _NeuralLayer setUpperLayer(final _NeuralLayer upperLayer) {
        if (this.upperLayer == null) {
            this.upperLayer = upperLayer;
            if (upperLayer.getLowerLayer() == null) {
                upperLayer.setLowerLayer(this);
            }
        }
        return this;
    }

    public _NeuralLayer getLowerLayer() {
        return lowerLayer;
    }

    public _NeuralLayer setLowerLayer(final _NeuralLayer lowerLayer) {
        if (this.lowerLayer == null) {
            this.lowerLayer = lowerLayer;
            if (lowerLayer.getUpperLayer() == null) {
                lowerLayer.setUpperLayer(this);
            }
        }
        return this;
    }

    public Integer getLayerId() {
        return layerId;
    }

    @Override
    public String toString() {
        return "_NeuralLayer{" +
                "layerId=" + layerId +
                ", neurons=" + neurons +
                ", isInputLayer=" + this.isInputLayer() +
                ", isHiddenLayer=" + this.isHiddenLayer() +
                ", isOutputLayer=" + this.isOutputLayer() +
                "} " + super.toString();
    }

    public Boolean isHiddenLayer() {
        return this.upperLayer != null && this.lowerLayer != null;
    }

    public Boolean isOutputLayer() {
        return this.upperLayer == null && this.lowerLayer != null;
    }

    public Boolean isInputLayer() {
        return this.lowerLayer == null && this.upperLayer != null;
    }

    public final String getType() {
        if (this.isHiddenLayer()) {
            return "hidden";
        }
        if (this.isInputLayer()) {
            return "input";
        }
        return "output";
    }
}
