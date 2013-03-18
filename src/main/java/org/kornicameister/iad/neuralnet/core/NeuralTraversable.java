package org.kornicameister.iad.neuralnet.core;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public interface NeuralTraversable {
    Double getInput(int index);

    void setInput(int index, Double value);

    /**
     * Result of last lesson
     *
     * @return result of last lesson
     */
    Double getDelta();

    void setInput(Double[] clone);
}
