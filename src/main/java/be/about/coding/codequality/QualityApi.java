package be.about.coding.codequality;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.about.coding.codequality.persistence.QualityRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("codebases/")
@AllArgsConstructor
public class QualityApi {

    private QualityRepository qualityRepository;

    @PostMapping()
    public ResponseEntity startQualityCheckFor(String codebasePath, String codebaseName) {
        CodeBaseValidator validator = new CodeBaseValidator();
        validator.validateCodeBase(codebasePath);

        qualityRepository.addCodebase(codebasePath);

        return new ResponseEntity(HttpStatus.OK);
    }
}
