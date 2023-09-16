package com.oviesAries.recipe.domain.user.domain.converter;


import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
import jakarta.persistence.AttributeConverter;

public class PasswordConverter implements AttributeConverter<EncodedPassword, String> {

    @Override
    public String convertToDatabaseColumn(final EncodedPassword encodedPassword) {
        return encodedPassword.getValue();
    }

    @Override
    public EncodedPassword convertToEntityAttribute(final String encodedPassword) {
        return EncodedPassword.from(encodedPassword);
    }
}
