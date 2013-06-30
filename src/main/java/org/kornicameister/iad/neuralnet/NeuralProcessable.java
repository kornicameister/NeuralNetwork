package org.kornicameister.iad.neuralnet;

/**
 * Interface showing how each significant brick
 * of the network works. There is no difference when
 * comes to processing the network as each layer and
 * neuron in that layer must be processed, taught or initialized.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public interface NeuralProcessable {
    /**
     * Use this method to process input of neural network.
     */
    NeuralProcessable process();

    NeuralProcessable setSignal(Double... signal);

    /**
     * Retrieve an output from the processable instance.
     * What this method returns differs for each type.
     * For example. Whether the {@link org.kornicameister.iad.neuralnet.impl.Neuron} returns
     * an array which has always 1 element, the {@link org.kornicameister.iad.neuralnet.impl.NeuralLayer}
     * will always return an array with as many elements as it's
     * {@link org.kornicameister.iad.neuralnet.impl.NeuralNetwork#getSize()}
     *
     * @return output as an array
     */
    Double[] getOutput();

    /**
     * Returns size of the processable instance. The behaviour is different for each processable type.
     * For example this method will return amount of weights/signal in the
     * {@link org.kornicameister.iad.neuralnet.impl.Neuron},
     * but for {@link org.kornicameister.iad.neuralnet.impl.NeuralLayer}
     * it will return the amount of layers the network has.
     *
     * @return size of processable type
     */
    Integer getSize();
}
