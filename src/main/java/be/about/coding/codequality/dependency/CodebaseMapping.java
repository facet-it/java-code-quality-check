package be.about.coding.codequality.dependency;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Scope("prototype")
class CodebaseMapping {

    private final List<String> ignoredDirectories = List.of("/src/test",
                                                            "/target");
    private final Map<String, List<String>> registry = new HashMap<>();
    private final Snapshot snapshot;

    public CodebaseMapping(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    public Map<String, List<String>> start(String codebase, File currentPackage) {
        walkthrough(currentPackage);
        snapshot.make(codebase, registry);
        return registry;
    }

    //recursive method, i think this is the best way of registering
    private void walkthrough(File currentPackage) {
        if (currentPackage.list() != null && isNotIgnored(currentPackage)) {
            File[] files = currentPackage.listFiles();

            registerActualFiles(currentPackage, files);

            List<File> directories = Stream.of(files)
                .filter(File::isDirectory)
                .collect(Collectors.toList());

            for (File directory : directories) {
                walkthrough(directory);
            }
        }
    }

    private boolean isNotIgnored(File currentPackage) {
        for (String ignored : ignoredDirectories) {
            if (currentPackage.toString().endsWith(ignored)) {
                return false;
            }
        }
        return true;
    }

    private void registerActualFiles(File currentPackage, File[] fileList) {
        //only interested in .java file atm
        String fileType = ".java";
        List<File> actualFiles = Stream.of(fileList)
            .filter(File::isFile)
            .filter(file -> file.getName().endsWith(fileType))
            .collect(Collectors.toList());

        if (!actualFiles.isEmpty()) {
            registry.put(currentPackage.getAbsolutePath(), new LinkedList<>());
        }

        for (File file : actualFiles) {
            registry.get(currentPackage.getAbsolutePath()).add(file.getAbsolutePath());
        }
    }
}
