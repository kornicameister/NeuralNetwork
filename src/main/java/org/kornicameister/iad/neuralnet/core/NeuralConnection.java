package org.kornicameister.iad.neuralnet.core;

/**
 * This interface defines functionality
 * required to traverse trough neural network
 * neuron by neuron in context of teachable
 * neuronal network.
 * </br>
 * Connection between neurons is related either to
 * link existing between two internal neurons or between
 * neurons in the last layer and network output
 *
 * @author kornicameister
 * @since 0.0.1
 */
public interface NeuralConnection {
    /**
     * This method is used to push value to next
     * layer on given slot. Slot should be memorized
     * with classes that implements this interface.
     *
     * @param result value to be passed
     */
    void pushResultForward(Double result);

    /**
     * Retrieves value from upper layers and push
     * it down to the previous
     *
     * @return retrieved value
     */
    Double getResultBackward();

    /**
     * This method is used to retrieve information up from neuronal network
     * output and push it to elementary neuron. Having this method
     * implemented allows each neuron to teach.
     * <a href="http://en.wikipedia.org/wiki/Delta_Rule">Delta Rule</a>
     *
     * @return teaching vector
     */
    Double getDelta();

}
