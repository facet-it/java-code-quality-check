package be.about.coding.codequality.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import be.about.coding.codequality.dependency.entity.DefinedClass;
import be.about.coding.codequality.dependency.entity.DefinedPackage;
import be.about.coding.codequality.dependency.entity.DependencyAnalysis;
import be.about.coding.codequality.persistence.dependency.DependencyAnalysisRepository;
import be.about.coding.codequality.persistence.memory.QualityRepository;

@Component
public class CodebaseSnapshot {

    private QualityRepository inMemorySnapshot;
    private DependencyAnalysisRepository analysisRepository;

    @Autowired
    public CodebaseSnapshot(QualityRepository inMemorySnapshot, DependencyAnalysisRepository analysisRepository) {
        this.inMemorySnapshot = inMemorySnapshot;
        this.analysisRepository = analysisRepository;
    }

    @Transactional
    public void make(String codebase) {
        Map<String, List<String>> codebaseRegistery = inMemorySnapshot.getRegistry().get(codebase);

        DependencyAnalysis analysis = new DependencyAnalysis(codebase);

        for(String packagename : codebaseRegistery.keySet()) {
            DefinedPackage definedPackage = new DefinedPackage(packagename);
            List<String> classes = codebaseRegistery.get(packagename);

            List<DefinedClass> definedClasses = classes.stream().map(DefinedClass::new)
                .peek(definedClass -> definedClass.setMyPackage(definedPackage))
                .collect(Collectors.toList());

            definedPackage.getClasses().addAll(definedClasses);
            definedPackage.setAnalysis(analysis);
            analysis.getPackages().add(definedPackage);
        }

        analysisRepository.saveAndFlush(analysis);
    }

}
