package be.about.coding.codequality.dependency.query;

import java.util.LinkedList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DependencySummary {

    private List<String> nodes = new LinkedList<>();
    private List<Dependency> links = new LinkedList<>();

    /**
     * This constructor takes the target from which we want the dependency summary
     * @param originalTarget class for which we want the dependency summary
     */
    public DependencySummary(String originalTarget) {
        this.nodes.add(originalTarget);
    }

    public void addNode(String classname) {
        this.nodes.add(classname);
    }

    public void addLink(String source, String target) {
        Dependency dependency = new Dependency(source, target);
        this.links.add(dependency);
    }

    public List<String> getNodes() {
        return new LinkedList<>(nodes);
    }

    public List<Dependency> getLinks() {
        return new LinkedList<>(links);
    }

}
