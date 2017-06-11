package org.talkapp.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.talkapp.mapping.SentenceMapping;

/**
 * @author Budnikau Aliaksandr
 */
public interface SentenceRepository extends ElasticsearchRepository<SentenceMapping, String> {
}