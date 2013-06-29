package org.kornicameister.iad.neuralnet.core;

/**
 * {@link NeuralLearner} is an interface used in learning process.
 */
public interface NeuralLearner {
    Double computeError();

    void adjustWeights(final Double error);
}
