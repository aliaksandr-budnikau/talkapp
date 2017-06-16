package org.talkapp.mapping;

import java.util.List;
import java.util.Objects;

/**
 * @author Budnikau Aliaksandr
 */
public class WordSetMapping {
    private String id;

    private List<String> words;

    private int trainingExperience;

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

    public int getTrainingExperience() {
        return trainingExperience;
    }

    public void setTrainingExperience(int trainingExperience) {
        this.trainingExperience = trainingExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSetMapping wordSet = (WordSetMapping) o;
        return id == wordSet.id &&
                trainingExperience == wordSet.trainingExperience &&
                Objects.equals(words, wordSet.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, words, trainingExperience);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WordSetMapping{");
        sb.append("id=").append(id);
        sb.append(", words=").append(words);
        sb.append(", trainingExperience=").append(trainingExperience);
        sb.append('}');
        return sb.toString();
    }
}
