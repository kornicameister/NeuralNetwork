package org.kornicameister.iad.neuralnet.core;

/**
 * Class represent abstract connection
 * existing in neural network regardless
 * of its type.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public abstract class AbstractConnection implements NeuralConnection {
    /**
     * Slot means an input slot...which is the position
     * on which we push result of the previous layer.
     */
    private int slot;

    protected AbstractConnection(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractConnection{");
        sb.append("slot=").append(slot);
        sb.append('}');
        return sb.toString();
    }
}
