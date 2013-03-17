package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.function.Functional;
import org.kornicameister.iad.neuralnet.core.NeuronalTraversable;

/**
 * {@link Neuron} is basic brick used to built
 * {@link org.kornicameister.iad.neuralnet.NeuralNetwork}. Neurons are organized into
 * {@link org.kornicameister.iad.neuralnet.NeuralLayer}
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class Neuron implements
        NeuralProcessable,
        NeuronalTraversable {
    private Double[] weights;
    private Double[] inputs;
    private Functional activationFunction;

    public Neuron(int size, Functional activationFunction) {
        this.activationFunction = activationFunction;
        this.weights = new Double[size];
        this.inputs = new Double[size];
    }

    @Override
    public void teach() {
    }

    @Override
    public void process() {
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
}
