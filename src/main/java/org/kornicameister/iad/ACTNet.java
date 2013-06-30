package org.kornicameister.iad;

import org.kornicameister.iad.task.TaskBootstrap;

import java.io.IOException;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ACTNet {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new RuntimeException("Missing input arguments, need to pass -Dfile={properties_path}");
        }
        final String arg = args[0];
        final String[] split = arg.split("=");
        final String flag = split[0],
                path = split[1];
        if (!path.isEmpty() && flag.equals("-Dfile")) {
            TaskBootstrap.bootstrap(path);
        }
    }
}
