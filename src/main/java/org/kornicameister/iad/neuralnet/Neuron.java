package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.core.NeuralConnection;
import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.core.NeuralTraversable;
import org.kornicameister.iad.neuralnet.function.Function;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link Neuron} is basic brick used to built
 * {@link NeuralNetwork}. Neurons are organized into
 * {@link NeuralLayer}
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class Neuron implements
        NeuralProcessable,
        NeuralTraversable {
    private final static Double LEARNING_FACTOR = 0.4;
    private static Integer NEURON_ID = 0;
    private Integer neuronId = NEURON_ID++;
    private Double[] weights = new Double[0];
    private Double[] inputs = new Double[0];
    private Function activationFunction;
    private List<NeuralConnection> connections = new LinkedList<>();
    private Double delta = 0.0;

    public Neuron(Function function) {
        this.activationFunction = function;
    }

    public Neuron(Function function,
                  NeuralConnection... connections) {
        this(function);
        this.connections.addAll(Arrays.asList(connections));
    }

    public Neuron(Function function,
                  Double[] weights,
                  NeuralConnection... connections) {
        this(function, connections);
        this.weights = weights;
        this.inputs = new Double[this.weights.length];
    }

    /**
     * Teaching is process that is considered as reversed to
     * processing. By that, teaching neuron in context of network
     * is based on:
     * <ul>
     * <li>retrieving results from neurons in upper layer</li>
     * <li>updating weights in neuron by particular calculated value</li>
     * </ul>
     */
    @Override
    public void teach() {
        this.delta = 0.0;
        for (NeuralConnection connection : this.connections) {
            delta += connection.getDelta();
        }
        Double currentResult = this.computeOutput();
        Double weightUpdater = delta
                * LEARNING_FACTOR
                * this.activationFunction.derivativeCalculate(currentResult);
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] += (weightUpdater * this.inputs[i]);
        }
    }

    /**
     * Processing neuron is based on computing output value
     * and pushing it further.
     */
    @Override
    public void process() {
        Double result = this.computeOutput();
        for (NeuralConnection connectible : this.connections) {
            connectible.pushResultForward(result);
        }
    }

    /**
     * Computes output of this neuron by multiplying input signal by weight
     *
     * @return computed output
     */
    private Double computeOutput() {
        Double result = 0.0;
        for (int i = 0; i < this.weights.length; i++) {
            result += this.inputs[i] * this.weights[i];
        }
        return this.activationFunction.calculate(result);
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        seed.setSeed(System.nanoTime());
        for (int w = 0; w < this.weights.length; w++) {
            this.weights[w] = seed.nextDouble() * (higher - lower) + lower;
        }
    }

    @Override
    public Double getInput(int index) {
        return this.inputs[index];
    }

    @Override
    public void setInput(int index, Double value) {
        this.inputs[index] = value;
    }

    @Override
    public void setInput(Double[] clone) {
        this.inputs = clone;
    }

    public Double[] getWeights() {
        return weights;
    }

    public void setWeights(Double... weights) {
        this.weights = weights;
        if (this.inputs == null || this.inputs.length != this.weights.length) {
            this.inputs = new Double[this.weights.length];
        }
    }

    @Override
    public Double getDelta() {
        return this.delta;
    }

    public boolean addConnection(NeuralConnection... neuralConnections) {
        return connections.addAll(Arrays.asList(neuralConnections));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Neuron{");
        sb.append("neuronId=").append(neuronId);
        sb.append(", weights=").append(Arrays.toString(weights == null ? new Double[]{} : weights));
        sb.append(", inputs=").append(Arrays.toString(inputs == null ? new Double[]{} : inputs));
        sb.append(", delta=").append(delta);
        sb.append(", connections=").append(connections);
        sb.append(", activationFunction=").append(activationFunction);
        sb.append('}');
        return sb.toString();
    }
}
