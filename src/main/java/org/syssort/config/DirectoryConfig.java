package org.syssort.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


/*
 * Used for holding each configured (in xml) directory name and included formats.
 * */


public class DirectoryConfig {
    private final String name;
    private final String[] includedFormats;

    public DirectoryConfig(String name, String[] includedFormats) {
        this.name = name;
        try {
            if (!new File(name).exists()) {
                Files.createDirectory(Path.of(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.includedFormats = includedFormats;
    }

    /*
     * To prevent replacing existing name.
     * @param absolutePath - absolute path of target file
     * */
    private static Path makePath(String absolutePath) {
        Path finalPath;
        int num = 1;
        while ((finalPath = Path.of(absolutePath + " (" + num++ + ')')).toFile().exists()) ;
        return finalPath;
    }

    public void moveFrom(String targetFileOrDirectory) throws IOException {
        Path targetFileOrDirectoryPath = Path.of(targetFileOrDirectory);
        Path willRenameTo = Path.of(name +
                Shared.DIRECTORY_SEPARATOR +
                targetFileOrDirectoryPath.getFileName());
        if (willRenameTo.toFile().exists()) {
            willRenameTo = makePath(willRenameTo.toString());
        }
        if (Arrays.stream(includedFormats).anyMatch(targetFileOrDirectory::contains) && !targetFileOrDirectoryPath.getParent().endsWith(name)) {
            System.out.println(targetFileOrDirectory);
            if (!new File(targetFileOrDirectory).renameTo(new File(willRenameTo.toString()))) {
                throw new IOException(targetFileOrDirectory);
            }
        }
    }
}
