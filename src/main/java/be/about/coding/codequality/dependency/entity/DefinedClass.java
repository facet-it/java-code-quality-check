package be.about.coding.codequality.dependency.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "class")
@Data
public class DefinedClass {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="fullname")
    private String fullname;

    @ManyToOne
    private DefinedPackage myPackage;

}
