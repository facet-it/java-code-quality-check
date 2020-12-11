package be.about.coding.codequality.check;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.about.coding.codequality.persistence.QualityRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class Registrator {

    private QualityRepository repository;

    //recursive method, i think this is the best way of registering
    public void register(String codebase, File currentPackage) {
        if (currentPackage.list() != null) {
            File[] files = currentPackage.listFiles();

            registerActualFiles(codebase, currentPackage, files);

            List<File> directories = Stream.of(files)
                .filter(File::isDirectory)
                .collect(Collectors.toList());

            for (File directory : directories) {
                register(codebase, directory);
            }
        }
    }

    private void registerActualFiles(String codebase, File currentPackage, File[] fileList) {
        //only interested in .java file atm
        String fileType = ".java";
        List<File> actualFiles = Stream.of(fileList)
            .filter(File::isFile)
            .filter(file -> file.getName().endsWith(fileType))
            .collect(Collectors.toList());

        if (!actualFiles.isEmpty()) {
            repository.addPackageFor(codebase, currentPackage.getAbsolutePath());
        }

        for (File file : actualFiles) {
            repository.addClassFor(codebase, currentPackage.getAbsolutePath(), file.getAbsolutePath());
        }
    }

}
