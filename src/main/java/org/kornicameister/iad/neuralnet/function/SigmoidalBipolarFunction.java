package org.kornicameister.iad.neuralnet.function;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class SigmoidalBipolarFunction extends SigmoidalUnipolarFunction {
    private static final double DOUBLE = -2.0;

    @Override
    public Double calculate(Double... args) {
        return (DEFAULT_BETA - Math.exp(-this.beta * args[0])) / super.calculate(args);
    }

    @Override
    public Double derivativeCalculate(Double arg) {
        return DOUBLE * ((this.beta * Math.exp(arg * this.beta)) / (Math.pow(DOUBLE, Math.exp(this.beta * arg) + 1)));
    }
}
