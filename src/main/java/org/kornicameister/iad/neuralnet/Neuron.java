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
//TODO extra weight and input = 1 is not included
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
        this.setSize(this.weights.length + 1);
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
     * Computes output of this neuron by multiplying input signal by weight.
     * Neuron's output is calculated by following this equation, which
     * does includes weight which is an extra one added at the first
     * position in the neuron.
     * <pre>
     *     <code>
     *         y = f(w_0 + sum_{i={1,n}}(w_i * x_i))
     *     </code>
     *     x stands for neuron's input at given i's position.
     * </pre>
     *
     * @return computed output
     */
    private Double computeOutput() {
        Double result = this.weights[0];
        for (int i = 1; i < this.weights.length; i++) {
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
        this.inputs[0] = 1.0;
    }

    /**
     * Returns an index for given index
     *
     * @param index index of an input
     * @return input value at given index
     */
    @Override
    public Double getInput(int index) {
        return this.inputs[index];
    }

    /**
     * Sets an input on given index
     *
     * @param index absolute index
     * @param value value of input at the given index
     */
    @Override
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
    @Override
    public void setInput(Double[] inputVector) {
        this.inputs = inputVector;
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

    public void setSize(int size) {
        this.weights = new Double[size];
        this.inputs = new Double[size];
    }

    public int getSize() {
        return this.weights.length;
    }

    public Integer getId() {
        return this.neuronId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Neuron{\n");
        sb.append("neuronId=").append(neuronId).append("\n");
        sb.append("weights=").append(Arrays.toString(weights == null ? new Double[]{} : weights)).append("\n");
        sb.append("inputs=").append(Arrays.toString(inputs == null ? new Double[]{} : inputs)).append("\n");
        sb.append("delta=").append(delta).append("\n");
        sb.append("connections=").append(connections).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
