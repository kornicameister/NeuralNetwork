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
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class TaskBootstrap {
    private static final Logger LOGGER = Logger.getLogger(TaskBootstrap.class);
    private static Properties PROPERTIES;

    public static void bootstrap(final String propertiesPath) throws IOException {
        TaskBootstrap.PROPERTIES = new Properties();
        TaskBootstrap.PROPERTIES.load(new BufferedReader(new FileReader(new File(propertiesPath))));

        if (TaskBootstrap.isBatch()) {
            final Set<String> properties = TaskBootstrap.PROPERTIES.stringPropertyNames();
            for (final String property : properties) {
                if (!property.contains("batch")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TaskBootstrap.launchTask(TaskBootstrap.PROPERTIES.getProperty(property), TaskBootstrap.getTasks(property.toUpperCase()));
                        }
                    }).start();
                }
            }

        } else {
            final Tasks tasks = TaskBootstrap.getTasks();
            TaskBootstrap.launchTask(propertiesPath, tasks);
        }
    }

    private static Tasks getTasks(final String taskName) {
        return Tasks.valueOf(taskName);
    }

    private static Tasks getTasks() throws IOException {
        return TaskBootstrap.getTasks(TaskBootstrap.PROPERTIES.getProperty("task.name").toUpperCase());
    }

    private static boolean isBatch() throws IOException {
        return TaskBootstrap.PROPERTIES.getProperty("batch", "false").equals("true");
    }

    private static void launchTask(final String propertiesPath, final Tasks tasks) {
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
}
