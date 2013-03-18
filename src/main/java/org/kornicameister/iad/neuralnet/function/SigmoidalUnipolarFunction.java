package org.kornicameister.iad.neuralnet.function;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class SigmoidalUnipolarFunction extends Function {
    protected static final double DEFAULT_BETA = 1.0;
    protected Double beta;

    public SigmoidalUnipolarFunction() {
        this(DEFAULT_BETA);
    }

    public SigmoidalUnipolarFunction(Double beta) {
        this.beta = beta;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    @Override
    public Double calculate(Double... args) {
        return DEFAULT_BETA / (DEFAULT_BETA + Math.exp(-this.beta * args[0]));
    }

    @Override
    public Double derivativeCalculate(Double arg) {
        final Double calculatedDouble = this.calculate(arg);
        return calculatedDouble * (1 - calculatedDouble);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SigmoidalUnipolarFunction{");
        sb.append("beta=").append(beta);
        sb.append('}');
        return sb.toString();
    }
}
