package org.talkapp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class EqualityScorerImpl implements EqualityScorer {

    public static final String REPLACEMENT = " ";
    public static final String REGEX = "[^A-Za-z0-9]";

    @Override
    public int score(String expected, String actual) {
        if (expected.equals(actual)) {
            return 100;
        }

        Set<String> expectedWords = toSet(expected);
        Set<String> actualWords = toSet(actual);

        int result = 0;
        for (String word : expectedWords) {
            if (actualWords.remove(word)) {
                result += 100 / expectedWords.size();
            }
        }

        return result;
    }

    @NotNull
    private HashSet<String> toSet(String expected) {
        return new HashSet<>(Arrays.asList(expected.replaceAll(REGEX, REPLACEMENT).split(REPLACEMENT)));
    }
}