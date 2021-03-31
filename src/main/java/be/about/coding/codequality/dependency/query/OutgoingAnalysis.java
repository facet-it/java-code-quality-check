package be.about.coding.codequality.dependency.query;

import org.springframework.stereotype.Component;

import java.util.List;

import be.about.coding.codequality.dependency.entity.OutgoingDependencyProjection;
import be.about.coding.codequality.persistence.dependency.OutgoingDependencyRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OutgoingAnalysis {

    private OutgoingDependencyRepository repository;

    public DependencySummary analyseOutGoingDependenciesFor(long analysisId, String classname) {
        List<OutgoingDependencyProjection> outgoingDependencies = repository.getAllOutgoingFor(analysisId, classname);
        return createOutgoingAnalysis(classname, outgoingDependencies);
    }

    private DependencySummary createOutgoingAnalysis(String classname, List<OutgoingDependencyProjection> dependencies) {
        DependencySummary dependencySummary = new DependencySummary(toNode(classname));
        dependencies.forEach(dependency -> dependencySummary.addNode(toNode(dependency.getOutgoing())));
        dependencies.forEach(dependency -> dependencySummary.addLink(dependency.getCurrent(), dependency.getOutgoing()));

        return dependencySummary;
    }

    private Node toNode(String classname) {
        String[] packages = classname.split("\\.");
        String name = packages[packages.length -1];

        return new Node(name, classname);
    }

}
