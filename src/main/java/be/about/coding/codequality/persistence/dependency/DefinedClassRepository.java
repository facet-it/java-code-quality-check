package be.about.coding.codequality.persistence.dependency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.about.coding.codequality.dependency.entity.DefinedClass;

@Repository
public interface DefinedClassRepository extends JpaRepository<DefinedClass, Long> {

}
