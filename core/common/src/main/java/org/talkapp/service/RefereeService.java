package org.talkapp.service;

import org.talkapp.model.AnswerCheckingResult;
import org.talkapp.model.UncheckedAnswer;

/**
 * @author Budnikau Aliaksandr
 */
public interface RefereeService {
    AnswerCheckingResult checkAnswer(UncheckedAnswer answer);
}