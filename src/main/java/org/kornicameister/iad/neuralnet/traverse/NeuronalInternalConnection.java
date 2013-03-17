package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.core.NeuronalConnectible;
import org.kornicameister.iad.neuralnet.core.NeuronalTraversable;

/**
 * Defines connection between particular neuron upon
 * particular slot. Slot determines from which entry
 * weight or signal is being chosen.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuronalInternalConnection implements NeuronalConnectible {
    private final NeuronalTraversable neuron;
    private final int slot;

    public NeuronalInternalConnection(NeuronalTraversable neuron, int slot) {
        this.neuron = neuron;
        this.slot = slot;
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
    public Double getTeachingDiff() {
        return this.neuron.getTeachingDiff();
    }
}
