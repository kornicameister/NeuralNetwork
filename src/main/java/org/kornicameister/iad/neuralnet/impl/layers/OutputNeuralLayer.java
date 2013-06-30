package org.kornicameister.iad.neuralnet.impl.layers;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralBackPropagation;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.Neuron;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class OutputNeuralLayer extends NeuralLayer {
    private static final Logger LOGGER = Logger.getLogger(OutputNeuralLayer.class);

    public OutputNeuralLayer(final Neuron... neurons) {
        super(neurons);
    }

    public OutputNeuralLayer(final NeuralLayer lowerLayer, final Neuron... neurons) {
        super(null, lowerLayer, neurons);
    }

    @Override
    public NeuralBackPropagation learn(final Double... networkExpectedResult) {
        for (int i = 0, size = this.getSize(); i < size; i++) {
            // getting this layer neuron
            final Neuron neuron = this.neurons.get(i);

            // setting help variables
            final Double neuronRawOutput = neuron.getRawOutput()[0];
            final Double neuronOutput = neuron.getOutput()[0];
            final Function function = neuron.getActivationFunction();
            final Double delta = (neuronOutput - networkExpectedResult[i]) * function.derivativeCalculate(neuronRawOutput);

            // learn neuron
            neuron.learn(delta);

            this.error[i] = delta;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FF >> Layer %d/%s has\n\terror=%s",
                    this.layerId,
                    this.getType(),
                    Arrays.toString(this.error)));
        }

        return this;
    }

    @Override
    public NeuralProcessable process() {
        final List<Double> layerResult = new LinkedList<>();
        for (Neuron neuron : this.neurons) {
            layerResult.add(neuron.process().getOutput()[0]);
        }
        this.output = layerResult.toArray(new Double[layerResult.size()]);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FF >> Layer %d/%s has\n\toutput=%s",
                    this.layerId,
                    this.getType(),
                    Arrays.toString(output)));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FF >>> top layer reached\n\toutput=%s", Arrays.toString(this.output)));
        }
        return this;
    }
}
