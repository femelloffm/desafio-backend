package org.femelloffm.dataanalysis.exceptions;

public class ObjectParsingException extends RuntimeException {
    public ObjectParsingException(String data, Class objectClass, Throwable cause) {
        super("Could not parse " + data + " to " + objectClass.getName() + " object", cause);
    }
}
