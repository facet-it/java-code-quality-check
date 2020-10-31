package be.about.coding.codequality.check;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.about.coding.codequality.persistence.QualityRepository;
import lombok.AllArgsConstructor;

@Component
@Scope(scopeName = "prototype")
@AllArgsConstructor
class Registrator {

    private QualityRepository repository;

    //recursive method, i think this is the best way of registering
    public void register(String codebase, File currentPackage) {
        if (currentPackage.list() != null) {
            String[] files = currentPackage.list();

            Stream.of(files).map(File::new)
                .filter(File::isFile)
                .forEach(file -> {
                    repository.addClassFor(codebase, currentPackage.getAbsolutePath(), file.getAbsolutePath());
                });

            List<File> directories = Stream.of(files).map(File::new)
                .filter(File::isDirectory)
                .collect(Collectors.toList());

            for(File directory :directories) {
                register(codebase, directory);
            }
        }
    }

}
