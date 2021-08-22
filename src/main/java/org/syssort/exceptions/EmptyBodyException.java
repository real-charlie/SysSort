package org.syssort.exceptions;

public class EmptyBodyException extends Exception {
    public EmptyBodyException(String elementTag) {
        super('<'+elementTag+"> requires a body.");
    }
}
