package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.mapping.WordSetExperienceMapping;
import org.talkapp.model.WordSetExperience;
import org.talkapp.repository.WordSetExperienceRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class WordSetExperienceServiceImpl implements WordSetExperienceService {
    @Autowired
    private WordSetExperienceRepository wordSetExperienceRepository;

    @Override
    public void save(WordSetExperience wordSetExperience) {
        wordSetExperienceRepository.save(toMapping(wordSetExperience));
    }

    @Override
    public List<WordSetExperience> findAll() {
        List<WordSetExperience> result = new LinkedList<>();
        for (WordSetExperienceMapping mapping : wordSetExperienceRepository.findAll()) {
            result.add(toDto(mapping));
        }
        return result;
    }

    @Override
    public List<WordSetExperience> findAllByAccountId(String accountId) {
        List<WordSetExperience> result = new LinkedList<>();
        for (WordSetExperienceMapping mapping : wordSetExperienceRepository.findAllByAccountId(accountId)) {
            result.add(toDto(mapping));
        }
        return result;
    }

    @Override
    public WordSetExperience findById(String id) {
        return toDto(wordSetExperienceRepository.findById(id));
    }

    private WordSetExperienceMapping toMapping(WordSetExperience wordSetExperience) {
        WordSetExperienceMapping mapping = new WordSetExperienceMapping();
        mapping.setId(wordSetExperience.getId());
        mapping.setWordSetId(wordSetExperience.getWordSetId());
        mapping.setAccountId(wordSetExperience.getAccountId());
        mapping.setTrainingExperience(wordSetExperience.getTrainingExperience());
        mapping.setMaxTrainingExperience(wordSetExperience.getMaxTrainingExperience());
        return mapping;
    }

    private WordSetExperience toDto(WordSetExperienceMapping mapping) {
        WordSetExperience wordSetExperience = new WordSetExperience();
        wordSetExperience.setId(mapping.getId());
        wordSetExperience.setWordSetId(mapping.getWordSetId());
        wordSetExperience.setAccountId(mapping.getAccountId());
        wordSetExperience.setTrainingExperience(mapping.getTrainingExperience());
        wordSetExperience.setMaxTrainingExperience(mapping.getMaxTrainingExperience());
        return wordSetExperience;
    }
}