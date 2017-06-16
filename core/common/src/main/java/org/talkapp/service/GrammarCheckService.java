package org.talkapp.service;


import org.talkapp.model.AnswerCheckingResult;

/**
 * @author Budnikau Aliaksandr
 */
public interface GrammarCheckService {
    AnswerCheckingResult check(String text);
}
