package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.core.NeuralConnection;
import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.core.NeuralTraversable;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.impl._Neuron;

import java.util.Arrays;

/**
 * {@link Neuron} is basic brick used to built
 * {@link NeuralNetwork}. Neurons are organized into
 * {@link NeuralLayer}
 *
 * @author kornicameister
 * @since 0.0.1
 */
//TODO extra weight and input = 1 is not included
public class Neuron extends _Neuron implements
        NeuralProcessable,
        NeuralTraversable {
    private Function activationFunction;
    private Double delta = 0.0;
    private Double[] weights = new Double[0];
    private Double[] inputs = new Double[0];

    protected Neuron(Boolean biasEnabled) {
        super(biasEnabled);
    }

    public Neuron(Boolean biasEnabled,
                  Function function) {
        this(biasEnabled);
        this.activationFunction = function;
    }

    public Neuron(Boolean biasEnabled,
                  Function function,
                  NeuralConnection... connections) {
        this(biasEnabled, function);
        this.connections.addAll(Arrays.asList(connections));
    }

    public Neuron(Boolean biasEnabled,
                  Function function,
                  Double[] weights,
                  NeuralConnection... connections) {
        this(biasEnabled, function, connections);
        this.setSize(this.weights.length);
        this.weights = weights;
        this.inputs = new Double[this.weights.length];
    }

    /**
     * Teaching is feedForward that is considered as reversed to
     * processing. By that, teaching neuron in context of network
     * is based on:
     * <ul>
     * <li>retrieving results from neurons in upper layer</li>
     * <li>updating weights in neuron by particular calculated value</li>
     * </ul>
     */
    @Override
    public void feedBackward() {
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
    public void feedForward() {
        final Double result = this.computeOutput();
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
     *         y = f(sum_{i={0,n}}(w_i * x_i))
     *     </code>
     *     x stands for neuron's input at given i's position.
     * </pre>
     * Bias, as stands here <a href="http://natureofcode.com/book/chapter-10-neural-networks/">Point 10.3</a>,
     * is included in the output.
     *
     * @return computed output
     * @see Neuron#BIAS_VALUE
     * @see Neuron#biasEnabled
     */
    private Double computeOutput() {
        Double result = 0.0;
        for (int i = 0; i < this.weights.length; i++) {
            result += this.inputs[i] * this.weights[i];
        }
        if (this.biasEnabled) {
            result += BIAS_VALUE * this.biasWeight;
        }
        return this.activationFunction.calculate(result);
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        seed.setSeed(System.nanoTime());
        for (int w = 0; w < this.weights.length; w++) {
            this.weights[w] = seed.nextDouble() * (higher - lower) + lower;
        }
        this.biasWeight = seed.nextDouble() * (higher - lower) + lower;
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

    public void setSize(int size) {
        this.weights = new Double[size];
        this.inputs = new Double[size];
    }

    public int getSize() {
        return this.weights.length;
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
