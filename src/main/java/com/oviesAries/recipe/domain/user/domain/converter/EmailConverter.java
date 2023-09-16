package com.oviesAries.recipe.domain.user.domain.converter;

import com.oviesAries.recipe.domain.user.domain.Email;
import jakarta.persistence.AttributeConverter;

public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(final Email email) {
        return email.getValue();
    }

    @Override
    public Email convertToEntityAttribute(final String email) {
        return Email.from(email);
    }
}
