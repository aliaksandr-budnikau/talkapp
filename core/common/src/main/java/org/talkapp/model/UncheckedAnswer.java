package org.talkapp.model;

import java.util.Objects;

/**
 * @author Budnikau Aliaksandr
 */
public class UncheckedAnswer {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UncheckedAnswer that = (UncheckedAnswer) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UncheckedAnswer{");
        sb.append("text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}