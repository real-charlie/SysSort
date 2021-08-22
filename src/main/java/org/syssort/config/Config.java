package org.syssort.config;

/*
 * Used for holding the file sorting configurations.
 * */


public class Config {

    private DirectoryConfig[] directoryConfigs;
    private String[] ignores; // List of ignored files/directories
    private String workingDir;
    private boolean deleteEmptyDirs;
    private boolean hiddenFiles;

    public void setDirectoryConfigs(DirectoryConfig[] directoryConfigs) {
        this.directoryConfigs = directoryConfigs;
    }

    public void setIgnores(String[] ignores) {
        this.ignores = ignores;
    }

    public void setDeleteEmptyDirs(boolean deleteEmptyDirs) {
        this.deleteEmptyDirs = deleteEmptyDirs;
    }

    public void setHiddenFiles(boolean hiddenFiles) {
        this.hiddenFiles = hiddenFiles;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }
    public DirectoryConfig[] getDirectoryConfigs() {
        return directoryConfigs;
    }

    public String[] getIgnores() {
        return ignores;
    }

    public String getWorkingDir() {
        return workingDir;
    }
    public boolean shouldDeleteEmptyDirectories() {
        return deleteEmptyDirs;
    }

    public boolean includeHiddenFiles() {
        return hiddenFiles;
    }
}