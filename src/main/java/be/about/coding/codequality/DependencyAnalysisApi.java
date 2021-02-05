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
import be.about.coding.codequality.persistence.memory.QualityRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/analysis")
@AllArgsConstructor
public class DependencyAnalysisApi {

    private final QualityRepository qualityRepository;
    private final Analysis codebaseCheck;

    @PostMapping("/")
    public ResponseEntity<Map<String, Map<String, List<String>>>> startQualityCheckFor(String codebasePath, String codebaseName) {
        CodeBaseValidator validator = new CodeBaseValidator();
        validator.validateCodeBase(codebasePath);

        qualityRepository.addCodebase(codebaseName);
        DependencyAnalysis analysis = codebaseCheck.startCodebaseCheck(codebasePath, codebaseName);

        return new ResponseEntity(analysis, HttpStatus.OK);
    }

}
