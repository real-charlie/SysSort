package org.syssort.exceptions;

public class EmptyWorkingDirectory extends Exception{
    public EmptyWorkingDirectory(String directory) {
        super(directory + " is empty, so nothing to travers.");
    }
}
