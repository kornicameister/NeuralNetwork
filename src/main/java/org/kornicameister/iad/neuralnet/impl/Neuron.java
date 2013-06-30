package org.kornicameister.iad.neuralnet.impl;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralBackPropagation;
import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.backend._Neuron;
import org.kornicameister.iad.neuralnet.data.NeuronConstants;
import org.kornicameister.iad.neuralnet.data.NeuronData;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Neuron extends _Neuron {
    private static final String DELTA_FOR_THE_NEURON_TOO_BIG = "Delta for the neuron too big";
    private static final Logger LOGGER = Logger.getLogger(Neuron.class);
    private Double output = 0d;
    private Double rawOutput = 0d;
    private Double delta = 0d;

    public Neuron(final NeuronConstants constants, final NeuronData values) {
        super(constants, values);
    }

    @Override
    public NeuralProcessable process() {
        this.rawOutput = 0d;
        for (int i = 0; i < this.getSize(); i++) {
            this.rawOutput += this.getWeightAt(i) * this.getSignalAt(i);
        }
        this.output = this.getActivationFunction().calculate(this.rawOutput);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FF >> Neuron %d has output=%f", this.neuronId, this.output));
        }
        return this;
    }

    @Override
    public Double[] getOutput() {
        return new Double[]{this.output};
    }

    public Double[] getRawOutput() {
        return new Double[]{this.rawOutput};
    }

    @Override
    public NeuralBackPropagation update() {
        Double val;
        Double oldVal;
        for (int i = 0; i < this.getSize(); i++) {
            val = this.getWeightAt(i);
            oldVal = val;
            val -= 2.0 * this.delta * this.getLearningConstant() * this.getSignalAt(i) + this.getMomentum() * (this.getWeightAt(i) - this.getOldWeightAt(i));
            this.setWeightAt(val, i);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("FB << Neuron %d updates\n\tweight=[%d, %.15f >> %.15f]", this.neuronId, i, oldVal, val));
            }
        }
        this.setOldWeights(this.getWeights());
        return this;
    }

    @Override
    public NeuralBackPropagation learn(final Double... delta) {
        Preconditions.checkArgument(delta.length == 1, DELTA_FOR_THE_NEURON_TOO_BIG);
        this.delta = delta[0];
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("FB << Neuron %d has delta=%f", this.neuronId, this.delta));
        }
        return this;
    }

    public Double getDelta() {
        return this.delta;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "output=" + output +
                ", rawOutput=" + rawOutput +
                ", delta=" + delta +
                "} " + super.toString();
    }
}
