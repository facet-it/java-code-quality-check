package be.about.coding.codequality.metric.analyse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Going to try and implement some kind of state machine.
 */
@Component
@Scope("prototype")
public class PublicMethodAnalysis implements AnalysisListener {

    //the space is important, because you might have false positives on public
    private final String PUBLIC_KEYWORD = "public ";
    private final List<Character> resetCharacters = List.of('=', ';', '{', '}');

    private StringBuilder sequencer = new StringBuilder();
    private int count = 0;

    private final int BUILDING_KEYWORD = 0;
    private final int KEYWORD_REACHED = 1;
    private final int PARAMETERS_REACHED = 2;

    private int currentStatus = 0;

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
        if (resetCharacters.contains(current)) {
            sequencer = new StringBuilder();
        } else {
            sequencer.append(current);
            if (sequencer.toString().equals(PUBLIC_KEYWORD)) {
                currentStatus = KEYWORD_REACHED;
            }
        }
    }

    private void checkForParameterStart(Character current) {
        if (resetCharacters.contains(current)) {
            sequencer = new StringBuilder();
        } else {
            sequencer.append(current);
            if (current.equals('(')) {
                currentStatus = PARAMETERS_REACHED;
            }
        }
    }

    private void checkForParameterEnd(Character current) {
        if (resetCharacters.contains(current)) {
            sequencer = new StringBuilder();
        } else {
            sequencer.append(current);
            if (current.equals(')')) {
                count ++;
                currentStatus = BUILDING_KEYWORD;
                result.add(sequencer.toString());
            }
        }
    }

    private boolean shouldResetSequencer(Character current) {
        //todo fill this
        return false;
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
