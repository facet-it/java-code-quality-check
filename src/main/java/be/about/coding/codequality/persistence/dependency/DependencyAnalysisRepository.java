package be.about.coding.codequality.persistence.dependency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import be.about.coding.codequality.dependency.entity.DependencyAnalysis;

@Repository
public interface DependencyAnalysisRepository extends JpaRepository<DependencyAnalysis, Long> {

    @Query(value = "SELECT * FROM dependency_analysis WHERE codebase = :codebaseName "
                   + "ORDER BY timestamp DESC limit 1",
    nativeQuery = true)
    DependencyAnalysis getLatestDependencyAnalysis(String codebaseName);
}
