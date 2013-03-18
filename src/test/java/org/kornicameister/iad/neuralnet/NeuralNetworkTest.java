package org.kornicameister.iad.neuralnet;

import org.junit.Test;
import org.kornicameister.iad.neuralnet.function.LinearFunction;
import org.kornicameister.iad.neuralnet.traverse.NeuralInternalConnection;
import org.kornicameister.iad.neuralnet.traverse.NeuralOutputConnection;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralNetworkTest {

    @Test
    public void testOne() throws Exception {
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

    @Test
    public void testTwo() throws Exception {
        NeuralNetwork network = new NeuralNetwork(1);

        Neuron neuron1 = new Neuron(new LinearFunction(1.0, 2.0));
        neuron1.setWeights(1.0, 2.0, 3.0, 4.0);
        Neuron neuron2 = new Neuron(new LinearFunction(-0.4, 2.0));
        neuron2.setWeights(-1.0);
        Neuron neuron3 = new Neuron(new LinearFunction(-1.1, 2.0));
        neuron3.setWeights(0.01);
        Neuron neuron4 = new Neuron(new LinearFunction(0.001, 2.0));
        neuron4.setWeights(0.5);

        neuron4.addConnection(new NeuralOutputConnection(network, 0));
        neuron3.addConnection(new NeuralInternalConnection(neuron4, 0));
        neuron2.addConnection(new NeuralInternalConnection(neuron3, 0));
        neuron1.addConnection(new NeuralInternalConnection(neuron2, 0));

        network.pushLayer(new NeuralLayer(neuron4));
        network.pushLayer(new NeuralLayer(neuron3));
        network.pushLayer(new NeuralLayer(neuron2));
        network.pushLayer(new NeuralLayer(neuron1));

        network.initWithSignal(new Double[]{4.0, 5.0, 0.05, 6.0});
        network.process();
    }

    @Test
    public void testThree() throws Exception {
        NeuralNetwork network = new NeuralNetwork(1);

        Neuron neuron1 = new Neuron(new LinearFunction(1.0, 2.0));
        neuron1.setWeights(1.0, 2.0, 3.0, 4.0);
        Neuron neuron2 = new Neuron(new LinearFunction(-0.4, 2.0));
        neuron2.setWeights(-1.0, 2.014, -0.6, 0.2);
        Neuron neuron3 = new Neuron(new LinearFunction(-1.1, 2.0));
        neuron3.setWeights(0.01, 0.2);
        Neuron neuron4 = new Neuron(new LinearFunction(0.001, 2.0));
        neuron4.setWeights(0.5);

        neuron4.addConnection(new NeuralOutputConnection(network, 0));
        neuron3.addConnection(new NeuralInternalConnection(neuron4, 0));
        neuron2.addConnection(new NeuralInternalConnection(neuron3, 0));
        neuron1.addConnection(new NeuralInternalConnection(neuron3, 1));

        network.pushLayer(new NeuralLayer(neuron4));
        network.pushLayer(new NeuralLayer(neuron3));
        network.pushLayer(new NeuralLayer(neuron1, neuron2));

        network.initWithSignal(new Double[]{4.0, 5.0, 0.05, 6.0});
        network.process();
    }
}
