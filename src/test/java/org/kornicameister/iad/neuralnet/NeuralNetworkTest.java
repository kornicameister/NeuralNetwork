package org.kornicameister.iad.neuralnet;

import org.junit.Test;
import org.kornicameister.iad.neuralnet.function.LinearFunction;
import org.kornicameister.iad.neuralnet.function.SigmoidalUnipolarFunction;
import org.kornicameister.iad.neuralnet.traverse.NeuralInternalConnection;
import org.kornicameister.iad.neuralnet.traverse.NeuralOutputConnection;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

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

    /**
     * Network spec:
     * - at least 3 layers with at least 5 neurons per layer
     * - neurons count generated randomly per layer
     * - weights generated randomly [-1/2,1/2]
     * - 1 pass
     * - randomly generated 5 sets of randomly generated data
     * </br>
     * <h1>Task</h1>
     * <ol>
     * <li>Download input data from edu <b>in.txt</b></li>
     * <li>Save output to out.txt</li>
     * <li>Network txt format
     * <ol>
     * <li>amount of layers</li>
     * <li>amount of neurons in layer per lines</li>
     * <li>in the same line weights for each neuron in layer</li>
     * <li>dot as decimal separator</li>
     * </ol>
     * </li>
     * </ol>
     *
     * @throws Exception
     */
    @Test
    public void testFour() throws Exception {
        Random seed = new Random(System.nanoTime());
        PrintWriter out = new PrintWriter(new File("network.txt"));
        final int layersCount = 3;
        final int minimumNeurons = 5;
        int inputCountInFirstLayer = seed.nextInt(minimumNeurons);

        out.println(layersCount);
        out.println(inputCountInFirstLayer);

        //generate signal
        Double[][] randomSignal = new Double[5][inputCountInFirstLayer];
        for (int i = 0; i < randomSignal.length; i++) {
            for (int j = 1; j < randomSignal[i].length; j++) {
                randomSignal[i][j] = seed.nextDouble();
            }
            randomSignal[i][0] = 1.0;
        }

        NeuralLayer[] layers = new NeuralLayer[layersCount];
        NeuralNetwork network = new NeuralNetwork(0);
        Neuron[] neurons;
        // 1. generated neurons for each layers
        int outputTapped = 0;
        for (int l = 0; l < layersCount; l++) {
            int neuronsInLayer = minimumNeurons + (int) (Math.random() * (minimumNeurons * 3));
            out.println(neuronsInLayer);
            neurons = new Neuron[neuronsInLayer];

            for (int n = 0; n < neuronsInLayer; n++) {
                Neuron neuron = new Neuron(new SigmoidalUnipolarFunction(1.0));
                if (l == 0) {
                    neuron.setSize(inputCountInFirstLayer);
                } else if (l != layersCount - 1) {
                    neuron.setSize(layers[l - 1].getSize());
                    for (int nn = 0; nn < neuron.getSize(); nn++) {
                        layers[l - 1].getNeuron(nn).addConnection(new NeuralInternalConnection(neuron, nn));
                    }
                } else {
                    neuron.setSize(layers[l - 1].getSize());
                    for (int nn = 0; nn < neuron.getSize(); nn++) {
                        layers[l - 1].getNeuron(nn).addConnection(new NeuralInternalConnection(neuron, nn));
                    }
                    neuron.addConnection(new NeuralOutputConnection(network, outputTapped++));
                }
                neurons[n] = neuron;
            }
            layers[l] = new NeuralLayer(neurons);
        }
        network.setSize(layers[layers.length - 1].getSize());
        for (NeuralLayer layer : layers) {
            network.addLayer(layer);
        }
        network.initByRandom(-0.5, 0.5);

        for (NeuralLayer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                for (int i = 0; i < neuron.getWeights().length; i++) {
                    out.print(String.format("%f ", neuron.getWeights()[i]));
                }
                out.println();
            }
        }
        out.close();

        // generate drop to file
        out = new PrintWriter(new File("in.txt"));
        for (int i = 0; i < randomSignal.length; i++) {
            for (int j = 0; j < randomSignal[i].length; j++) {
                out.print(String.format("%f ", randomSignal[i][j]));
            }
            out.println();
        }
        out.close();

        out = new PrintWriter(new File("out.txt"));
        for (int i = 0; i < randomSignal.length; i++) {
            network.initWithSignal(randomSignal[i]);
            network.process();
            for (int j = 0; j < network.getResult().length; j++) {
                out.print(String.format("%f ", network.getResult()[j]));
            }
            out.println();
        }
        out.close();
    }
}
