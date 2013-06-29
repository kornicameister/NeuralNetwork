package org.kornicameister.iad.neuralnet.backend;

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
        implements NeuralProcessable, NeuralInformation {
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
        return values.getOldWeights();
    }

    public void setOldWeights(final Double[] oldWeights) {
        values.setOldWeights(oldWeights);
    }

    public Double[] getSignals() {
        return values.getSignals();
    }

    public void setSignals(final Double[] signals) {
        values.setSignals(signals);
    }

    public Double[] getWeights() {
        return values.getWeights();
    }

    public void setWeights(final Double[] weights) {
        values.setWeights(weights);
    }

    public int getSize() {
        return values.getSize();
    }

    public Neuron getNeuron() {
        return values.getNeuron();
    }

    public Double getSignalAt(final int index) {
        return values.getSignalAt(index);
    }

    public Double getWeightAt(final int index) {
        return values.getWeightAt(index);
    }
}
