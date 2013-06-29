package org.kornicameister.iad.neuralnet.function;

public class IdentityFunction extends LinearFunction {
    private static final double DOUBLE = 1.0;

    public IdentityFunction(Double x) {
        super(x, 0d);
    }

    @Override
    public Double calculate(Double... args) {
        return this.aConstant;
    }

    @Override
    public Double derivativeCalculate(Double arg) {
        return DOUBLE;
    }

    @Override
    public String toString() {
        return "IdentityFunction{" +
                "aConstant=" + aConstant +
                ", bConstant=" + bConstant +
                "} " + super.toString();
    }
}
