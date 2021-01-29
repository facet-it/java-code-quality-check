package be.about.coding.codequality.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import be.about.coding.codequality.metric.analyse.AnalysisListener;
import be.about.coding.codequality.metric.entity.ClassMetrics;

@Component
public class ClassAnalyzingProcess {

    private List<AnalysisListener> listeners = new LinkedList<>();
    private int lineCount = 0;

    @Autowired
    public ClassAnalyzingProcess(List<AnalysisListener> listeners) {
        this.listeners.addAll(listeners);
    }

    public ClassMetrics analyze(String fileName) {
        File classFile = validateClass(fileName);

        try {
            BufferedReader classReader = new BufferedReader(new FileReader(classFile));
            String line = classReader.readLine();
            while (line != null) {
                line += "\n"; //this is for working with annotations. The buffered reader doesn't pass line separators
                //This action is not really on its place here, but ok...
                lineCount++;
                analyseLine(line);
                line = classReader.readLine();
            }
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }

        ClassMetrics metrics = groupMetrics();
        return metrics;
    }

    private File validateClass(String fileName) {
        File classFile = new File(fileName);
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("Please pass a file name");
        }
        if (!classFile.exists() || !classFile.isFile()) {
            throw new IllegalArgumentException("The given file is not an existing file");
        }
        if (!fileName.endsWith(".java")) {
            throw new IllegalArgumentException("Please select a .java file");
        }

        return classFile;
    }

    private void analyseLine(String line) {
        String trimmedLine = line.trim();
        char[] characters = trimmedLine.toCharArray();
        for (char character : characters) {
            notifyAnalysers(character);
        }
    }

    private void notifyAnalysers(Character next) {
        listeners.forEach(listener -> listener.receive(next));
    }

    private ClassMetrics groupMetrics() {
        ClassMetrics metrics = new ClassMetrics();
        metrics.addMetric("Line count", lineCount);

        listeners.forEach(listener -> metrics.addMetric(listener.getMetricName(), listener.getMetrics()));
        return metrics;
    }

}
