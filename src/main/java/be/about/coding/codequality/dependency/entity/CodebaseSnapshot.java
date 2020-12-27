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

@Entity
@Table(name="codebase_snapshot")
@Data
public class CodebaseSnapshot {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "timestamp")
    private long timestamp = System.currentTimeMillis();

    @OneToMany(mappedBy = "snapshot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DefinedPackage> packages = new LinkedList<>();

    @Column(name="codebase")
    private String codebase;

    public CodebaseSnapshot(String codebase) {
        this.codebase = codebase;
    }

}
