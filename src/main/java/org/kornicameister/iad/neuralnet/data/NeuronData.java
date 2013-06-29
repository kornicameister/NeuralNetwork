package org.kornicameister.iad.neuralnet.data;

import com.google.common.base.Preconditions;
import org.kornicameister.iad.neuralnet.core.NeuralData;
import org.kornicameister.iad.neuralnet.core.impl.Neuron;

import java.util.Arrays;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuronData implements NeuralData {
    private Neuron neuron;
    private Double[] oldWeights;
    private Double[] weights;
    private Double[] signals;

    public NeuronData(Double[] signals, Double[] weights) {
        this.signals = signals.clone();
        this.weights = weights.clone();
        this.oldWeights = weights.clone();
    }

    @Override
    public Double[] getOldWeights() {
        return oldWeights;
    }

    @Override
    public void setOldWeights(Double[] oldWeights) {
        this.oldWeights = oldWeights.clone();
    }

    @Override
    public Double[] getSignals() {
        return signals;
    }

    @Override
    public void setSignals(Double[] signals) {
        this.signals = signals;
    }

    @Override
    public Double[] getWeights() {
        return weights;
    }

    @Override
    public void setWeights(Double[] weights) {
        this.weights = weights.clone();
    }

    @Override
    public final int getSize() {
        return this.weights.length;
    }

    public Neuron getNeuron() {
        return neuron;
    }

    public void setNeuron(final Neuron neuron) {
        this.neuron = neuron;
    }

    public Double getSignalAt(final int index) {
        Preconditions.checkArgument(index >= 0 && index <= (this.getSize() - 1), "Invalid signal index");
        return this.signals[index];
    }

    public Double getWeightAt(final int index) {
        Preconditions.checkArgument(index >= 0 && index <= (this.getSize() - 1), "Invalid weight index");
        return this.weights[index];
    }

    @Override
    public String toString() {
        return "NeuronData{" +
                "neuron=" + neuron +
                ", oldWeights=" + Arrays.toString(oldWeights) +
                ", weights=" + Arrays.toString(weights) +
                ", signals=" + Arrays.toString(signals) +
                "} " + super.toString();
    }
}
