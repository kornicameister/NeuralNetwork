package org.kornicameister.iad.neuralnet.core;

import java.util.Random;

/**
 * Interface showing how each significant brick
 * of the network works. There is no difference when
 * comes to processing the network as each layer and
 * neuron in that layer must be processed, taught or initialized.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public interface NeuralProcessable {
    /**
     * Seed to be used in random initialization
     */
    static Random seed = new Random();

    void teach();

    void process();

    /**
     * Method to initialize either network,layer or particular neuron
     *
     * @param lower  bound of initialization
     * @param higher bound of initialization
     */
    void initByRandom(Double lower, Double higher);
}
