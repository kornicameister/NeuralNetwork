package org.kornicameister.iad.neuralnet.factory;

import org.kornicameister.iad.neuralnet.data.NeuronBiasData;
import org.kornicameister.iad.neuralnet.data.NeuronConstants;
import org.kornicameister.iad.neuralnet.data.NeuronData;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.Neuron;
import org.kornicameister.iad.neuralnet.impl.layers.OutputNeuralLayer;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;

import java.util.LinkedList;
import java.util.List;

public class NeuralFactory {

    public static class Layers {

        public synchronized static NeuralLayer newLayer(final Double lower,
                                                        final Double higher,
                                                        final double biasWeight,
                                                        final Integer neurons,
                                                        final Integer neuronSize,
                                                        final Function function,
                                                        final Double momentumRate,
                                                        final Double learningFactor,
                                                        final Boolean output) {
            final List<Neuron> hiddenNeurons = new LinkedList<>();
            for (int it = 0; it < neurons; it++) {
                hiddenNeurons.add(NeuralFactory.Neurons.newBiasNeuron(
                        function,
                        momentumRate,
                        learningFactor,
                        biasWeight,
                        ArraysUtils.newRandomDoubleArray(neuronSize, lower, higher))
                );
            }
            final Neuron[] neuronsAsArray = hiddenNeurons.toArray(new Neuron[hiddenNeurons.size()]);
            if (!output) {
                return new NeuralLayer(neuronsAsArray);
            }
            return new OutputNeuralLayer(neuronsAsArray);
        }
    }

    public static class Neurons {

        public synchronized static Neuron newNeuron(final Function function,
                                                    final Double momentum,
                                                    final Double learningFactor,
                                                    final Double[] signals,
                                                    final Double[] weights) {
            final NeuronConstants neuronConstants = new NeuronConstants(function, false, momentum, learningFactor);
            final NeuronData neuronData = new NeuronData(signals, weights);
            return newNeuron(neuronConstants, neuronData);
        }

        public synchronized static Neuron newNeuron(final NeuronConstants neuronConstants, final NeuronData neuronData) {
            final Neuron neuron = new Neuron(neuronConstants, neuronData);
            neuronData.setNeuron(neuron);
            return neuron;
        }

        public synchronized static Neuron newBiasNeuron(final Function function,
                                                        final Double momentum,
                                                        final Double learningFactor,
                                                        final Double biasWeight,
                                                        final Double[] signals,
                                                        final Double[] weights) {
            final NeuronConstants neuronConstants = new NeuronConstants(function, true, momentum, learningFactor);
            final NeuronData neuronData = new NeuronBiasData(biasWeight, weights, signals);
            return newNeuron(neuronConstants, neuronData);
        }

        public synchronized static Neuron newBiasNeuron(final Function function,
                                                        final Double momentum,
                                                        final Double learningFactor,
                                                        final Double biasWeight,
                                                        final Double[] weights) {
            final NeuronConstants neuronConstants = new NeuronConstants(function, true, momentum, learningFactor);
            final NeuronData neuronData = new NeuronBiasData(biasWeight, weights);
            return newNeuron(neuronConstants, neuronData);
        }

    }
}