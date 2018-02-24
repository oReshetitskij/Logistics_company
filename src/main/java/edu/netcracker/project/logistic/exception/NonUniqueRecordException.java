package edu.netcracker.project.logistic.exception;

import java.util.*;

public class NonUniqueRecordException extends RuntimeException {
    private Set<String> fields;

    public NonUniqueRecordException(String field) {
        this.fields = Collections.singleton(field);
    }

    public NonUniqueRecordException(Collection<String> fields) {
        this.fields = Collections.unmodifiableSet(new HashSet<>(fields));
    }

    public Set<String> duplicateFields() {
        return fields;
    }
}
