package be.about.coding.codequality.dependency.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface DependencyAnalysisProjection {

    long getId();

    long getTimestamp();

    String getCodebase();

}
