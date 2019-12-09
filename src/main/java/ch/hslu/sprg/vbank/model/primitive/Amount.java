package ch.hslu.sprg.vbank.model.primitive;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Amount implements Serializable {

    private BigDecimal value;

    public Amount(String amount) {
        this(new BigDecimal(amount));
    }


    public Amount(BigDecimal amount) {
        if (amount.scale() > 2) {
            throw new ValidationException("Amount can have max 2 digits after the decimal place.");
        } else if (amount.doubleValue() <= 0) {
            throw new ValidationException("Amount must be positive");
        }
        this.value = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount that = (Amount) o;
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
    public BigDecimal getValue() {
        return value;
    }
}