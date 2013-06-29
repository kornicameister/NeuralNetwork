package org.kornicameister.iad.task;

import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;
import org.kornicameister.iad.neuralnet.util.RandomUtil;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class DefaultTask implements Task {
    protected NeuralNetwork neuralNetwork = null;

    protected Double[] getWeightByHiddenLayersSize(final int size, final double lower, final double higher) {
        Double[] weight = new Double[size];
        for (int i = 0; i < size; i++) {
            weight[i] = RandomUtil.randomDouble(lower, higher);
        }
        return weight;
    }
}
