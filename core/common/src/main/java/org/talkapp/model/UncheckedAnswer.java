package org.talkapp.model;

import java.util.Objects;

/**
 * @author Budnikau Aliaksandr
 */
public class UncheckedAnswer {
    private String wordSetId;
    private String expectedAnswer;
    private String actualAnswer;

    public String getWordSetId() {
        return wordSetId;
    }

    public void setWordSetId(String wordSetId) {
        this.wordSetId = wordSetId;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public String getActualAnswer() {
        return actualAnswer;
    }

    public void setActualAnswer(String actualAnswer) {
        this.actualAnswer = actualAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UncheckedAnswer that = (UncheckedAnswer) o;
        return Objects.equals(wordSetId, that.wordSetId) &&
                Objects.equals(expectedAnswer, that.expectedAnswer) &&
                Objects.equals(actualAnswer, that.actualAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordSetId, expectedAnswer, actualAnswer);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UncheckedAnswer{");
        sb.append("wordSetId='").append(wordSetId).append('\'');
        sb.append(", expectedAnswer='").append(expectedAnswer).append('\'');
        sb.append(", actualAnswer='").append(actualAnswer).append('\'');
        sb.append('}');
        return sb.toString();
    }
}