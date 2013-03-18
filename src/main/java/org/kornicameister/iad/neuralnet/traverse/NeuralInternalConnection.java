package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.core.NeuralConnection;
import org.kornicameister.iad.neuralnet.core.NeuralTraversable;

/**
 * Defines connection between particular neuron upon
 * particular slot. Slot determines from which entry
 * weight or signal is being chosen.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralInternalConnection implements NeuralConnection {
    private final NeuralTraversable neuron;
    /**
     * Slot means an input slot...which is the position
     * on which we push result of the previous layer.
     */
    private final int slot;

    public NeuralInternalConnection(NeuralTraversable neuron, int slot) {
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
    public Double getDelta() {
        return this.neuron.getDelta();
    }
}
