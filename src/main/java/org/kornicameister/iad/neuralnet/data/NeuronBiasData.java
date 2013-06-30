package org.kornicameister.iad.neuralnet.data;

import org.kornicameister.iad.neuralnet.util.ArraysUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuronBiasData extends NeuronData {
    public static final Double BIAS_VALUE = 1.0;
    private Double biasWeight;
    private Double oldBiasWeight;

    public NeuronBiasData(final Double biasWeight, final Double[] weights) {
        this(biasWeight, ArraysUtils.newDoubleArray(weights.length), weights);
    }

    public NeuronBiasData(Double biasWeight, Double[] signals, Double[] weights) {
        super(signals, weights);
        this.biasWeight = biasWeight;
        this.oldBiasWeight = biasWeight;
    }

    @Override
    public Double[] getOldWeights() {
        List<Double> oldWeights = new LinkedList<>(Arrays.asList(super.getOldWeights().clone()));
        oldWeights.add(0, this.oldBiasWeight);
        return oldWeights.toArray(new Double[oldWeights.size()]);
    }

    @Override
    public void setOldWeights(Double[] oldWeights) {
        List<Double> oldWeightsTmp = new LinkedList<>(Arrays.asList(oldWeights.clone()));
        this.oldBiasWeight = oldWeightsTmp.remove(0);
        super.setWeights(oldWeightsTmp.toArray(new Double[oldWeightsTmp.size()]));
    }

    @Override
    public Double[] getSignals() {
        List<Double> signals = new LinkedList<>(Arrays.asList(super.getSignals().clone()));
        signals.add(0, BIAS_VALUE);
        return signals.toArray(new Double[signals.size()]);
    }

    @Override
    public Double[] getWeights() {
        List<Double> weights = new LinkedList<>(Arrays.asList(super.getWeights().clone()));
        weights.add(0, this.biasWeight);
        return weights.toArray(new Double[weights.size()]);
    }

    @Override
    public void setWeights(Double[] weights) {
        List<Double> weightsTmp = new LinkedList<>(Arrays.asList(weights.clone()));
        this.biasWeight = weightsTmp.remove(0);
        super.setWeights(weightsTmp.toArray(new Double[weightsTmp.size()]));
    }

    @Override
    public int getSize() {
        return super.getSize() + 1;
    }

    @Override
    public Double getSignalAt(int index) {
        if (index == 0) {
            return BIAS_VALUE;
        }
        return super.getSignalAt(index - 1);
    }

    @Override
    public Double getWeightAt(int index) {
        if (index == 0) {
            return this.getBiasWeight();
        }
        return super.getWeightAt(index - 1);
    }

    public Double getBiasWeight() {
        return biasWeight;
    }

    @Override
    public String toString() {
        return "NeuronBiasData{" +
                "biasWeight=" + biasWeight +
                ", oldBiasWeight=" + oldBiasWeight +
                "} " + super.toString();
    }

    @Override
    public Double getOldWeightAt(final int index) {
        if (index == 0) {
            return this.getOldBiasWeight();
        }
        return super.getOldWeightAt(index - 1);
    }

    @Override
    public void setWeightAt(final Double val, final int index) {
        if (index == 0) {
            this.biasWeight = val;
            return;
        }
        super.setWeightAt(val, index - 1);
    }

    public Double getOldBiasWeight() {
        return oldBiasWeight;
    }
}
