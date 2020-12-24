package be.about.coding.codequality.dependency.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "class")
@Data
@NoArgsConstructor
public class DefinedClass {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="fullname")
    private String fullname;

    @ManyToOne
    @JsonIgnoreProperties({"classes"})
    private DefinedPackage myPackage;

    public DefinedClass(String fullname) {
        this.fullname = fullname;

        String[] nameParts = fullname.split("/");
        if(nameParts.length > 0) {
            this.name = nameParts[nameParts.length -1];
        }
    }

}
