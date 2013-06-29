package org.kornicameister.iad.neuralnet;

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
     *
     * @param delta value of an error, that will be used in neurons teaching process
     */
    void feedBackward(final double delta);

    /**
     * Use this method to feedForward input of neural network.
     *
     * @param signal signal to be processed
     */
    void feedForward(final Double[] signal);
}
