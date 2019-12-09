package ch.hslu.sprg.vbank.model.domainprimitives.converter;

import ch.hslu.sprg.vbank.model.domainprimitives.UserName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserNameConverter implements AttributeConverter<UserName, String> {

    @Override
    public String convertToDatabaseColumn(UserName attribute) {
        return attribute.toString();
    }

    @Override
    public UserName convertToEntityAttribute(String dbData) {
        return new UserName(dbData);
    }
}