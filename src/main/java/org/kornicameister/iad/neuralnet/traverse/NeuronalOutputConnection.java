package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.NeuralNetwork;
import org.kornicameister.iad.neuralnet.core.NeuronalConnectible;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuronalOutputConnection implements NeuronalConnectible {
    private final NeuralNetwork network;
    private final int slot;

    public NeuronalOutputConnection(NeuralNetwork network, int slot) {
        this.network = network;
        this.slot = slot;
    }

    @Override
    public void pushResultForward(Double result) {
        this.network.getResult()[this.slot] = result;
    }

    @Override
    public Double getResultBackward() {
        return this.network.getResult()[this.slot];
    }

    @Override
    public Double getTeachingDiff() {
        return this.network.getDesiredResult()[this.slot] - this.network.getResult()[this.slot];
    }
}
