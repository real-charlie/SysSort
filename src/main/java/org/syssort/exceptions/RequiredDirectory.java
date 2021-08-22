package org.syssort.exceptions;

public class RequiredDirectory extends Exception {
    public RequiredDirectory(String attr, String input) {
        super("Required a directory as path in attribute `" + attr + "` but " + input + " is a File.");
    }
}