package be.about.coding.codequality.dependency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="outgoingdependency")
@Data
public class OutgoingDependency {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="outgoing")
    private String outgoing;

    @Column(name="current")
    private String current;

    @Column(name="analysis_id")
    private long analysisId;

}
