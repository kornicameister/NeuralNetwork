package org.kornicameister.iad.neuralnet.backend;

import org.kornicameister.iad.neuralnet.NeuralProcessable;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
abstract public class _NeuralNetwork implements NeuralProcessable {
    protected List<NeuralLayer> layerList = new LinkedList<>();

    protected _NeuralNetwork(NeuralLayer... layerList) {
        this.layerList.addAll(Arrays.asList(layerList));
    }

    public boolean addLayer(NeuralLayer neuralLayer) {
        return layerList.add(neuralLayer);
    }

    public void pushLayer(NeuralLayer neuralLayer) {
        this.layerList.add(0, neuralLayer);
    }

    public NeuralLayer getLayer(int layerIndex) {
        return layerList.get(layerIndex);
    }

    public List<NeuralLayer> getLayers() {
        return layerList;
    }

    @Override
    public String toString() {
        return "_NeuralNetwork{" +
                "layerList=" + this.layerList.size() +
                "} " + super.toString();
    }
}
