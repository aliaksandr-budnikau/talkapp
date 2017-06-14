package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.talkapp.mapping.SentenceMapping;
import org.talkapp.model.Sentence;
import org.talkapp.repository.SentenceRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class SentenceServiceImpl implements SentenceService {
    @Autowired
    private SentenceRepository sentenceRepository;
    @Value("${core.srv.elasticsearch.clearing.enabled}")
    private boolean clearingEnabled;

    @Override
    public void save(Sentence sentence) {
        sentenceRepository.save(toMapping(sentence));
    }

    @Override
    public List<Sentence> findAll() {
        List<Sentence> result = new LinkedList<>();
        for (SentenceMapping mapping : sentenceRepository.findAll()) {
            result.add(toSentence(mapping));
        }
        return result;
    }

    @Override
    public void deleteAll() {
        if (clearingEnabled) {
            sentenceRepository.deleteAll();
        } else {
            throw new RuntimeException("Cleaning is disabled!");
        }
    }

    @Override
    public List<Sentence> findByWords(String words) {
        List<Sentence> result = new LinkedList<>();
        for (SentenceMapping mapping : sentenceRepository.findByWords(words)) {
            result.add(toSentence(mapping));
        }
        return result;
    }

    private SentenceMapping toMapping(Sentence sentence) {
        SentenceMapping mapping = new SentenceMapping();
        mapping.setId(sentence.getId());
        mapping.setText(sentence.getText());
        mapping.setTranslations(sentence.getTranslations());
        return mapping;
    }

    private Sentence toSentence(SentenceMapping mapping) {
        Sentence sentence = new Sentence();
        sentence.setId(mapping.getId());
        sentence.setText(mapping.getText());
        sentence.setTranslations(mapping.getTranslations());
        return sentence;
    }
}