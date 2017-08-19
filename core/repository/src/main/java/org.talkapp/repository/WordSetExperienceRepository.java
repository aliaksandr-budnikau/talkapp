package org.talkapp.repository;

import org.talkapp.mapping.WordSetExperienceMapping;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
public interface WordSetExperienceRepository {

    void save(WordSetExperienceMapping mapping);

    List<WordSetExperienceMapping> findAll();

    WordSetExperienceMapping findById(String id);

    List<WordSetExperienceMapping> findAllByAccountId(String accountId);
}