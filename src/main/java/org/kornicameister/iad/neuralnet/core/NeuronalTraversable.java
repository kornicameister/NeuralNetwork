package org.kornicameister.iad.neuralnet.core;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public interface NeuronalTraversable {
    Double getInput(int index);

    void setInput(int index, Double value);
}
