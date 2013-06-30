package org.kornicameister.iad.neuralnet;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NeuralData {
    Double[] getOldWeights();

    void setOldWeights(Double[] oldWeights);

    Double[] getSignals();

    void setSignal(Double... signal);

    Double[] getWeights();

    void setWeights(Double... weight);

    int getSize();
}
