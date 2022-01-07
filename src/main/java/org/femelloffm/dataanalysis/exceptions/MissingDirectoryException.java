package org.femelloffm.dataanalysis.exceptions;

import java.nio.file.Path;

public class MissingDirectoryException extends RuntimeException {
    public MissingDirectoryException(Path directoryPath) {
        super("Directory " + directoryPath + " does not exist");
    }
}
