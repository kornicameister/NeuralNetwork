package org.kornicameister.iad.neuralnet.util;

import java.util.Arrays;
import java.util.Random;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class ArraysUtils {

    public static <T> T[][] shuffle(T[][] input) {
        int j;
        Random seed = new Random(System.nanoTime());
        T[][] cloned = input.clone();
        for (int i = cloned.length - 1; i > 0; i--) {
            j = seed.nextInt(i);
            for (int k = 0; k < cloned[i].length; k++) {
                T c = cloned[i][k];
                cloned[i][k] = cloned[j][k];
                cloned[j][k] = c;
            }
        }
        return cloned;
    }

    public static Double[] newRandomDoubleArray(final int size, final double lower, final double higher) {
        final Random seed = new Random(System.nanoTime());
        Double[] array = ArraysUtils.newDoubleArray(size);
        for (int i = 0; i < size; i++) {
            array[i] = RandomUtil.randomDouble(lower, higher);
        }
        return array;
    }

    public static Double[] newRandomDoubleArray(final int size) {
        final Random seed = new Random(System.nanoTime());
        Double[] array = ArraysUtils.newDoubleArray(size);
        for (int i = 0; i < size; i++) {
            array[i] = RandomUtil.randomDouble();
        }
        return array;
    }

    public static Double[] newDoubleArray(final int size) {
        Double[] array = new Double[size];
        Arrays.fill(array, 0d);
        return array;
    }

}
