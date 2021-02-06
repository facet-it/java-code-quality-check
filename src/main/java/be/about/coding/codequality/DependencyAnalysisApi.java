package be.about.coding.codequality;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import be.about.coding.codequality.dependency.Analysis;
import be.about.coding.codequality.dependency.entity.Dependency;
import be.about.coding.codequality.dependency.entity.DependencyAnalysis;
import be.about.coding.codequality.persistence.dependency.DependencyAnalysisRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/dependency/analysis")
@AllArgsConstructor
public class DependencyAnalysisApi {

    private final Analysis analysis;
    private final DependencyAnalysisRepository dependencyAnalysisRepository;

    @PostMapping("/")
    public ResponseEntity<Dependency> startQualityCheckFor(String codebasePath, String codebaseName) {
        CodeBaseValidator validator = new CodeBaseValidator();
        validator.validateCodeBase(codebasePath);

        DependencyAnalysis analysis = this.analysis.startCodebaseCheck(codebasePath, codebaseName);

        return new ResponseEntity(analysis, HttpStatus.OK);
    }

    @GetMapping("/{codebaseName}")
    public ResponseEntity<DependencyAnalysis> getLatestDependencyAnalysisFor(@PathVariable String codebaseName) {
        DependencyAnalysis latestAnalysis = dependencyAnalysisRepository.getLatestDependencyAnalysis(codebaseName);
        if (latestAnalysis == null) {
            return new ResponseEntity<>(new DependencyAnalysis(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(latestAnalysis, HttpStatus.OK);
    }

}
