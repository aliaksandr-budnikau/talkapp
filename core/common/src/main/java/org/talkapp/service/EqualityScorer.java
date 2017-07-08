package org.talkapp.service;

/**
 * @author Budnikau Aliaksandr
 */
public interface EqualityScorer {
    int score(String expected, String actual);
}