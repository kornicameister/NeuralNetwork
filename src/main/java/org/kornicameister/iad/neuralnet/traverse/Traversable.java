package org.kornicameister.iad.neuralnet.traverse;

/**
 * This interface defines functionality
 * required to traverse trough neural network
 * neuron by neuron in context of teachable
 * neuronal network
 *
 * @author kornicameister
 * @since 0.0.1
 */
public interface Traversable {
    void pushResultForward(Double result);

    void receiveResult(Double result);

    Double getResultBackward();

    void attachTarget(Traversable... target);

    void attachSource(Traversable... source);

}
