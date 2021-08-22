package org.syssort.exceptions;


/*
 *
 * */
public class InvalidTagException extends Exception {
    public InvalidTagException(String tag) {
        super('<'+tag + "> is not defined.");
    }
}
