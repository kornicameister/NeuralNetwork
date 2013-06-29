package org.kornicameister.iad.neuralnet.data;

import org.kornicameister.iad.neuralnet.function.Function;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuronConstants {
    protected final Function activationFunction;
    protected final Boolean biasEnabled;
    protected final Double momentumRate;
    protected final Double learningFactor;

    public NeuronConstants(final Function activationFunction, final Boolean biasEnabled, final Double momentumRate, final Double learningFactor) {
        this.activationFunction = activationFunction;
        this.biasEnabled = biasEnabled;
        this.momentumRate = momentumRate;
        this.learningFactor = learningFactor;
    }

    public Function getActivationFunction() {
        return activationFunction;
    }

    public Boolean getBiasEnabled() {
        return biasEnabled;
    }

    public Double getMomentumRate() {
        return momentumRate;
    }

    public Double getLearningFactor() {
        return learningFactor;
    }

    @Override
    public String toString() {
        return "NeuronConstants{" +
                "activationFunction=" + activationFunction +
                ", biasEnabled=" + biasEnabled +
                ", momentumRate=" + momentumRate +
                ", learningFactor=" + learningFactor +
                "} " + super.toString();
    }
}
