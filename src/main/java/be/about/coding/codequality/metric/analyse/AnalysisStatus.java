package be.about.coding.codequality.metric.analyse;

import java.util.ArrayList;
import java.util.List;

enum AnalysisStatus {
    BUILDING_KEYWORD(List.of('=', ';', '{', '}')),
    KEYWORD_REACHED(List.of('=', ';', '{', '}')),
    PARAMETERS_REACHED(List.of(';'));

    private final List<Character> resetCharacters;
    AnalysisStatus(List<Character> resetCharacters) {
        this.resetCharacters = new ArrayList<>(resetCharacters);
    }

    public boolean needsToReset(char current) {
        return this.resetCharacters.contains(current);
    }
}
