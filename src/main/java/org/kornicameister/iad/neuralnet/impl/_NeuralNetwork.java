package org.kornicameister.iad.neuralnet.impl;

import org.kornicameister.iad.neuralnet.NeuralLayer;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class _NeuralNetwork {
    protected List<NeuralLayer> layerList = new LinkedList<>();

    public boolean addLayer(NeuralLayer neuralLayer) {
        return layerList.add(neuralLayer);
    }

    public void pushLayer(NeuralLayer neuralLayer) {
        this.layerList.add(0, neuralLayer);
    }

    public NeuralLayer removeLayer(int layerIndex) {
        return layerList.remove(layerIndex);
    }

    public boolean removeLayer(Object object) {
        return layerList.remove(object);
    }

    public NeuralLayer getLayer(int layerIndex) {
        return layerList.get(layerIndex);
    }

    public List<NeuralLayer> getLayers() {
        return layerList;
    }

    public void setLayers(List<NeuralLayer> layerList) {
        this.layerList = layerList;
    }
}
