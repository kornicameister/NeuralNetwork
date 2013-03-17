package org.kornicameister.iad.neuralnet.network;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralProcessable;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * NeuralNetwork represents network built from
 * {@link org.kornicameister.iad.neuralnet.neuron.Neuron}s organized into {@link NeuralLayer} with
 * every {@link org.kornicameister.iad.neuralnet.neuron.Neuron} connected with each other by
 * {@link org.kornicameister.iad.neuralnet.neuron.Arc}s
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralNetwork implements NeuralProcessable {
    private final static Logger LOGGER = Logger.getLogger(NeuralNetwork.class);
    private List<NeuralLayer> layerList = new LinkedList<>();
    private Double[] result;

    public NeuralNetwork(int size) {
        this.result = new Double[size];
    }

    /**
     * Teaching in neural network performed in backward
     * way...teaching from output to input.
     */
    @Override
    public void teach() {
        ListIterator<NeuralLayer> iterator = this.layerList.listIterator(this.layerList.size());
        while (iterator.hasPrevious()) {
            final NeuralLayer layer = iterator.previous();
            LOGGER.info(String.format("Teaching layer %s", layer));
            layer.teach();
        }
    }

    @Override
    public void process() {
        for (NeuralLayer layer : this.layerList) {
            LOGGER.info(String.format("Processing layer %s", layer));
            layer.process();
        }
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        for (NeuralLayer layer : this.layerList) {
            layer.initByRandom(lower, higher);
        }
    }

    public boolean addLayer(NeuralLayer neuralLayer) {
        return layerList.add(neuralLayer);
    }

    public NeuralLayer removeLayer(int i) {
        return layerList.remove(i);
    }

    public boolean removeLayer(Object o) {
        return layerList.remove(o);
    }

    public NeuralLayer getLayer(int i) {
        return layerList.get(i);
    }

    public List<NeuralLayer> getLayers() {
        return layerList;
    }

    public void setLayers(List<NeuralLayer> layerList) {
        this.layerList = layerList;
    }
}
