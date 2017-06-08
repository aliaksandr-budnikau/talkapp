package org.talkapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Budnikau Aliaksandr
 */
public class GrammarCheckResult {
    private List<GrammarError> errors = new ArrayList<>();

    public List<GrammarError> getErrors() {
        return errors;
    }

    public void setErrors(List<GrammarError> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrammarCheckResult that = (GrammarCheckResult) o;
        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GrammarCheckResult{");
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }
}
