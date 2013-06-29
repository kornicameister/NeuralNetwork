package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.Neuron;
import org.kornicameister.iad.neuralnet.core.Arc;

/**
 * Defines connection between particular neuron upon
 * particular slot. Slot determines from which entry
 * weight or signal is being chosen.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class InternalArc extends Arc {
    private final Neuron neuron;

    public InternalArc(Neuron neuron, int slot) {
        super(slot);
        this.neuron = neuron;
    }

    @Override
    public void pushResultForward(Double result) {
        this.neuron.setInput(this.slot, result);
    }

    @Override
    public Double getResultBackward() {
        return this.neuron.getInput(this.slot);
    }

    @Override
    public Double getDelta() {
        return this.neuron.getDelta() * this.neuron.getWeights()[this.slot];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{neuron=").append(neuron.getNeuronId());
        sb.append(", slot=").append(this.slot);
        sb.append('}');
        return sb.toString();
    }
}
