package org.femelloffm.dataanalysis.exceptions;

public class SalesNotFoundException extends RuntimeException {
    public SalesNotFoundException() {
        super("No sale information was found in the flat files");
    }
}
