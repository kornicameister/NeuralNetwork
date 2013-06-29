package org.kornicameister.iad.neuralnet.factory;

import org.kornicameister.iad.neuralnet.data.NeuronBiasData;
import org.kornicameister.iad.neuralnet.data.NeuronConstants;
import org.kornicameister.iad.neuralnet.data.NeuronData;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.Neuron;
import org.kornicameister.iad.neuralnet.impl.layers.InputNeuralLayer;
import org.kornicameister.iad.neuralnet.impl.layers.OutputNeuralLayer;

import java.util.List;

public class NeuralFactory {

    public static class Layers {
        public static NeuralLayer newOutputLayer(final NeuralLayer lowerLayer, final List<Neuron> neurons) {
            return new OutputNeuralLayer(lowerLayer, neurons.toArray(new Neuron[neurons.size()]));
        }

        public static NeuralLayer newHiddenLayer(final NeuralLayer upperLayer, final NeuralLayer lowerLayer, final List<Neuron> neurons) {
            return new NeuralLayer(upperLayer, lowerLayer, neurons.toArray(new Neuron[neurons.size()]));
        }

        public static NeuralLayer newInputLayer(final NeuralLayer higherLayer, final List<Neuron> neurons) {
            return new InputNeuralLayer(higherLayer, neurons.toArray(new Neuron[neurons.size()]));
        }
    }

    public static class Neurons {

        public static Neuron newNeuron(final Function function,
                                       final Double momentum,
                                       final Double learningFactor,
                                       final Double[] signals,
                                       final Double[] weights) {
            final NeuronConstants neuronConstants = new NeuronConstants(function, false, momentum, learningFactor);
            final NeuronData neuronData = new NeuronData(signals, weights);
            return newNeuron(neuronConstants, neuronData);
        }

        public static Neuron newNeuron(final NeuronConstants neuronConstants, final NeuronData neuronData) {
            final Neuron neuron = new Neuron(neuronConstants, neuronData);
            neuronData.setNeuron(neuron);
            return neuron;
        }

        public static Neuron newBiasNeuron(final Function function,
                                           final Double momentum,
                                           final Double learningFactor,
                                           final Double biasWeight,
                                           final Double[] signals,
                                           final Double[] weights) {
            final NeuronConstants neuronConstants = new NeuronConstants(function, true, momentum, learningFactor);
            final NeuronData neuronData = new NeuronBiasData(biasWeight, weights, signals);
            return newNeuron(neuronConstants, neuronData);
        }

        public static Neuron newBiasNeuron(final Function function,
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