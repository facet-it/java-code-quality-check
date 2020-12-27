package be.about.coding.codequality.dependency.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dependency_analysis")
@Data
@NoArgsConstructor
public class DependencyAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "timestamp")
    private long timestamp = System.currentTimeMillis();

    @Column(name="codebase")
    private String codebase;

    @OneToMany(mappedBy = "analysis", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependency> dependencies = new LinkedList<>();

    public DependencyAnalysis(String codebase) {
        this.codebase = codebase;
    }

    public void addDependency(Dependency dependency) {
        dependency.setAnalysis(this);
        this.dependencies.add(dependency);
    }
}
