package org.syssort.config;

// Shared variables.
public class Shared {
    public static final int MAX_ALLOWED_THREADS = 10; // Better to prevent increasing this
    public static final String DIRECTORY_FORMAT = "d", // Not necessary to modify
            FILE_SPECIFICATION_SEPARATOR = "\\|", // Whatever you prefer to separate <dir> body specifications
            DIRECTORY_SEPARATOR = "/"; // For UNIX-Based Systems use "/" and for Windows "\\"
}
