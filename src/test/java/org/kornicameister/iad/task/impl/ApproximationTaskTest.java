package org.kornicameister.iad.task.impl;

import org.apache.log4j.Logger;
import org.junit.Test;


/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ApproximationTaskTest {
    private static final Logger LOGGER = Logger.getLogger(ApproximationTask.class);

    @Test
    public void testCompute() throws Exception {
        new ApproximationTask().compute("D:/Dropbox/STUDIA/INFORMATYKA/SEMESTR6/IAD/laboratoria/NeuralNetwork/src/main/resources/task_a.properties");
    }
}
