package org.syssort;

import org.syssort.config.Config;
import org.syssort.config.DirectoryConfig;
import org.syssort.config.Shared;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MoveFile {

    /*
     * Traversing through the working directory and all it's subdirectories,
     *   then moving them to specified directories in xml configuration file.
     * @param config - extracted configurations
     * @param currentDir - current/working directory to traverse.
     * */
    public static void traverseWorkingDir(Config config, String currentDir) throws IOException {
        File dirAsFile = new File(currentDir);
        File[] children = dirAsFile.listFiles();
        if ((!config.includeHiddenFiles() && dirAsFile.isHidden())
                || Arrays.asList(config.getIgnores()).contains(currentDir))
            return;
        if (children != null) {
            for (File subFile : children) {
                if (subFile.isDirectory()) {
                    if (Thread.getAllStackTraces().keySet().size() < Shared.MAX_ALLOWED_THREADS) {
                        new Thread(() -> {
                            try {
                                traverseWorkingDir(config, subFile.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } else {
                        traverseWorkingDir(config, subFile.getAbsolutePath());
                    }
                } else {
                    for (DirectoryConfig dirConf : config.getDirectoryConfigs()) {
                        dirConf.moveFrom(subFile.getAbsolutePath());
                    }
                }
            }
        }
        if (config.shouldDeleteEmptyDirectories()) {
            dirAsFile.delete();
        }
    }
}
