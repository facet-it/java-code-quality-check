package be.about.coding.codequality.persistence.dependency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.about.coding.codequality.dependency.entity.DefinedPackage;

@Repository
public interface DefinedPackageRepository extends JpaRepository<DefinedPackage, Long> {

}
