package org.syssort.exceptions;

public class EmptyPathException extends Exception{
    public EmptyPathException(String elementTag){
        super("Required a path attribute for <" + elementTag + '>');
    }
}
