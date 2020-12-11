package be.about.coding.codequality;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import be.about.coding.codequality.check.CodebaseCheck;
import be.about.coding.codequality.check.feature.DependencyAnalysis;
import be.about.coding.codequality.persistence.QualityRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/codebases")
@AllArgsConstructor
public class QualityApi {

    private QualityRepository qualityRepository;
    private CodebaseCheck codebaseCheck;
    private DependencyAnalysis analyser;

    @PostMapping("/")
    public ResponseEntity<Map<String, Map<String, List<String>>>> startQualityCheckFor(String codebasePath, String codebaseName) {
        CodeBaseValidator validator = new CodeBaseValidator();
        validator.validateCodeBase(codebasePath);

        qualityRepository.addCodebase(codebaseName);
        codebaseCheck.startCodebaseCheck(codebasePath, codebaseName);

        return new ResponseEntity(qualityRepository.getRegistry(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getImportsOf() {
        File file = new File("/Users/nicholasocket/Documents/workspace/prod/iKentoo/sf-integration-api/src/main/java/com/lightspeed/hospitality/sfintegrationapi/IntegrationApi.java");
        try {
            List<String> dependencies = analyser.extractDependencies(file);
            return new ResponseEntity<>(dependencies, HttpStatus.OK);
        }
        catch(IOException ioe) {
            List<String> noresult = new LinkedList<>();
            noresult.add("no result");
            return new ResponseEntity<>(noresult, HttpStatus.BAD_REQUEST);
        }
    }
}
