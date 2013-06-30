package org.kornicameister.iad.neuralnet.backend;

import org.kornicameister.iad.neuralnet.NeuralBackPropagation;
import org.kornicameister.iad.neuralnet.NeuralInformation;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.data.NeuronConstants;
import org.kornicameister.iad.neuralnet.data.NeuronData;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.impl.Neuron;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class _Neuron
        implements NeuralProcessable, NeuralInformation, NeuralBackPropagation {
    private static Integer NEURON_ID = 0;
    protected final Integer neuronId;
    protected final NeuronConstants constants;
    protected final NeuronData values;

    protected _Neuron(final NeuronConstants constants, final NeuronData values) {
        this.neuronId = NEURON_ID++;
        this.constants = constants;
        this.values = values;
    }

    public Integer getNeuronId() {
        return neuronId;
    }

    @Override
    public final Function getActivationFunction() {
        return this.constants.getActivationFunction();
    }

    @Override
    public final Boolean isBiasEnabled() {
        return this.constants.isBiasEnabled();
    }

    @Override
    public final Double getMomentum() {
        return this.constants.getMomentum();
    }

    @Override
    public final Double getLearningConstant() {
        return this.constants.getLearningConstant();
    }

    public Double[] getOldWeights() {
        return this.values.getOldWeights();
    }

    public void setOldWeights(final Double[] oldWeights) {
        this.values.setOldWeights(oldWeights);
    }

    public Double[] getSignals() {
        return this.values.getSignals();
    }

    @Override
    public NeuralProcessable setSignal(final Double... signals) {
        this.values.setSignal(signals);
        return this;
    }

    @Override
    public Integer getSize() {
        return this.values.getSize();
    }

    public Double[] getWeights() {
        return this.values.getWeights();
    }

    public void setWeights(final Double[] weights) {
        this.values.setWeights(weights);
    }

    public Neuron getNeuron() {
        return this.values.getNeuron();
    }

    public Double getSignalAt(final int index) {
        return this.values.getSignalAt(index);
    }

    public Double getWeightAt(final int index) {
        return this.values.getWeightAt(index);
    }

    public Double getOldWeightAt(final int index) {
        return this.values.getOldWeightAt(index);
    }

    public void setWeightAt(final Double val, final int index) {
        this.values.setWeightAt(val, index);
    }
}
