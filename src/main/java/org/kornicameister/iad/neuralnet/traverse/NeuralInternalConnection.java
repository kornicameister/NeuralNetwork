package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.Neuron;
import org.kornicameister.iad.neuralnet.core.AbstractConnection;
import org.kornicameister.iad.neuralnet.core.NeuralTraversable;

/**
 * Defines connection between particular neuron upon
 * particular slot. Slot determines from which entry
 * weight or signal is being chosen.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralInternalConnection extends AbstractConnection {
    private final NeuralTraversable neuron;

    public NeuralInternalConnection(NeuralTraversable neuron, int slot) {
        super(slot);
        this.neuron = neuron;
    }

    @Override
    public void pushResultForward(Double result) {
        this.neuron.setInput(this.getSlot(), result);
    }

    @Override
    public Double getResultBackward() {
        return this.neuron.getInput(this.getSlot());
    }

    @Override
    public Double getDelta() {
        return this.neuron.getDelta();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{neuron=").append(((Neuron) neuron).getId());
        sb.append(", slot=").append(this.getSlot());
        sb.append('}');
        return sb.toString();
    }
}
