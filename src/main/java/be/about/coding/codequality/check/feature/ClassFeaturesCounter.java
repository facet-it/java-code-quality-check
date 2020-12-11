package be.about.coding.codequality.check.feature;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
@Scope("prototype")
public class ClassFeaturesCounter {

    private int amountOfLines = 0;
    private int amountOfImports = 0;

    public void countFeatures(File targetClass) throws IOException {
        BufferedReader classReader = new BufferedReader(new FileReader(targetClass));
        String line = classReader.readLine();

        while (line != null) {
            String trimmedLine = line.trim();
            amountOfLines++;

            if (isImport(trimmedLine)) {
                amountOfImports++;
            }

            line = classReader.readLine();
        }
    }

    private boolean isImport(String line) {
        return line.contains("import");
    }
}

