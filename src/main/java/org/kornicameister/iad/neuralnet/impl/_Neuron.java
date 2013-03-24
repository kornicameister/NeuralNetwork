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

    protected Double[] weights = new Double[0];
    protected Double[] inputs = new Double[0];

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

    public void setSize(final int size) {
        this.weights = new Double[size];
        this.inputs = new Double[size];
    }

    public int getSize() {
        return this.weights.length;
    }


    /**
     * Returns an index for given index
     *
     * @param index index of an input
     * @return input value at given index
     */
    public Double getInput(int index) {
        return this.inputs[index];
    }

    /**
     * Sets an input on given index
     *
     * @param index absolute index
     * @param value value of input at the given index
     */
    public void setInput(int index, Double value) {
        this.inputs[index] = value;
    }

    /**
     * Sets and input, by passing the <strong>input vector</strong>.
     * This method excludes first index of an input, which is always
     * equal to one.
     *
     * @param inputVector an input to be set
     */
    public void setInput(Double[] inputVector) {
        this.inputs = inputVector.clone();
    }

    public Double[] getWeights() {
        return weights;
    }

    public void setWeights(Double... weights) {
        this.weights = weights.clone();
        if (this.inputs == null || this.inputs.length != this.weights.length) {
            this.inputs = new Double[this.weights.length];
        }
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
