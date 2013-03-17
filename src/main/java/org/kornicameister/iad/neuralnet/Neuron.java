package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.core.NeuronalConnectible;
import org.kornicameister.iad.neuralnet.core.NeuronalTraversable;
import org.kornicameister.iad.neuralnet.function.Functional;

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
        NeuronalTraversable {
    private final static Double LEARNING_FACTOR = 0.4;
    private Double[] weights;
    private Double[] inputs;
    private Functional activationFunction;
    private List<NeuronalConnectible> connections;
    private Double teachingResult = 0.0;

    public Neuron(Functional functional,
                  Double[] weights,
                  NeuronalConnectible... connections) {
        this(functional, connections);
        this.weights = weights;
    }

    public Neuron(Functional functional,
                  NeuronalConnectible... connections) {
        this.activationFunction = functional;
        this.connections = new LinkedList<>(Arrays.asList(connections));
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
        this.teachingResult = 0.0;
        for (NeuronalConnectible connection : this.connections) {
            teachingResult += connection.getTeachingDiff();
        }
        Double currentResult = this.computeOutput();
        Double weightUpdater = teachingResult
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
        for (NeuronalConnectible connectible : this.connections) {
            connectible.pushResultForward(result);
        }
    }

    private Double computeOutput() {
        // weights must be calculated by multiplying them by input
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

    public Double[] getWeights() {
        return weights;
    }

    public void setWeights(Double[] weights) {
        this.weights = weights;
    }

    @Override
    public Double getTeachingDiff() {
        return this.teachingResult;
    }
}
