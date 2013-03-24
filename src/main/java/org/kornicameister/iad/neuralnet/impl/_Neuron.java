package org.kornicameister.iad.neuralnet.impl;

import org.kornicameister.iad.neuralnet.core.NeuralConnection;
import org.kornicameister.iad.neuralnet.function.Function;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public abstract class _Neuron {
    private static Integer NEURON_ID = 0;
    /**
     * By this variable neuron is kept from learning
     * two fast by backward propagation method (gradient)
     */
    protected final static Double LEARNING_FACTOR = 0.4;
    /**
     * If bias input is enabled for neuron, therefore
     * this value is used and extra weight is computed
     * for this bias.
     */
    protected final static Double BIAS_VALUE = 1.0;

    protected Function activationFunction;
    protected final Integer neuronId = NEURON_ID++;
    protected List<NeuralConnection> connections = new LinkedList<>();
    protected Random seed = new Random();

    /**
     * If set to true, then additional weight is created for bias
     * and bias is used in neuron's computation process.
     */
    protected final Boolean biasEnabled;
    /**
     * Bias weight, by default equal to 1.0,
     * overridable via Neuron constructors
     * or setter method
     */
    protected Double biasWeight = 0.0;

    protected _Neuron(Boolean biasEnabled, Function activationFunction) {
        this.biasEnabled = biasEnabled;
        this.activationFunction = activationFunction;
    }

    public Integer getNeuronId() {
        return neuronId;
    }

    public boolean addConnection(NeuralConnection... neuralConnections) {
        return connections.addAll(Arrays.asList(neuralConnections));
    }

    public NeuralConnection getConnection(int connIndex) {
        return connections.get(connIndex);
    }

    public Boolean getBiasEnabled() {
        return biasEnabled;
    }

    public Double getBiasWeight() {
        return biasWeight;
    }

    public void setBiasWeight(Double biasWeight) {
        this.biasWeight = biasWeight;
    }

    public Function getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(Function activationFunction) {
        this.activationFunction = activationFunction;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("_Neuron{");
        sb.append("seed=").append(seed);
        sb.append(", neuronId=").append(neuronId);
        sb.append(", connections=").append(connections);
        sb.append(", biasWeight=").append(biasWeight);
        sb.append(", biasEnabled=").append(biasEnabled);
        sb.append(", activationFunction=").append(activationFunction);
        sb.append('}');
        return sb.toString();
    }
}
