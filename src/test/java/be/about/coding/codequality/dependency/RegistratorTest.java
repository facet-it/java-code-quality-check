package be.about.coding.codequality.dependency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import be.about.coding.codequality.persistence.memory.QualityRepository;

class RegistratorTest {

    private Path testPath;
    private Registrator registrator;
    private QualityRepository repository;
    private String testName = "test";

    @BeforeEach
    public void setup() {
        testPath = Paths.get("src", "test", "resources", "test");
        repository = new QualityRepository();
        repository.addCodebase(testName);
        registrator = new Registrator(repository);
    }

    @Test
    public void registratorShouldRegisterAllPackages() {
        registrator.register(testName, testPath.toFile());
        Map<String, List<String>> testRegistry = repository.getRegistry().get("test");

        Assertions.assertEquals(2, testRegistry.keySet().size());
    }

    @Test
    public void registratorShouldRegisterAllClasses() {
        registrator.register(testName, testPath.toFile());
        Map<String, List<String>> testRegistry = repository.getRegistry().get("test");

        Assertions.assertTrue(testRegistry.values().stream()
        .map(List::size)
        .filter(size -> size == 0)
        .findFirst().isEmpty());
    }

}