package be.about.coding.codequality.dependency.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class")
@Data
@NoArgsConstructor
public class DefinedClass {

    @Transient
    private static final String ROOT_PACKAGE = "src/main/java/";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="fullname")
    private String fullName;

    @ManyToOne
    @JsonIgnoreProperties({"classes"})
    private DefinedPackage myPackage;

    public DefinedClass(String fullname) {
        this.fullName = parseFullName(fullname);
        this.name = parseClassName(fullname);
    }

    private String parseFullName(String fullName) {
        String parsedFullName = fullName;
        if(fullName != null && this.fullName.contains(ROOT_PACKAGE)) {
            String[] parts = fullName.split(ROOT_PACKAGE);
            parsedFullName = parts[1];
        }
        return parsedFullName;
    }

    private String parseClassName(String fullName) {
        String[] nameParts = fullName.split("/");
        if(nameParts.length > 0) {
            return nameParts[nameParts.length -1];
        }
        return fullName;
    }

}
