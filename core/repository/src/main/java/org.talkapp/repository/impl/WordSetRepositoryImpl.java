package org.talkapp.repository.impl;

import org.springframework.stereotype.Component;
import org.talkapp.mapping.WordSetMapping;
import org.talkapp.repository.WordSetRepository;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Budnikau Aliaksandr
 */
@Component
public class WordSetRepositoryImpl implements WordSetRepository {
    public static final String QWE_0 = "qwe0";
    public static final String QWE_1 = "qwe1";
    private Map<String, WordSetMapping> store = new HashMap<>();

    @PostConstruct
    public void init() {
        WordSetMapping mapping = new WordSetMapping();
        mapping.setWords(Arrays.asList("House", "Cat", "Yellow", "Gray", "Run", "Stay"));
        mapping.setId(QWE_0);
        store.put(mapping.getId(), mapping);

        mapping = new WordSetMapping();
        mapping.setWords(Arrays.asList("Wall", "Dog", "Green", "Smart", "Jump", "Pick"));
        mapping.setId(QWE_1);
        store.put(mapping.getId(), mapping);
    }

    @Override
    public void save(WordSetMapping mapping) {
        store.put(mapping.getId(), mapping);
    }

    @Override
    public List<WordSetMapping> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public WordSetMapping findById(String id) {
        return store.get(id);
    }
}
