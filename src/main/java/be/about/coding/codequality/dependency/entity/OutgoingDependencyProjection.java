package be.about.coding.codequality.dependency.entity;

import javax.persistence.Column;

public interface OutgoingDependencyProjection {

    String getOutgoing();

    String getCurrent();
}
