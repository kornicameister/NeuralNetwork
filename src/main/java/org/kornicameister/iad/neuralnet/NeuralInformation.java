package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.function.Function;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NeuralInformation {
    Function getActivationFunction();

    Boolean isBiasEnabled();

    Double getMomentum();

    Double getLearningConstant();
}
