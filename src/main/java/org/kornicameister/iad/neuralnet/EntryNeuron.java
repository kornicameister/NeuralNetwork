package org.kornicameister.iad.neuralnet;

import org.kornicameister.iad.neuralnet.core.NeuralConnection;
import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.core.NeuralTraversable;
import org.kornicameister.iad.neuralnet.function.Function;

public class EntryNeuron extends Neuron implements NeuralProcessable, NeuralTraversable {

    public EntryNeuron(Boolean biasEnabled, Function activationFunction) {
        super(biasEnabled, activationFunction);
    }

    public EntryNeuron(Boolean biasEnabled, Function function, Double[] weights, NeuralConnection... connections) {
        super(biasEnabled, function, weights, connections);
    }

    @Override
    public void feedBackward() {
    }
}
