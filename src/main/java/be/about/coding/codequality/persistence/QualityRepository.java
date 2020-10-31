package be.about.coding.codequality.persistence;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import be.about.coding.codequality.ControlStatus;

@Component
public class QualityRepository {

    private Map<String, String> checkStatuses = new HashMap<>();
    private Map<String, Map<String, List<String>>> registry = new HashMap<>();

    public void addCodebase(String codebase) {
        this.checkStatuses.put(codebase, ControlStatus.ANALYSING.getName());
    }

    public void updateStatus(String codebase, ControlStatus newStatus) {
        validateCodebase(codebase);

        checkStatuses.put(codebase, newStatus.getName());
    }

    public void addPackageFor(String codebase, String packageName) {
        validateCodebase(codebase);

        List<String> classList = new LinkedList<>();
        Map<String, List<String>> packageRegistryForCodebase = new HashMap<>();
        packageRegistryForCodebase.put(packageName, classList);
        registry.put(codebase, packageRegistryForCodebase);
    }

    public void addClassFor(String codebase, String packageName, String className) {
        validateCodebase(codebase);

        Map<String, List<String>> packageRegistery = registry.get(codebase);
        packageRegistery.get(packageName).add(className);
    }

    private void validateCodebase(String codebase) {
        if (!checkStatuses.containsKey(codebase)) {
            throw new IllegalArgumentException("Codebase " + codebase + " is unknown to the system. Cannot update status");
        }
    }
}
