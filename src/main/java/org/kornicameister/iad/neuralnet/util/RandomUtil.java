package org.kornicameister.iad.neuralnet.util;

import java.util.Random;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class RandomUtil {
    public static final Random SEED = new Random(System.nanoTime());

    public static Double randomDouble(final double lower, final double higher) {
        SEED.setSeed(System.nanoTime());
        return (SEED.nextDouble() * higher) - lower;
    }

    public static Double randomDouble() {
        SEED.setSeed(System.nanoTime());
        return SEED.nextDouble();
    }
}
