package org.kornicameister.iad.neuralnet.function;

import org.apache.log4j.Logger;

/**
 * Implementation of linear function. Linear function has
 * following equation <pre>f(x) = ax + b</pre>
 * therefore this class implements such equation.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class LinearFunction extends Function {
    private final static Logger LOGGER = Logger.getLogger(LinearFunction.class);
    private Double aConstant;
    private Double bConstant;

    public LinearFunction(Double aConstant) {
        this(aConstant, 0.0);
    }

    public LinearFunction(Double aConstant, Double bConstant) {
        this.aConstant = aConstant;
        this.bConstant = bConstant;
        LOGGER.info(String.format("Initialized::%s", this));
    }

    public Double getaConstant() {
        return aConstant;
    }

    public void setaConstant(Double aConstant) {
        this.aConstant = aConstant;
    }

    public Double getbConstant() {
        return bConstant;
    }

    public void setbConstant(Double bConstant) {
        this.bConstant = bConstant;
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
