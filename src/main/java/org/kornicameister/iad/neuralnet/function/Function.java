package org.kornicameister.iad.neuralnet.function;

/**
 * Template for methods that can be
 * implemented within neuron. Comes with
 * two method:
 * <ol>
 * <li>
 * {@link Function#calculate(Double...)} - calculates value
 * from range of input values
 * </li>
 * <li>
 * {@link Function#derivativeCalculate(Double)} - implements derivative
 * version on calculate method
 * </li>
 * </ol>
 *
 * @author kornicameister
 * @since 0.0.1
 */
public abstract class Function {
    public abstract Double calculate(Double... args);

    public abstract Double derivativeCalculate(Double arg);
}
