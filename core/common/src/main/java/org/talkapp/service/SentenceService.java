package org.talkapp.service;

import org.talkapp.model.Sentence;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
public interface SentenceService {
    void save(Sentence sentence);

    List<Sentence> findAll();

    List<Sentence> findByWords(String words);
}