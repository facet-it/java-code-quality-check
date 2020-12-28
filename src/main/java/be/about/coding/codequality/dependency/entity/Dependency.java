package be.about.coding.codequality.dependency.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="dependency")
@NoArgsConstructor
@Getter
@Setter
public class Dependency {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="source")
    private String source;

    @Column(name="target")
    private String target;

    @ManyToOne
    @JsonIgnoreProperties({"dependencies"})
    private DependencyAnalysis analysis;

    public Dependency(String source, String target) {
        this.source = source;
        this.target = target;
    }

}
