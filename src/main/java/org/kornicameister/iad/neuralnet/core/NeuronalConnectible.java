package org.kornicameister.iad.neuralnet.core;

/**
 * This interface defines functionality
 * required to traverse trough neural network
 * neuron by neuron in context of teachable
 * neuronal network
 *
 * @author kornicameister
 * @since 0.0.1
 */
public interface NeuronalConnectible {
    void pushResultForward(Double result);

    Double getResultBackward();

    /**
     * This method is used to retrieve information up from neuronal network
     * output and push it to elementary neuron. Having this method
     * implemented allows each neuron to teach.
     *
     * @return teaching vector
     */
    Double getTeachingDiff();

}
