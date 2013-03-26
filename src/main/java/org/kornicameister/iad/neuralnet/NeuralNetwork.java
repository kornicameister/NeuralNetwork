package org.kornicameister.iad.neuralnet;

import org.apache.log4j.Logger;
import org.kornicameister.iad.neuralnet.core.NeuralProcessable;
import org.kornicameister.iad.neuralnet.impl._NeuralNetwork;

import java.util.Arrays;
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
public class NeuralNetwork extends _NeuralNetwork implements NeuralProcessable {
    private final static Logger LOGGER = Logger.getLogger(NeuralNetwork.class);
    private Double[] result;
    private Double[] desiredResult;

    public NeuralNetwork(int size) {
        this.result = new Double[size];
        this.desiredResult = new Double[size];
        for (int i = 0; i < size; i++) {
            this.result[i] = 0.0;
            this.desiredResult[i] = 0.0;
        }
    }

    public NeuralNetwork(Double[] desiredResult) {
        this.desiredResult = desiredResult;
        this.result = new Double[this.desiredResult.length];
        for (int i = 0; i < desiredResult.length; i++) {
            this.result[i] = 0.0;
        }
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
    public void feedBackward() {
        ListIterator<NeuralLayer> iterator = this.layerList.listIterator(this.layerList.size());
        while (iterator.hasPrevious()) {
            final NeuralLayer layer = iterator.previous();
            layer.feedBackward();
        }
    }

    @Override
    public void feedForward() {
        for (NeuralLayer layer : this.layerList) {
            layer.feedForward();
        }
        LOGGER.info(String.format("Processed and computed with result=%s", Arrays.deepToString(this.result)));
    }

    @Override
    public void initByRandom(Double lower, Double higher) {
        for (NeuralLayer layer : this.layerList) {
            layer.initByRandom(lower, higher);
        }
    }

    public void initWithSignal(Double[] signal) {
        for (Neuron neuron : this.layerList.get(0).getNeurons()) {
            neuron.setInput(signal.clone());
        }
    }

    public void setSize(int size) {
        this.result = new Double[size];
        this.desiredResult = new Double[size];
    }

    public Integer getSize() {
        return this.result.length;
    }

    public Double calculateError() {
        Double err = 0.0;
        for (int i = 0; i < this.desiredResult.length; i++) {
            err += Math.pow(this.desiredResult[i] - this.result[i], 2);
        }
        return err / 2.0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NeuralNetwork{");
        sb.append("result=").append(Arrays.toString(result));
        sb.append(", layerList=").append(layerList);
        sb.append(", desiredResult=").append(Arrays.toString(desiredResult));
        sb.append('}');
        return sb.toString();
    }


}
