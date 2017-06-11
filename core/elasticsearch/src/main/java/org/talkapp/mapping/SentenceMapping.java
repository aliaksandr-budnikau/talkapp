package org.talkapp.mapping;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author Budnikau Aliaksandr
 */
@Document(indexName = "sentence_index", type = "sentence_type")
@Mapping(mappingPath = "/mappings/mappings.json")
public class SentenceMapping {
    @Id
    private String id;
    private String text;
    private Map<String, String> translations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentenceMapping sentence = (SentenceMapping) o;
        return Objects.equals(id, sentence.id) &&
                Objects.equals(text, sentence.text) &&
                Objects.equals(translations, sentence.translations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, translations);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SentenceMapping{");
        sb.append("id='").append(id).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", translations=").append(translations);
        sb.append('}');
        return sb.toString();
    }
}
