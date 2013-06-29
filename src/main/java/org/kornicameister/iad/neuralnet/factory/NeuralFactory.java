package org.kornicameister.iad.neuralnet.factory;

import org.kornicameister.iad.neuralnet.Neuron;
import org.kornicameister.iad.neuralnet.core.NeuronConstants;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;
import org.kornicameister.iad.neuralnet.util.RandomUtil;
import org.kornicameister.iad.neuralnet.values.NeuronBiasData;
import org.kornicameister.iad.neuralnet.values.NeuronData;

public class NeuralFactory {

    public static Neuron newRandomNeuron(final Function function,
                                         final Double momentum,
                                         final Double learningFactor,
                                         final Integer size,
                                         final Integer lower,
                                         final Integer higher) {
        return NeuralFactory.newNeuron(function, momentum, learningFactor, ArraysUtils.newRandomDoubleArray(size, lower, higher), ArraysUtils.newRandomDoubleArray(size, lower, higher));
    }

    public static Neuron newNeuron(final Function function,
                                   final Double momentum,
                                   final Double learningFactor,
                                   final Double[] signals,
                                   final Double[] weights) {
        final NeuronConstants neuronConstants = new NeuronConstants(function, false, momentum, learningFactor);
        final NeuronData neuronData = new NeuronData(signals, weights);
        return NeuralFactory.newNeuron(neuronConstants, neuronData);
    }

    public static Neuron newNeuron(final NeuronConstants neuronConstants, final NeuronData neuronData) {
        final Neuron neuron = new Neuron(neuronConstants, neuronData);
        neuronData.setNeuron(neuron);
        return neuron;
    }

    public static Neuron newRandomBiasNeuron(final Function function,
                                             final Double momentum,
                                             final Double learningFactor,
                                             final Integer size,
                                             final Integer lower,
                                             final Integer higher) {
        return NeuralFactory.newBiasNeuron(function, momentum, learningFactor, RandomUtil.randomDouble(lower, higher), ArraysUtils.newRandomDoubleArray(size, lower, higher), ArraysUtils.newRandomDoubleArray(size, lower, higher));
    }

    public static Neuron newBiasNeuron(final Function function,
                                       final Double momentum,
                                       final Double learningFactor,
                                       final Double biasWeight,
                                       final Double[] signals,
                                       final Double[] weights) {
        final NeuronConstants neuronConstants = new NeuronConstants(function, true, momentum, learningFactor);
        final NeuronData neuronData = new NeuronBiasData(biasWeight, signals, weights);
        return NeuralFactory.newNeuron(neuronConstants, neuronData);
    }
}