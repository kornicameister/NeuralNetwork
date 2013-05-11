package org.kornicameister.iad.neuralnet.function;

public class IdentityFunction extends Function {
    private static final double DOUBLE = 1.0;
    private final Double x;

    public IdentityFunction(Double x) {
        this.x = x;
    }

    @Override
    public Double calculate(Double... args) {
        return this.x;
    }

    @Override
    public Double derivativeCalculate(Double arg) {
        return DOUBLE;
    }
}
