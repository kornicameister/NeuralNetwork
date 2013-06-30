package org.kornicameister.iad.neuralnet.impl;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralBackPropagation;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.backend._NeuralLayer;
import org.kornicameister.iad.neuralnet.function.Function;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuralLayer extends _NeuralLayer {
    private static final Logger LOGGER = Logger.getLogger(NeuralLayer.class);
    protected Double[] output;
    protected Double[] error;

    public NeuralLayer(Neuron... neurons) {
        super(neurons);
        this.output = ArraysUtils.newDoubleArray(this.getSize());
        this.error = ArraysUtils.newDoubleArray(this.getSize());
    }

    public NeuralLayer(final NeuralLayer upperLayer, final NeuralLayer lowerLayer, Neuron... neurons) {
        super(upperLayer, lowerLayer, neurons);
    }

    public List<Neuron> getUpperNeurons() {
        Preconditions.checkState(!this.isOutputLayer(), "Can not get upper neurons, because this layer is already at the top");
        return this.upperLayer.getNeurons();
    }

    public List<Neuron> getLowerNeurons() {
        Preconditions.checkState(!this.isInputLayer(), "Can not get upper neurons, because this layer is already at the top");
        return this.lowerLayer.getNeurons();
    }

    @Override
    public NeuralBackPropagation update() {
        for (Neuron neuron : this.neurons) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("FB << Updating neuron %d", neuron.getNeuronId()));
            }
            neuron.update();
        }
        return this;
    }

    @Override
    public NeuralBackPropagation setDelta(final Double... result) {
        this.error = new Double[this.getSize()];
        for (int i = 0, thisLayerNeuronsSize = this.neurons.size(); i < thisLayerNeuronsSize; i++) {
            final Neuron neuron = this.neurons.get(i);
            final Function function = neuron.getActivationFunction();

            Double err = 0d;

            for (int j = 0, upperLayerNeuronsSize = this.upperLayer.getSize(); j < upperLayerNeuronsSize; j++) {
                final Neuron upperNeuron = this.upperLayer.getNeuron(j);
                err += upperNeuron.getDelta() * upperNeuron.getWeightAt(i);
            }

            err = err * function.derivativeCalculate(neuron.getRawOutput()[0]);

            this.error[i] = ((Neuron) neuron.setDelta(err)).getDelta();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FB >>> \n\tresult=%s,\n\terror=%s",
                    Arrays.toString(this.output),
                    Arrays.toString(this.error)));
        }
        return this;
    }

    @Override
    public NeuralProcessable feedForward() {
        final List<Double> layerResult = new LinkedList<>();
        for (Neuron neuron : this.neurons) {
            layerResult.add(neuron.feedForward().getOutput()[0]);
        }
        final Double[] output = layerResult.toArray(new Double[layerResult.size()]);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FF >> Layer %d/%s has\n\toutput=%s",
                    this.layerId,
                    this.getType(),
                    Arrays.toString(output)));
        }
        this.getUpperLayer().setSignal(output);
        this.output = output;
        return this;
    }

    @Override
    public NeuralProcessable setSignal(final Double... signal) {
        for (Neuron neuron : this.neurons) {
            neuron.setSignal(signal);
        }
        return this;
    }

    @Override
    public Double[] getOutput() {
        return this.output;
    }

    public NeuralLayer getUpperLayer() {
        return (NeuralLayer) this.upperLayer;
    }

    public NeuralLayer getLowerLayer() {
        return (NeuralLayer) this.lowerLayer;
    }

    @Override
    public String toString() {
        return "NeuralLayer{" +
                "output=" + Arrays.toString(output) +
                "} " + super.toString();
    }
}
