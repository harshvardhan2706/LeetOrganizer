package com.harshvardhan.leetorganizer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Statistics {

    private final Map<String, List<String>> languageProblems;

    public Statistics(Map<String, List<String>> languageProblems) {
        this.languageProblems = Collections.unmodifiableMap(Objects.requireNonNull(languageProblems, "languageProblems cannot be null"));
    }

    public Map<String, List<String>> getLanguageProblems() {
        return languageProblems;
    }

    public int getTotalProblems() {
        return languageProblems.values().stream()
                .mapToInt(List::size)
                .sum();
    }
}
