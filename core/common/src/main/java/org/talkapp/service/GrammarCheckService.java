package org.talkapp.service;


import org.talkapp.model.GrammarCheckResult;

/**
 * @author Budnikau Aliaksandr
 */
public interface GrammarCheckService {
    GrammarCheckResult check(String text);
}
