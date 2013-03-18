package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.NeuralNetwork;
import org.kornicameister.iad.neuralnet.core.NeuralConnection;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralOutputConnection implements NeuralConnection {
    private final NeuralNetwork network;
    private final int slot;

    public NeuralOutputConnection(NeuralNetwork network, int slot) {
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
    public Double getDelta() {
        return this.network.getDesiredResult()[this.slot] - this.network.getResult()[this.slot];
    }
}
