package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.mapping.WordSetMapping;
import org.talkapp.model.WordSet;
import org.talkapp.repository.WordSetRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class WordSetServiceImpl implements WordSetService {
    @Autowired
    private WordSetRepository wordSetRepository;

    @Override
    public void save(WordSet wordSet) {
        wordSetRepository.save(toMapping(wordSet));
    }

    @Override
    public List<WordSet> findAll() {
        List<WordSet> result = new LinkedList<>();
        for (WordSetMapping mapping : wordSetRepository.findAll()) {
            result.add(toDto(mapping));
        }
        return result;
    }

    @Override
    public WordSet findById(String id) {
        return toDto(wordSetRepository.findById(id));
    }

    private WordSetMapping toMapping(WordSet wordSet) {
        WordSetMapping mapping = new WordSetMapping();
        mapping.setId(wordSet.getId());
        mapping.setWords(wordSet.getWords());
        return mapping;
    }

    private WordSet toDto(WordSetMapping mapping) {
        WordSet wordSet = new WordSet();
        wordSet.setId(mapping.getId());
        wordSet.setWords(mapping.getWords());
        return wordSet;
    }
}