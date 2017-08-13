package org.talkapp.repository;

import org.talkapp.mapping.WordSetMapping;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
public interface WordSetRepository {
    void save(WordSetMapping mapping);

    List<WordSetMapping> findAll();

    WordSetMapping findById(String id);
}