package ch.hslu.sprg.vbank.model.domainprimitives;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.ValidationException;

public class Comment {

    private final int MIN_LENGTH = 10;
    private final int MAX_LENGTH = 100;
    private final String PATTERN = "[A-Za-z0-9\\s!?.,]+";

    private final String value;

    public Comment(String value) {
        if (value == null || "".equals(value.trim())) {
            throw new ValidationException("Comment cannot be empty");
        }
        else if (value.length() < MIN_LENGTH) {
            throw new ValidationException("Comment too short");
        }
        else if (value.length() > MAX_LENGTH) {
            throw new ValidationException("Comment too long");
        }
        else if (!value.matches(PATTERN)) {
            throw new ValidationException("Comment has invalid chars");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
