package be.about.coding.codequality.dependency.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "package")
@Data
@NoArgsConstructor
public class DefinedPackage {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "myPackage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DefinedClass> classes = new LinkedList<>();

    @ManyToOne(optional = false)
    @JsonIgnoreProperties({"packages"})
    private CodebaseSnapshot snapshot;
    
    public DefinedPackage(String name) {
        this.name = name;
    }
}
