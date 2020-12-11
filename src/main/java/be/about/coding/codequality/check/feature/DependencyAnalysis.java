package be.about.coding.codequality.check.feature;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Scope(value = "prototype")
public class DependencyAnalysis {

    private List<String> dependencies;
    private StringBuilder completeImportList = new StringBuilder();

    public List<String> extractDependencies(File targetClass) throws IOException {
        BufferedReader classReader = new BufferedReader(new FileReader(targetClass));
        String line = classReader.readLine();

        while (line != null) {
            String trimmedLine = line.trim();
            if (!trimmedLine.contains("class")) {
                completeImportList.append(trimmedLine);
                line = classReader.readLine();
            } else {
                //We don't need more lines after we have had the line where we class is defined
                break;
            }
        }

        parseDependencies();
        return dependencies;
    }

    private void parseDependencies() {
        String[] possibleImports = completeImportList.toString().split(";");
        dependencies = Stream.of(possibleImports)
            .filter(line -> !line.contains("package"))
            .filter(line -> !line.contains("@"))
            .map(line -> {
                return line.replace("import", "").trim();
            })
            .collect(Collectors.toList());
    }
}
