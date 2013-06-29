package org.kornicameister.iad.neuralnet.backend;

import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.layers.InputNeuralLayer;
import org.kornicameister.iad.neuralnet.impl.layers.OutputNeuralLayer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
abstract public class _NeuralNetwork implements NeuralProcessable {
    protected final InputNeuralLayer inputNeuralLayer;
    protected final OutputNeuralLayer outputNeuralLayer;
    protected List<NeuralLayer> hiddenLayers = new LinkedList<>();

    protected _NeuralNetwork(final InputNeuralLayer inputNeuralLayer, final OutputNeuralLayer outputNeuralLayer, NeuralLayer... hiddenLayers) {
        this.inputNeuralLayer = inputNeuralLayer;
        this.outputNeuralLayer = outputNeuralLayer;
        this.hiddenLayers.addAll(Arrays.asList(hiddenLayers));
    }

    public boolean addHiddenLayer(NeuralLayer neuralLayer) {
        return hiddenLayers.add(neuralLayer);
    }

    public void pushLayer(NeuralLayer neuralLayer) {
        this.hiddenLayers.add(0, neuralLayer);
    }

    public NeuralLayer getLayer(int layerIndex) {
        return hiddenLayers.get(layerIndex);
    }

    public List<NeuralLayer> getLayers() {
        return hiddenLayers;
    }

    @Override
    public String toString() {
        return "_NeuralNetwork{" +
                "hiddenLayers=" + hiddenLayers +
                "} " + super.toString();
    }
}
