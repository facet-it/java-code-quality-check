package be.about.coding.codequality.dependency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.about.coding.codequality.dependency.entity.Dependency;

/**
 * This is not a Spring class. I don't deem it necessary and it won't impact testing as much IMO
 */
class DependencyExtraction {

    private static final String ROOT_PACKAGE = "src/main/java/";

    private String currentClass;
    private File target;
    private List<Dependency> dependencies = new LinkedList<>();
    private StringBuilder completeImportList = new StringBuilder();

    public DependencyExtraction(String target) {
        this.target = new File(target);
        parseQualifiedClassName(target);
    }

    private void parseQualifiedClassName(String target) {
        if (target != null && target.contains(ROOT_PACKAGE)) {
            String[] parts = target.split(ROOT_PACKAGE);
            String targetDirectory = parts[1];

            currentClass = targetDirectory.replace("/", ".");
        } else {
            currentClass = target;
        }
    }

    public List<Dependency> extract() {
        try {
            BufferedReader classReader = new BufferedReader(new FileReader(target));
            String line = classReader.readLine();
            while (line != null) {
                String trimmedLine = line.trim();
                if (!containsTypeKeywords(trimmedLine)) {
                    completeImportList.append(trimmedLine);
                    line = classReader.readLine();
                } else {
                    //We don't need more lines after we have had the line where we class is defined
                    break;
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }

        parseDependencies();
        return dependencies;
    }

    private boolean containsTypeKeywords(String trimmedLine) {
        return trimmedLine.contains("class") || trimmedLine.contains("interface") || trimmedLine.contains("enum");
    }

    private void parseDependencies() {
        String[] possibleImports = completeImportList.toString().split(";");
        dependencies = Stream.of(possibleImports)
            .filter(line -> !line.contains("package"))
            .filter(line -> !line.contains("@"))
            .map(line -> {
                return line.replace("import", "").trim();
            })
            .map(parsedImport -> new Dependency(this.currentClass, parsedImport))
            .collect(Collectors.toList());
    }
}
