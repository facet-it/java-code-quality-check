package be.about.coding.codequality;

import java.io.File;
import java.nio.file.Path;

/**
 * Decided to really encapsulate this. First stage, this will not even be a spring bean (for the record, being as Spring bean
 * has nothing to do with encapsulation btw :) ).
 */
public class CodeBaseValidator {

    private static final String codeDirectory = "/src/main/java";

    public void validateCodeBase(String codebasePath) {
        isDirectory(codebasePath);
    }

    private void isDirectory(String codebasePath) {
        File path = Path.of(codebasePath).toFile();
        if (! path.isDirectory()) {
            throw new IllegalArgumentException("Given path " + codebasePath + "  is not a directory");
        }
    }
}
