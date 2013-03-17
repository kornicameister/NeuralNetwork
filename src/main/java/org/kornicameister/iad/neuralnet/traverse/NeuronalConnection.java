package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.core.NeuronalConnectible;
import org.kornicameister.iad.neuralnet.core.NeuronalTraversable;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuronalConnection implements NeuronalConnectible {
    private final NeuronalTraversable neuron;
    private final int slot;

    public NeuronalConnection(NeuronalTraversable neuron, int slot) {
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
}
