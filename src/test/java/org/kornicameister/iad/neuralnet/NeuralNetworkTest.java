package org.kornicameister.iad.neuralnet;

import org.junit.Test;
import org.kornicameister.iad.neuralnet.function.LinearFunction;
import org.kornicameister.iad.neuralnet.traverse.NeuralOutputConnection;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralNetworkTest {

    @Test
    public void testTeach() throws Exception {
        NeuralNetwork network = new NeuralNetwork(1);
        NeuralLayer layer = new NeuralLayer();

        layer.addNeuron(new Neuron(
                new LinearFunction(1.0, 2.0),
                new Double[]{1.0, 2.0, 3.0},
                new NeuralOutputConnection(network, 0)
        ));

        network.addLayer(layer);
        network.initWithSignal(new Double[]{4.0, 5.0, 6.0});
        network.setDesiredResult(new Double[]{4.0, 5.0, 6.0});
        network.process();
    }
}
