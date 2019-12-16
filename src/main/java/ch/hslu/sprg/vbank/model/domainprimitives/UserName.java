package ch.hslu.sprg.vbank.model.domainprimitives;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.Objects;

public class UserName implements Serializable {

    public static final String USERNAME_PATTERN = "^\\w{3,30}?$";

    private String value;

    public UserName(String username) {
        if (username == null || username.length() == 0) {
            //it cannot be null
            throw new ValidationException("Username is required");
        } else if (username.length() < 3) {
            //it should be 11 long
            throw new ValidationException("Username should be at least 3 characters long");
        } else if (username.length() > 30) {
            //it should be 11 long
            throw new ValidationException("Username should be no longer than 30 characters");
        } else if (!username.matches(USERNAME_PATTERN)) {
            //it has to match patter
            throw new ValidationException("Username is in invalid format");
        }        this.value = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName that = (UserName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
