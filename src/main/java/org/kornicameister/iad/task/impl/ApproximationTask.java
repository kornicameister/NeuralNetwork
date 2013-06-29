package org.kornicameister.iad.task.impl;

import com.google.common.base.Preconditions;
import org.kornicameister.iad.neuralnet.factory.NeuralFactory;
import org.kornicameister.iad.neuralnet.function.IdentityFunction;
import org.kornicameister.iad.neuralnet.function.SigmoidalUnipolarFunction;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;
import org.kornicameister.iad.neuralnet.impl.layers.InputNeuralLayer;
import org.kornicameister.iad.neuralnet.impl.layers.OutputNeuralLayer;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;
import org.kornicameister.iad.task.DefaultTask;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ApproximationTask extends DefaultTask {
    private final Integer neuronsInHidden;

    public ApproximationTask(final Integer neurons) {
        Preconditions.checkArgument(neurons != null);
        Preconditions.checkArgument(neurons >= 1);
        this.neuronsInHidden = neurons;
    }

    @Override
    public NeuralNetwork buildNetwork() {
        final double learningFactor = 0.0000001;
        final double momentumRate = learningFactor / 2.0;
        final double higher = 0.9;
        final double lower = -higher;
        final int inHidden = 12;
        final int startInHidden = 12;
        final int epochs = (int) Math.pow(10, 4);
        final double aConstant = 0.7;
        final double biasWeight = 1.0;
        final double beta = 0.1;

        final InputNeuralLayer inputLayer = new InputNeuralLayer();
        final OutputNeuralLayer outputLayer = new OutputNeuralLayer();
        final NeuralLayer hiddenLayer = new NeuralLayer();

        inputLayer.setUpperLayer(hiddenLayer).addNeuron(NeuralFactory.Neurons.newBiasNeuron(
                new IdentityFunction(aConstant),
                momentumRate,
                learningFactor,
                biasWeight,
                ArraysUtils.newRandomDoubleArray(this.neuronsInHidden, lower, higher)));
        hiddenLayer.setUpperLayer(outputLayer);

        for (int it = 0; it < this.neuronsInHidden; it++) {
            hiddenLayer.addNeuron(NeuralFactory.Neurons.newBiasNeuron(
                    new SigmoidalUnipolarFunction(beta),
                    momentumRate,
                    learningFactor,
                    biasWeight,
                    ArraysUtils.newRandomDoubleArray(1, lower, higher))
            );
        }

        this.neuralNetwork = new NeuralNetwork(inputLayer, outputLayer, hiddenLayer);

        return this.neuralNetwork;
    }

    @Override
    public void compute() {

    }

    @Override
    public void saveResult() {

    }

    @Override
    public void readData() {

    }
}
