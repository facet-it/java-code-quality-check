package be.about.coding.codequality.persistence.dependency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.about.coding.codequality.dependency.entity.OutgoingDependency;

@Repository
public interface OutgoingDependencyRepository extends JpaRepository<OutgoingDependency, Long> {

}
