package be.about.coding.codequality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CodeBaseValidatorTest {

    private CodeBaseValidator codeBaseValidator;

    @BeforeEach
    public void setup() {
        codeBaseValidator = new CodeBaseValidator();
    }

    @Test
    public void failValidationIfPathIsNotDirectory() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.codeBaseValidator.validateCodeBase("/blabla");
        });

        Assertions.assertEquals("Given path /blabla is not a directory", exception.getMessage());
    }

    //Testing border values

    @Test
    public void failValidationIfPathIsNull() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.codeBaseValidator.validateCodeBase(null);
        });

        Assertions.assertEquals("Codebase path cannot be null", exception.getMessage());
    }

    @Test
    public void failValidationIfPathIsEmpty() {
        IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.codeBaseValidator.validateCodeBase("");
        });

        Assertions.assertEquals("Given path  is not a directory", exception.getMessage());
    }
}