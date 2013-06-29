package org.kornicameister.iad.neuralnet.function;

/**
 * Implementation of linear function. Linear function has
 * following equation <pre>f(x) = ax + b</pre>
 * therefore this class implements such equation.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class LinearFunction extends Function {
    protected final Double aConstant;
    protected final Double bConstant;

    public LinearFunction(Double aConstant) {
        this(aConstant, 0.0);
    }

    public LinearFunction(Double aConstant, Double bConstant) {
        this.aConstant = aConstant;
        this.bConstant = bConstant;
    }

    public Double getAConstant() {
        return aConstant;
    }

    public Double getBConstant() {
        return bConstant;
    }

    @Override
    public Double calculate(Double... args) {
        return this.aConstant * args[0] + this.bConstant;
    }

    @Override
    public Double derivativeCalculate(Double arg) {
        return this.aConstant;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinearFunction{");
        sb.append("bConstant=").append(bConstant);
        sb.append(", aConstant=").append(aConstant);
        sb.append('}');
        return sb.toString();
    }
}
