package be.about.coding.codequality.dependency;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import be.about.coding.codequality.dependency.entity.CodebaseSnapshot;
import be.about.coding.codequality.dependency.entity.DefinedClass;
import be.about.coding.codequality.dependency.entity.DefinedPackage;
import be.about.coding.codequality.persistence.dependency.CodebaseSnapshotRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Snapshot {

    private CodebaseSnapshotRepository snapshotRepository;

    @Transactional
    public void make(String codebase, Map<String, List<String>> codebaseRegistery) {
        CodebaseSnapshot snapshot = new CodebaseSnapshot(codebase);

        for(String packagename : codebaseRegistery.keySet()) {
            DefinedPackage definedPackage = new DefinedPackage(packagename);
            List<String> classes = codebaseRegistery.get(packagename);

            List<DefinedClass> definedClasses = classes.stream().map(DefinedClass::new)
                .peek(definedClass -> definedClass.setMyPackage(definedPackage))
                .collect(Collectors.toList());

            definedPackage.getClasses().addAll(definedClasses);
            definedPackage.setSnapshot(snapshot);
            snapshot.getPackages().add(definedPackage);
        }

        snapshotRepository.saveAndFlush(snapshot);
    }

}
