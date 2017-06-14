package org.talkapp.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.talkapp.mapping.SentenceMapping;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
public interface SentenceRepository extends ElasticsearchRepository<SentenceMapping, String> {
    @Query("{\"bool\": {\"must\": {\"match\": {\"text\": {\"query\" : \"?0\", \"operator\" : \"and\"}}}}}")
    List<SentenceMapping> findByWords(String words);
}