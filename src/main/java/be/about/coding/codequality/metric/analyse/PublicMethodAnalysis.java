package be.about.coding.codequality.metric.analyse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Going to try and implement some kind of state machine.
 */
@Component
@Scope("prototype")
public class PublicMethodAnalysis implements AnalysisListener {

    //the space is important, because you might have false positives on public
    private final String PUBLIC_KEYWORD = "public ";
    private final Character OPEN_PARENTHESIS = '(';
    private final Character CLOSE_PARENTHESIS = ')';

    private final LinkedList<Character> paranthesisStack = new LinkedList<>();

    private final List<Character> resetCharacters = List.of('=', ';', '{', '}');

    private StringBuilder sequencer = new StringBuilder();
    private int count = 0;

    private AnalysisStatus currentStatus = AnalysisStatus.BUILDING_KEYWORD;

    private final List<String> result = new LinkedList<>();

    @Override
    public void receive(Character current) {
        switch (currentStatus) {
            case BUILDING_KEYWORD:
                buildKeyword(current);
                break;
            case KEYWORD_REACHED:
                checkForParameterStart(current);
                break;
            case PARAMETERS_REACHED:
                checkForParameterEnd(current);
                break;
        }
    }

    private void buildKeyword(Character current) {
        if (currentStatus.needsToReset(current)) {
            sequencer = new StringBuilder();
        }
        else {
            if (current.toString().equals("\n")) {
                current = ' ';
            }
            sequencer.append(current);
            if (sequencer.toString().contains(PUBLIC_KEYWORD)) {
                int indexOfKeyword = sequencer.toString().indexOf(PUBLIC_KEYWORD);
                sequencer = new StringBuilder(sequencer.substring(indexOfKeyword));
                currentStatus = AnalysisStatus.KEYWORD_REACHED;
            }
        }
    }

    private void checkForParameterStart(Character current) {
        if (currentStatus.needsToReset(current)) {
            sequencer = new StringBuilder();
            currentStatus = AnalysisStatus.BUILDING_KEYWORD;
        } else {
            sequencer.append(current);
            if (current.equals('(')) {
                currentStatus = AnalysisStatus.PARAMETERS_REACHED;
            }
        }
    }

    private void checkForParameterEnd(Character current) {
        if (currentStatus.needsToReset(current)) {
            sequencer = new StringBuilder();
            currentStatus = AnalysisStatus.BUILDING_KEYWORD;;
        } else {
            // in this status, we already have an open parenthesis for the parameters.
            // If there is another one, we are dealing with annotations inside
            sequencer.append(current);
            if (current.equals(OPEN_PARENTHESIS)) {
                paranthesisStack.add(current);
            }
            else if (current.equals(CLOSE_PARENTHESIS) && !paranthesisStack.isEmpty()) {
                paranthesisStack.pollLast();
            }
            else if (current.equals(CLOSE_PARENTHESIS)){
                count ++;
                currentStatus = AnalysisStatus.BUILDING_KEYWORD;;
                result.add(sequencer.toString());
                sequencer = new StringBuilder();
            }
        }
    }

    @Override
    public Object getMetrics() {
        List<String> resultCopy = new LinkedList<>(result);
        return resultCopy;
    }

    @Override
    public String getMetricName() {
        return "Amount of public methods";
    }
}
