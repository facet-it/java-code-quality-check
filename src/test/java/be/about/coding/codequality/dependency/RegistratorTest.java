package be.about.coding.codequality.dependency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class RegistratorTest {

    private Path testPath;
    private Registrator registrator;
    private String testName = "test";

    @BeforeEach
    public void setup(@Mock Snapshot snapshot) {
        testPath = Paths.get("src", "test", "resources", "test");
        registrator = new Registrator(snapshot);
    }

    @Test
    public void registratorShouldRegisterAllPackages() {
        Map<String, List<String>> registry = registrator.register(testName, testPath.toFile());
        Assertions.assertEquals(2, registry.keySet().size());
    }

    @Test
    public void registratorShouldRegisterAllClasses() {
        Map<String, List<String>> registry = registrator.register(testName, testPath.toFile());

        Assertions.assertTrue(registry.values().stream()
        .map(List::size)
        .filter(size -> size == 0)
        .findFirst().isEmpty());
    }

}