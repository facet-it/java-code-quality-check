package be.about.coding.codequality;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import be.about.coding.codequality.check.CodebaseCheck;
import be.about.coding.codequality.persistence.QualityRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/codebases")
@AllArgsConstructor
public class QualityApi {

    private QualityRepository qualityRepository;
    private CodebaseCheck codebaseCheck;

    @PostMapping()
    public ResponseEntity<Map<String, Map<String, List<String>>>> startQualityCheckFor(String codebasePath, String codebaseName) {
        CodeBaseValidator validator = new CodeBaseValidator();
        validator.validateCodeBase(codebasePath);

        qualityRepository.addCodebase(codebaseName);
        codebaseCheck.startCodebaseCheck(codebasePath, codebaseName);

        return new ResponseEntity(qualityRepository.getRegistry(), HttpStatus.OK);
    }
}
