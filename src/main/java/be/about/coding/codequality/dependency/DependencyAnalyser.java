package be.about.coding.codequality.dependency;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import be.about.coding.codequality.dependency.entity.Dependency;
import be.about.coding.codequality.dependency.entity.DependencyAnalysis;
import be.about.coding.codequality.dependency.entity.IncomingDependency;
import be.about.coding.codequality.dependency.entity.OutgoingDependency;
import be.about.coding.codequality.persistence.dependency.DependencyAnalysisRepository;
import be.about.coding.codequality.persistence.dependency.IncomingDependencyRepository;
import be.about.coding.codequality.persistence.dependency.OutgoingDependencyRepository;

@Component
@Scope("prototype")
class DependencyAnalyser {

    private Set<IncomingDependency> incomingDependencies = new HashSet<>();
    private Set<OutgoingDependency> outgoingDependencies = new HashSet<>();

    private OutgoingDependencyRepository outgoingDependencyRepository;
    private IncomingDependencyRepository incomingDependencyRepository;
    private DependencyAnalysisRepository dependencyAnalysisRepository;

    public DependencyAnalyser(OutgoingDependencyRepository outgoingDependencyRepository,
                              IncomingDependencyRepository incomingDependencyRepository,
                              DependencyAnalysisRepository dependencyAnalysisRepository) {

        this.outgoingDependencyRepository = outgoingDependencyRepository;
        this.incomingDependencyRepository = incomingDependencyRepository;
        this.dependencyAnalysisRepository = dependencyAnalysisRepository;

    }

    @Transactional
    public DependencyAnalysis start(String codebase, Map<String, List<String>> registry) {
        DependencyAnalysis analysis = new DependencyAnalysis(codebase);
        for (String currentPackage : registry.keySet()) {
            List<String> classes = registry.get(currentPackage);

            for (String currentClass : classes) {
                DependencyExtraction extraction = new DependencyExtraction(currentClass);
                List<Dependency> dependencies = extraction.extract();

                dependencies.forEach(dependency -> {
                    incomingDependencies.add(new IncomingDependency(dependency.getTarget(), dependency.getSource(), analysis));
                    outgoingDependencies.add(new OutgoingDependency(dependency.getSource(), dependency.getTarget(), analysis));
                    analysis.addDependency(dependency);
                });
            }
        }

        dependencyAnalysisRepository.saveAndFlush(analysis);
        incomingDependencies.forEach(incomingDependencyRepository::saveAndFlush);
        outgoingDependencies.forEach(outgoingDependencyRepository::saveAndFlush);
        return analysis;
    }

}
