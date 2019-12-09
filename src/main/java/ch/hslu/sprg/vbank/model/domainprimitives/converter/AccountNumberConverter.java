package ch.hslu.sprg.vbank.model.domainprimitives.converter;

import ch.hslu.sprg.vbank.model.domainprimitives.AccountNumber;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountNumberConverter implements AttributeConverter<AccountNumber, String> {

    @Override
    public String convertToDatabaseColumn(AccountNumber attribute) {
        return attribute.toString();
    }

    @Override
    public AccountNumber convertToEntityAttribute(String dbData) {
        return new AccountNumber(dbData, false);
    }
}