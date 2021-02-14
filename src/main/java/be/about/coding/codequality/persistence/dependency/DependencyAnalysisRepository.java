package be.about.coding.codequality.persistence.dependency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import be.about.coding.codequality.dependency.entity.DependencyAnalysis;
import be.about.coding.codequality.dependency.entity.DependencyAnalysisProjection;

@Repository
public interface DependencyAnalysisRepository extends JpaRepository<DependencyAnalysis, Long> {

    @Query(value = "SELECT * FROM dependency_analysis as da "
                   + "join dependency d on da.id = d.analysis_id  "
                   + "WHERE codebase = :codebaseName "
                   + "ORDER BY timestamp DESC limit 1",
        nativeQuery = true)
    DependencyAnalysis getLatestDependencyAnalysis(String codebaseName);

    @Query(value = "SELECT id, codebase, `timestamp` FROM dependency_analysis",
        nativeQuery = true)
    List<DependencyAnalysisProjection> getAllExecutedAnalysis();
}
