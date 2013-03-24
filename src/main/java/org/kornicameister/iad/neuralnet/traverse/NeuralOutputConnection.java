package org.kornicameister.iad.neuralnet.traverse;

import org.kornicameister.iad.neuralnet.NeuralNetwork;
import org.kornicameister.iad.neuralnet.core.AbstractConnection;

/**
 * Class represents connection
 * between neural network and
 * neurons.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralOutputConnection extends AbstractConnection {
    private final NeuralNetwork network;

    public NeuralOutputConnection(NeuralNetwork network, int slot) {
        super(slot);
        this.network = network;
    }

    @Override
    public void pushResultForward(Double result) {
        this.network.getResult()[this.getSlot()] = result;
    }

    @Override
    public Double getResultBackward() {
        return this.network.getResult()[this.getSlot()];
    }

    @Override
    public Double getDelta() {
        return this.network.getDesiredResult()[this.getSlot()] - this.network.getResult()[this.getSlot()];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{network=").append(network.getSize());
        sb.append(", slot=").append(this.getSlot());
        sb.append('}');
        return sb.toString();
    }
}
