package be.about.coding.codequality.dependency.query;

import java.util.LinkedList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DependencySummary {

    private List<Node> nodes = new LinkedList<>();
    private List<Dependency> links = new LinkedList<>();
    private List<String> names = new LinkedList<>();

    /**
     * This constructor takes the target from which we want the dependency summary
     * @param originalTarget class for which we want the dependency summary
     */
    public DependencySummary(Node originalTarget) {
        this.addNode(originalTarget);
    }

    public void addNode(Node node) {
        this.names.add(node.getWithPackage());
        this.nodes.add(node);
    }

    public void addLink(String source, String target) {
        int sourceIndex = this.names.indexOf(source);
        int targetIndex = this.names.indexOf(target);
        Dependency dependency = new Dependency(source, target, sourceIndex, targetIndex);
        this.links.add(dependency);
    }

    public List<Node> getNodes() {
        return new LinkedList<>(nodes);
    }

    public List<Dependency> getLinks() {
        return new LinkedList<>(links);
    }

}
