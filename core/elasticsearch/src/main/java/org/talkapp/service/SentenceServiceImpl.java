package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.talkapp.mapping.SentenceMapping;
import org.talkapp.model.Sentence;
import org.talkapp.repository.SentenceRepository;

import java.util.LinkedList;
import java.util.List;


import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class SentenceServiceImpl implements SentenceService {
    @Autowired
    private SentenceRepository sentenceRepository;
    @Autowired
    private ElasticsearchOperations operations;

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
    public List<Sentence> findByWords(String words) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("text", words).operator(AND))
                .build();
        List<SentenceMapping> mappings = operations.queryForList(searchQuery, SentenceMapping.class);
        List<Sentence> result = new LinkedList<>();
        for (SentenceMapping mapping : mappings) {
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