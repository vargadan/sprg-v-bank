package ch.hslu.sprg.vbank.model.domainprimitives.converter;

import ch.hslu.sprg.vbank.model.domainprimitives.Amount;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter(autoApply = true)
public class AmountConverter implements AttributeConverter<Amount, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Amount attribute) {
        return attribute.getValue();
    }

    @Override
    public Amount convertToEntityAttribute(BigDecimal dbData) {
        return new Amount(dbData);
    }
}