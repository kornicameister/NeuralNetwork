package org.kornicameister.iad.neuralnet.report;

import org.junit.Before;
import org.junit.Test;
import org.kornicameister.iad.neuralnet.NeuralLayer;
import org.kornicameister.iad.neuralnet.NeuralNetwork;
import org.kornicameister.iad.neuralnet.Neuron;
import org.kornicameister.iad.neuralnet.RandomFunny;
import org.kornicameister.iad.neuralnet.function.LinearFunction;
import org.kornicameister.iad.neuralnet.function.SigmoidalUnipolarFunction;
import org.kornicameister.iad.neuralnet.traverse.NeuralInternalConnection;
import org.kornicameister.iad.neuralnet.traverse.NeuralOutputConnection;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class NeuralNetworkTestApproximation {
    private static final String DATA_DIR = "report/approximation";
    private List<Double[]> testDataList = new ArrayList<>();
    private List<Double[]> dataOneList = new ArrayList<>();
    private List<Double[]> dataTwoList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        File dir = new File(DATA_DIR);
        if (dir.isDirectory()) {
            File testSet = new File(String.format("%s/%s", DATA_DIR, "approximation_test.txt"));
            File dataSet1 = new File(String.format("%s/%s", DATA_DIR, "approximation_train_1.txt"));
            File dataSet2 = new File(String.format("%s/%s", DATA_DIR, "approximation_train_2.txt"));
            System.out.println(String.format("Data files [%s, %s, %s]", testSet.getName(), dataSet1.getName(), dataSet2.getName()));

            Scanner scanner = new Scanner(testSet);
            while (scanner.hasNext()) {
                testDataList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

            scanner = new Scanner(dataSet1);
            while (scanner.hasNext()) {
                dataOneList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

            scanner = new Scanner(dataSet2);
            while (scanner.hasNext()) {
                dataTwoList.add(new Double[]{Double.valueOf(scanner.next()), Double.valueOf(scanner.next())});
            }
            scanner.close();

        } else {
            System.err.println("DATA_DIR not dir");
        }
    }

    @Test
    public void testApproximation() throws Exception {
        final double learningFactor = 0.001;
        final double momentumRate = 0.00001;
        final double higher = 0.4;
        final double lower = -higher;
        final int inHidden = 1;
        final int startInHidden = 1;
        final int epochs = 5000;

        for (int i = startInHidden; i <= inHidden; i++) {
            System.out.println(String.format("Neurons in hidden layers, count=%d", i));

            Neuron outNeuron = new Neuron(
                    true,
                    new LinearFunction(0.7),
                    this.getWeightByHiddenLayersSize(i, lower, higher)
            );

            outNeuron.setLearningFactor(learningFactor);
            outNeuron.setMomentumRate(momentumRate);
            outNeuron.setBiasWeight(this.getRandomDouble(lower, higher));

            NeuralLayer neuralLayer = new NeuralLayer();
            NeuralNetwork network = new NeuralNetwork(1);

            for (int n = 0; n < i; n++) {
                Neuron hidden = new Neuron(
                        true,
                        new SigmoidalUnipolarFunction(this.getRandomDouble(0.1, 1.0)),
                        this.getWeightByHiddenLayersSize(1, lower, higher)
                );
                hidden.setLearningFactor(learningFactor);
                hidden.setMomentumRate(momentumRate);
                hidden.setBiasWeight(this.getRandomDouble(lower, higher));

                for (int k = 0; k <= n; k++) {
                    hidden.addConnection(new NeuralInternalConnection(outNeuron, k));
                }
                neuralLayer.addNeuron(hidden);
            }
            outNeuron.addConnection(new NeuralOutputConnection(network, 0));
            network.pushLayer(new NeuralLayer(outNeuron));
            network.pushLayer(neuralLayer);

            System.out.println(this.visualizeNetwork(network));

            // go through test data
            int training = 0;
            PrintWriter errWriter = new PrintWriter(new File(String.format("%s/errors/err_for_%d_in_hidden.txt", DATA_DIR, i)));
            while ((training++) < epochs) {
                Double error = 0.0;
                for (int i1 = 0; i1 < this.dataOneList.size(); i1++) {
                    Double[] doubles = this.dataOneList.get(i1);
                    network.initWithSignal(new Double[]{doubles[0]});
                    network.setDesiredResult(new Double[]{doubles[1]});
                    network.feedForward();
                    network.feedBackward();
                }
                for (int i1 = 0; i1 < this.dataTwoList.size(); i1++) {
                    Double[] doubles = this.dataTwoList.get(i1);
                    network.initWithSignal(new Double[]{doubles[0]});
                    network.setDesiredResult(new Double[]{doubles[1]});
                    network.feedForward();
                    network.feedBackward();
                }
                for (Double[] aTestDataList : this.testDataList) {
                    network.initWithSignal(new Double[]{aTestDataList[0]});
                    network.setDesiredResult(new Double[]{aTestDataList[1]});
                    network.feedForward();
                    error += network.calculateError();
                }
                error = error / this.testDataList.size();
                errWriter.print(training - 1);
                errWriter.print(" ");
                errWriter.print(error);
                errWriter.println();
                System.out.println(String.format("Epoch %d with [ data ][ error ] [ %f ] [ %f ]", training - 1, network.getResult()[0], error));
            }
            PrintWriter dataWriter = new PrintWriter(new File(String.format("%s/data/data_for_%d_in_hidden.txt", DATA_DIR, i)));
            for (Double[] aTestDataList : this.testDataList) {
                network.initWithSignal(new Double[]{aTestDataList[0]});
                network.feedForward();
                dataWriter.print(aTestDataList[0]);
                dataWriter.print(" ");
                dataWriter.print(network.getResult()[0]);
                dataWriter.println();
            }
            dataWriter.close();
            errWriter.close();
        }
    }

    private Double[] getWeightByHiddenLayersSize(final int size, final double lower, final double higher) {
        Double[] weight = new Double[size];
        for (int i = 0; i < size; i++) {
            weight[i] = this.getRandomDouble(lower, higher);
        }
        return weight;
    }

    private double getRandomDouble(final double start, final double end) {
        return RandomFunny.doubleBetween(start, end);
    }

    private String visualizeNetwork(final NeuralNetwork network) {
        StringBuilder sb = new StringBuilder("Network").append("\n");
        List<NeuralLayer> layers = network.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            NeuralLayer layer = layers.get(i);
            sb.append(String.format("Layer %d", i + 1)).append("\n\t");
            for (Neuron neuron : layer.getNeurons()) {
                sb.append("\t").append("*N*");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
