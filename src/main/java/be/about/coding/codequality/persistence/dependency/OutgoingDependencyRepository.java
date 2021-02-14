package be.about.coding.codequality.persistence.dependency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import be.about.coding.codequality.dependency.entity.OutgoingDependency;
import be.about.coding.codequality.dependency.entity.OutgoingDependencyProjection;

@Repository
public interface OutgoingDependencyRepository extends JpaRepository<OutgoingDependency, Long> {

    @Query(value = "select current, outgoing from outgoingdependency "
                   + "where analysis_id = :analysisId "
                   + "and current = :current",
    nativeQuery = true)
    List<OutgoingDependencyProjection> getAllOutgoingFor(long analysisId, String current);

}
