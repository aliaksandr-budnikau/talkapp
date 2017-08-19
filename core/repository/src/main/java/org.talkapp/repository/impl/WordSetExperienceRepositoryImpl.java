package org.talkapp.repository.impl;

import org.springframework.stereotype.Component;
import org.talkapp.mapping.WordSetExperienceMapping;
import org.talkapp.repository.WordSetExperienceRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Budnikau Aliaksandr
 */
@Component
public class WordSetExperienceRepositoryImpl implements WordSetExperienceRepository {
    public static final String QWE_0 = "qwe0";
    public static final String QWE_1 = "qwe1";
    public static final String QWE_2 = "qwe2";
    public static final String QWE_3 = "qwe3";
    private Map<String, WordSetExperienceMapping> store = new HashMap<>();

    @PostConstruct
    public void init() {
        WordSetExperienceMapping mapping = new WordSetExperienceMapping();
        mapping.setTrainingExperience(0);
        mapping.setMaxTrainingExperience(4);
        mapping.setAccountId(AccountRepositoryImpl.QWE_0);
        mapping.setId(QWE_0);
        mapping.setWordSetId(WordSetRepositoryImpl.QWE_0);
        store.put(mapping.getId(), mapping);

        mapping = new WordSetExperienceMapping();
        mapping.setTrainingExperience(1);
        mapping.setMaxTrainingExperience(4);
        mapping.setAccountId(AccountRepositoryImpl.QWE_0);
        mapping.setWordSetId(WordSetRepositoryImpl.QWE_1);
        mapping.setId(QWE_1);
        store.put(mapping.getId(), mapping);

        mapping = new WordSetExperienceMapping();
        mapping.setTrainingExperience(3);
        mapping.setMaxTrainingExperience(3);
        mapping.setAccountId(AccountRepositoryImpl.QWE_1);
        mapping.setId(QWE_2);
        mapping.setWordSetId(WordSetRepositoryImpl.QWE_0);
        store.put(mapping.getId(), mapping);

        mapping = new WordSetExperienceMapping();
        mapping.setTrainingExperience(4);
        mapping.setMaxTrainingExperience(5);
        mapping.setAccountId(AccountRepositoryImpl.QWE_1);
        mapping.setWordSetId(WordSetRepositoryImpl.QWE_1);
        mapping.setId(QWE_3);
        store.put(mapping.getId(), mapping);
    }

    @Override
    public void save(WordSetExperienceMapping mapping) {
        store.put(mapping.getId(), mapping);
    }

    @Override
    public List<WordSetExperienceMapping> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<WordSetExperienceMapping> findAllByAccountId(String accountId) {
        ArrayList<WordSetExperienceMapping> result = new ArrayList<>();
        for (WordSetExperienceMapping mapping : store.values()) {
            if (accountId.equals(mapping.getAccountId())) {
                result.add(mapping);
            }
        }
        return result;
    }

    @Override
    public WordSetExperienceMapping findById(String id) {
        return store.get(id);
    }
}