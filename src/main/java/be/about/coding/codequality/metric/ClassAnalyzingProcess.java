package be.about.coding.codequality.check;

import org.springframework.stereotype.Component;

import java.io.File;

import be.about.coding.codequality.check.entity.ClassMetrics;

@Component
class ClassAnalyzingProcess {



    public ClassMetrics analyze(String fileName) {
        validateClass(fileName);

        return null;
    }

    private void validateClass(String fileName) {
        File file = new File(fileName);
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("Please pass a file name");
        }
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("The given file is not an existing file");
        }
        if(!fileName.endsWith(".java")) {
            throw new IllegalArgumentException("Please select a .java file");
        }
    }

}
