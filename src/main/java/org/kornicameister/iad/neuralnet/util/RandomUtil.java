package org.kornicameister.iad.neuralnet.util;

import com.google.common.base.Preconditions;

import java.util.Random;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RandomUtil {
    public static final Random SEED = new Random(System.nanoTime());

    public static Double randomDouble(final double lower, final double higher) {
        Preconditions.checkArgument(lower < higher, "Lower bound must be greater than higher bound");
        SEED.setSeed(System.nanoTime());
        return lower + (SEED.nextDouble() * (higher - lower));
    }

    public static Double randomDouble() {
        SEED.setSeed(System.nanoTime());
        return SEED.nextDouble();
    }
}
