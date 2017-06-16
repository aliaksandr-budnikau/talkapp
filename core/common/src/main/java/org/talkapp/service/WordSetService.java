package org.talkapp.service;

import org.talkapp.model.WordSet;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
public interface WordSetService {
    void save(WordSet wordSet);

    List<WordSet> findAll();

    WordSet findById(String id);
}