package org.kornicameister.iad.neuralnet;

import org.apache.log4j.Logger;
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
public class Neuron extends _Neuron implements
        NeuralProcessable,
        NeuralTraversable {
    private Double delta = 0.0;
    private final static Logger LOGGER = Logger.getLogger(Neuron.class);

    public Neuron(Boolean biasEnabled,
                  Function function) {
        super(biasEnabled, function);
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
        this.setWeights(weights);
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
        Double guess = this.computeOutput();
        this.recomputeDelta();
        Double weightUpdater = 2.0
                * delta
                * LEARNING_FACTOR
                * this.activationFunction.derivativeCalculate(guess);

        LOGGER.info(String.format("Neuron[%d] -> guess=%f,delta=%f,weight_update=%f",
                this.neuronId, guess, this.delta, weightUpdater));

        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] += (weightUpdater * this.inputs[i]);
        }
    }

    private void recomputeDelta() {
        this.delta = 0.0;
        for (NeuralConnection connection : this.connections) {
            delta += connection.getDelta();
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
        result += BIAS_VALUE * this.biasWeight;
        return this.activationFunction.calculate(result);
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        seed.setSeed(System.nanoTime());
        for (int w = 0; w < this.weights.length; w++) {
            this.weights[w] = seed.nextDouble() * (higher - lower) + lower;
        }
        if (this.biasEnabled) {
            this.biasWeight = seed.nextDouble() * (higher - lower) + lower;
        }
    }

    @Override
    public Double getDelta() {
        return this.delta;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Neuron{");
        sb.append("weights=").append(Arrays.toString(weights));
        sb.append(", inputs=").append(Arrays.toString(inputs));
        sb.append(", delta=").append(delta);
        sb.append(", biasEnabled=").append(biasEnabled);
        if (biasEnabled) {
            sb.append(", biasWeight=").append(biasWeight);
            sb.append(", biasValue=").append(BIAS_VALUE);
        }
        sb.append('}');
        return sb.toString();
    }
}
