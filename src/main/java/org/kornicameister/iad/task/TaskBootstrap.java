package org.kornicameister.iad.task;

import org.apache.log4j.Logger;
import org.kornicameister.iad.task.impl.ApproximationTask;
import org.kornicameister.iad.task.impl.ClassificationTask;
import org.kornicameister.iad.task.impl.TransformationTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TaskBootstrap {
    private static final Logger LOGGER = Logger.getLogger(TaskBootstrap.class);

    public static void bootstrap(final String propertiesPath) throws IOException {
        final Tasks tasks = TaskBootstrap.getTasks(propertiesPath);

        LOGGER.info(String.format("Task to be launched is >>> %s", tasks));

        if (tasks != null) {
            switch (tasks) {
                case APPROXIMATION:
                    new ApproximationTask().compute(propertiesPath);
                    break;
                case CLASSIFICATION:
                    new ClassificationTask().compute(propertiesPath);
                    break;
                case TRANSFORMATION:
                    new TransformationTask().compute(propertiesPath);
                    break;
            }
        } else {
            throw new UnsupportedTaskException();
        }
    }

    private static Tasks getTasks(final String propertiesPath) throws IOException {
        final Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader(new File(propertiesPath))));
        return Tasks.valueOf(properties.getProperty("task.name").toUpperCase());
    }
}
