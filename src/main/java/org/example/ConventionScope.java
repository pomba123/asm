package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConventionScope {
    CLASS, METHOD, FIELD;

    @JsonCreator
    public static ConventionScope fromString(String key) {
        if (key == null) return CLASS; // default fallback
        return ConventionScope.valueOf(key.toUpperCase());
    }
}
