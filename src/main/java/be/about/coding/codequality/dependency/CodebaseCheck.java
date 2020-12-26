package be.about.coding.codequality.dependency;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

/**
 * This class is a flowcontroller. It is a bit of a abstraction as it have very little details. Its only job is to control
 * the flow of the a codebase check. The actual details are considered to be 'concerns' and thus they will be separated
 * from this class.
 *
 * The CodebaseCheck class doesn't need to know how to actually do a check, nor does it need to know how to do a registry. It's
 * job is to know that there is a registry that needs to happen and that there is a check that needs to happen.
 *
 * So knowing the flow of a codebase check is its sole 'responsibility'. However, you need to realize that this responsibility
 * is just one part of the bigger responsibility: providing the functionality in which the stakeholder is interested
 * in: doing a check of the codebase.
 */
@Component
@AllArgsConstructor
public class CodebaseCheck {

    private static String CODE_BASE_DIRECTORY = "src/main/java";

    private Registrator registrator;
    private Snapshot snapshot;

    public void startCodebaseCheck(String codebasePath, String codebaseName) {

        File startDirectory = Path.of(codebasePath, CODE_BASE_DIRECTORY).toFile();
        Map<String, List<String>> registry = registrator.register(codebaseName, startDirectory);
    }

}
