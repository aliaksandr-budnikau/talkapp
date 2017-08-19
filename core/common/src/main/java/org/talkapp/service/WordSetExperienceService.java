package org.talkapp.service;

import org.talkapp.model.WordSetExperience;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
public interface WordSetExperienceService {
    void save(WordSetExperience wordSetExperience);

    List<WordSetExperience> findAll();

    WordSetExperience findById(String id);

    List<WordSetExperience> findAllByAccountId(String accountId);
}