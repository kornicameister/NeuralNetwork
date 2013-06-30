package org.kornicameister.iad.neuralnet;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NeuralBackPropagation {
    NeuralBackPropagation update();

    NeuralBackPropagation teach(Double... result);
}
