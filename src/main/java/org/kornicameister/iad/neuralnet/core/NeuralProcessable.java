package org.kornicameister.iad.neuralnet.core;

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
     * This method implementation is designed to support teaching feedForward. For
     * example method of backwards propagation can be used.
     */
    void feedBackward();

    /**
     * Use this method to feedForward input of neural network.
     */
    void feedForward();

    /**
     * Method to initialize either network,layer or particular neuron
     *
     * @param lower  bound of initialization
     * @param higher bound of initialization
     */
    void initByRandom(Double lower, Double higher);
}
