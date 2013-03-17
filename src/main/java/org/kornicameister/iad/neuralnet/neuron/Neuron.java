package org.kornicameister.iad.neuralnet.neuron;

import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.function.Functional;

/**
 * {@link Neuron} is basic brick used to built
 * {@link org.kornicameister.iad.neuralnet.network.NeuralNetwork}. Neurons are organized into
 * {@link org.kornicameister.iad.neuralnet.network.NeuralLayer} and connected with {@link Arc}.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class Neuron implements NeuralProcessable {
    private Double[] inputWeights;
    private Functional activationFunction;

    public Neuron(int size, Functional activationFunction) {
        this.activationFunction = activationFunction;
        this.inputWeights = new Double[size];
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
        for (int w = 0; w < this.inputWeights.length; w++) {
            this.inputWeights[w] = seed.nextDouble() * (higher - lower) + lower;
        }
    }
}
