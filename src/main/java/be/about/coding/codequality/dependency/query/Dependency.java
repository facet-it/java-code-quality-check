package be.about.coding.codequality.dependency.query;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Dependency {

    String sourceName;
    String targetName;
    int source;
    int target;

}
