package org.kornicameister.iad.task.impl;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.kornicameister.iad.neuralnet.impl.NeuralNetwork;


/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ApproximationTaskTest {
    private static final Logger LOGGER = Logger.getLogger(ApproximationTask.class);

    @Test
    public void testBuildNetwork() throws Exception {

        final NeuralNetwork neuralNetwork = new ApproximationTask(2).buildNetwork();
        Assert.assertNotNull(neuralNetwork);
    }
}
