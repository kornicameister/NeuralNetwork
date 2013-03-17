package org.kornicameister.iad.neuralnet.function;

/**
 * Template for methods that can be
 * implemented within neuron. Comes with
 * two method:
 * <ol>
 * <li>
 * {@link Functional#calculate(Double...)} - calculates value
 * from range of input values
 * </li>
 * <li>
 * {@link Functional#derivativeCalculate(Double)} - implements derivative
 * version on calculate method
 * </li>
 * </ol>
 *
 * @author kornicameister
 * @since 0.0.1
 */
public interface Functional {
    Double calculate(Double... args);

    Double derivativeCalculate(Double arg);
}
