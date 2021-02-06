package be.about.coding.codequality;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import be.about.coding.codequality.dependency.Analysis;
import be.about.coding.codequality.dependency.entity.DependencyAnalysis;
import be.about.coding.codequality.metric.ClassAnalyzingProcess;
import be.about.coding.codequality.metric.entity.ClassMetrics;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/codebases")
@AllArgsConstructor
public class QualityApi {

    private Analysis codebaseCheck;
    private ClassAnalyzingProcess classAnalyzingProcess;

    @PostMapping("/")
    public ResponseEntity<Map<String, Map<String, List<String>>>> startQualityCheckFor(String codebasePath, String codebaseName) {
        CodeBaseValidator validator = new CodeBaseValidator();
        validator.validateCodeBase(codebasePath);

        DependencyAnalysis analysis = codebaseCheck.startCodebaseCheck(codebasePath, codebaseName);

        return new ResponseEntity(analysis, HttpStatus.OK);
    }

    @PostMapping("/class/metrics")
    public ResponseEntity<ClassMetrics> analyseClass(String className) {
        ClassMetrics metrics = classAnalyzingProcess.analyze(className);
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}
