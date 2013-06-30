package org.kornicameister.iad.neuralnet.impl.networks;

import com.google.common.primitives.Doubles;
import org.kornicameister.iad.neuralnet.impl.NeuralLayer;
import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;
import org.kornicameister.iad.neuralnet.util.ArraysUtils;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ClassificationNeuralNetwork extends NeuralNetwork {

    public ClassificationNeuralNetwork(final Integer size, final NeuralLayer... layers) {
        super(size, layers);
    }

    public Boolean isClassificationValid() {
        final Integer indexOfOne = Doubles.indexOf(ArraysUtils.toPrimitiveDouble(this.expectedOutput), 1d);
        final Integer indexOfMaxOutput = this.maxOutputIndex();
        return indexOfOne.equals(indexOfMaxOutput);
    }

    private Integer maxOutputIndex() {
        final double[] result = ArraysUtils.toPrimitiveDouble(this.output);
        return Doubles.indexOf(result, new double[]{Doubles.max(result)});
    }

}
