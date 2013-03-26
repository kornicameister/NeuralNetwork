package org.kornicameister.iad.neuralnet.util;

import java.util.Random;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class ArraysUtils {
    public static <T> T[] shuffle(T[] input) {
        int j;
        Random seed = new Random(System.nanoTime());
        T[] cloned = input.clone();
        for (int i = cloned.length - 1; i > 0; i--) {
            j = seed.nextInt(i);
            T c = cloned[i];
            cloned[i] = cloned[j];
            cloned[j] = c;
        }
        return cloned;
    }

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
}
