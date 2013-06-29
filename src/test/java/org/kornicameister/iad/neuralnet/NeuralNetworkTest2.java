package org.kornicameister.iad.neuralnet;

import org.junit.Test;
import org.kornicameister.iad.neuralnet.function.SigmoidalUnipolarFunction;
import org.kornicameister.iad.neuralnet.traverse.InternalArc;
import org.kornicameister.iad.neuralnet.traverse.OutputArc;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;

import java.io.*;
import java.util.Arrays;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralNetworkTest2 {
    final boolean biasEnabled = false;

    @Test
    public void testProcessFromFile() throws Exception {
        Double[][] inputs = this.readInputFromFile();
        NeuralNetwork neuralNetwork = this.readNetworkFromFile();
        PrintWriter out = new PrintWriter(new File("task_26_03/out.txt"));
        for (int i = 0; i < inputs.length; i++) {
            neuralNetwork.initWithSignal(inputs[i]);
            neuralNetwork.feedForward();
            for (Double d : neuralNetwork.getResult()) {
                out.print(d);
                out.print(" ");
            }
            out.println();
        }
        out.close();
    }

    private Double[][] readInputFromFile() {
        Double[][] inputs = new Double[4][4];
        int input = 0;
        try {
            FileInputStream fstream = new FileInputStream("task_26_03/in.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] line = strLine.split(" ");
                for (int i = 0; i < 4; i++) {
                    inputs[input][i] = Double.parseDouble(line[i]);
                }
                input++;
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println(Arrays.deepToString(inputs));
        return inputs;
    }

    private NeuralNetwork readNetworkFromFile() {
        final NeuralNetwork network = new NeuralNetwork(4);
        final SigmoidalUnipolarFunction function = new SigmoidalUnipolarFunction(1.0);

        Neuron neuron_11 = new Neuron(biasEnabled, function, new Double[]{0.472801, 0.935628, -0.491067, 0.372298});
        Neuron neuron_12 = new Neuron(biasEnabled, function, new Double[]{-0.100727, 0.297812, -0.615205, -0.153709});
        neuron_11.setBiasWeight(0.0);
        neuron_12.setBiasWeight(0.0);

        Neuron neuron_21 = new Neuron(biasEnabled, function, new Double[]{0.346888, 0.484956});
        Neuron neuron_22 = new Neuron(biasEnabled, function, new Double[]{0.654139, -0.961072});
        Neuron neuron_23 = new Neuron(biasEnabled, function, new Double[]{0.872161, 0.603206});
        Neuron neuron_24 = new Neuron(biasEnabled, function, new Double[]{0.665244, -0.489092});
        neuron_21.setBiasWeight(0.0);
        neuron_22.setBiasWeight(0.0);
        neuron_23.setBiasWeight(0.0);
        neuron_24.setBiasWeight(0.0);

        neuron_11.addConnection(new InternalArc(neuron_21, 0));
        neuron_12.addConnection(new InternalArc(neuron_21, 1));

        neuron_11.addConnection(new InternalArc(neuron_22, 0));
        neuron_12.addConnection(new InternalArc(neuron_22, 1));

        neuron_11.addConnection(new InternalArc(neuron_23, 0));
        neuron_12.addConnection(new InternalArc(neuron_23, 1));

        neuron_11.addConnection(new InternalArc(neuron_24, 0));
        neuron_12.addConnection(new InternalArc(neuron_24, 1));

        neuron_21.addConnection(new OutputArc(network, 0));
        neuron_22.addConnection(new OutputArc(network, 1));
        neuron_23.addConnection(new OutputArc(network, 2));
        neuron_24.addConnection(new OutputArc(network, 3));


        NeuralLayer layer_1 = new NeuralLayer(neuron_11, neuron_12);
        NeuralLayer layer_2 = new NeuralLayer(neuron_21, neuron_22, neuron_23, neuron_24);

        network.pushLayer(layer_2);
        network.pushLayer(layer_1);

        return network;
    }

    @Test
    public void testTeachFromFile() throws Exception {
        Double[][] inputs = this.readInputFromFile();
        NeuralNetwork neuralNetwork = this.readNetworkFromFile();

        PrintWriter errWriter = new PrintWriter(new File("task_26_03/err.txt"));
        PrintWriter tWriter = new PrintWriter(new File(String.format("task_26_03/tt_bias_%s.txt", biasEnabled)));

        int training = 0;
        while ((training++) < 1000) {
            Double error = 0.0;

            tWriter.println("Iteration " + (training));
            for (int i = 0; i < inputs.length; i++) {
                neuralNetwork.initWithSignal(inputs[i]);
                neuralNetwork.setDesiredResult(inputs[i]);
                neuralNetwork.feedForward();
                neuralNetwork.feedBackward();

                tWriter.println((i + 1) + " :: " + Arrays.toString(inputs[i]));
                for (Neuron neuron : neuralNetwork.getLayer(0).getNeurons()) {
                    tWriter.println("\t" + neuron.computeOutput());
                }
                tWriter.println();
                tWriter.print("\t" + Arrays.toString(neuralNetwork.getResult()));
                tWriter.println();
            }
            tWriter.println("\n-----------------");
            Double[][] inputsCloned = ArraysUtils.shuffle(inputs);
            for (int i = 0; i < inputsCloned.length; i++) {
                neuralNetwork.initWithSignal(inputsCloned[i]);
                neuralNetwork.setDesiredResult(inputsCloned[i]);
                neuralNetwork.feedForward();
                error += neuralNetwork.calculateError();
            }
            errWriter.print(training - 1);
            errWriter.print(" ");
//            errWriter.print(String.format("%.6f",error * 0.25));
            errWriter.print(error / 4.0);
            errWriter.println();

        }
        errWriter.close();
        tWriter.close();
        this.dumpNetworkToFile(neuralNetwork);
    }

    private void dumpNetworkToFile(NeuralNetwork neuralNetwork) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File("task_26_03/network_out.txt"));

        out.println(neuralNetwork.getSize() - 2);
        out.println(neuralNetwork.getLayer(0).getNeuron(0).getSize());
        for (NeuralLayer layer : neuralNetwork.getLayers()) {
            out.println(layer.getSize());
        }
        for (NeuralLayer layer : neuralNetwork.getLayers()) {
            for (Neuron neuron : layer.getNeurons()) {
                out.print(neuron.getBiasWeight());
                out.print(" ");
                for (int i = 0; i < neuron.getSize(); i++) {
                    out.print(neuron.getWeights()[i]);
                    out.print(" ");
                }
                out.println();
            }
        }
        out.close();
    }
}
