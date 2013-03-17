package org.kornicameister.iad.neuralnet;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.core.NeuralProcessable;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * NeuralNetwork represents network built from
 * {@link Neuron}s organized into {@link NeuralLayer}s.
 * <p/>
 * Implementation based on:
 * <ul>
 * <li><a href="http://edu.pjwstk.edu.pl/wyklady/nai/scb/wyklad3/w3.htm">Link_1</a></li>
 * <li><a href="http://www.neurosoft.edu.pl/jbartman/NTI4.pdf">Link_2</a></li>
 * </ul>
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class NeuralNetwork implements NeuralProcessable {
    private final static Logger LOGGER = Logger.getLogger(NeuralNetwork.class);
    private List<NeuralLayer> layerList = new LinkedList<>();
    private Double[] result;
    private Double[] desiredResult;

    public NeuralNetwork(int size) {
        this.result = new Double[size];
        this.desiredResult = new Double[size];
    }

    public NeuralNetwork(Double[] desiredResult) {
        this.desiredResult = desiredResult;
        this.result = new Double[this.desiredResult.length];
    }

    public Double[] getDesiredResult() {
        return desiredResult;
    }

    public void setDesiredResult(Double[] desiredResult) {
        this.desiredResult = desiredResult;
    }

    public Double[] getResult() {
        return result;
    }

    public void setResult(Double[] result) {
        this.result = result;
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
