package org.talkapp.service;

import org.talkapp.model.GrammarCheckResult;

/**
 * @author Budnikau Aliaksandr
 */
public interface UncheckedAnswerService {
    GrammarCheckResult check(String text);
}