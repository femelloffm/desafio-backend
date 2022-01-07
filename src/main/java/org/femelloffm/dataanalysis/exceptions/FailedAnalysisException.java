package org.femelloffm.dataanalysis.exceptions;

public class FailedAnalysisException extends RuntimeException {
    public FailedAnalysisException(String message) {
        super(message);
    }

    public FailedAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
