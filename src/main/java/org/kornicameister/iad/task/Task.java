package org.kornicameister.iad.task;

import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Task {
    NeuralNetwork buildNetwork();

    void compute();

    void saveResult();

    void readData();
}
