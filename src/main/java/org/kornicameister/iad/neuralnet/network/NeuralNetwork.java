package org.kornicameister.iad.neuralnet.network;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.NeuralProcessable;

import java.util.LinkedList;
import java.util.List;

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

    @Override
    public void teach() {
        for (NeuralLayer layer : this.layerList) {
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
}
