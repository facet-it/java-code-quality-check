package be.about.coding.codequality.dependency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="incomingdependency")
@Data
@NoArgsConstructor
public class IncomingDependency {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="incoming")
    private String incoming;

    @Column(name="current")
    private String current;

    @ManyToOne
    private DependencyAnalysis analysis;

    public IncomingDependency(String current, String incoming, DependencyAnalysis analysis) {
        this.current = current;
        this.incoming = incoming;
        this.analysis = analysis;
    }
}
