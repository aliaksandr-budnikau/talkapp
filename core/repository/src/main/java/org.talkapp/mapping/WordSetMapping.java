package org.talkapp.mapping;

import java.util.List;
import java.util.Objects;

/**
 * @author Budnikau Aliaksandr
 */
public class WordSetMapping {
    private String id;

    private List<String> words;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSetMapping that = (WordSetMapping) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, words);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WordSetMapping{");
        sb.append("id='").append(id).append('\'');
        sb.append(", words=").append(words);
        sb.append('}');
        return sb.toString();
    }
}
