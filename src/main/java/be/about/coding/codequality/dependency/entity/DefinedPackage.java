package be.about.coding.codequality.dependency.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "package")
@Data
public class DefinedPackage {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @Column(name="name")
    private String name;

    @Column(name="codebasename")
    private String codebaseName;

    @OneToMany(mappedBy = "myPackage", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<DefinedClass> classes;

    @ManyToOne
    private DependencyAnalysis analysis;
    

}
