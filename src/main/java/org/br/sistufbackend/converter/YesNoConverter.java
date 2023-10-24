package org.br.sistufbackend.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.br.sistufbackend.model.enums.YesNo;

import java.util.Objects;

@Converter
public class YesNoConverter implements AttributeConverter<YesNo, String> {

    @Override
    public String convertToDatabaseColumn(YesNo attribute) {
        return Objects.nonNull(attribute) ? attribute.name() : null;
    }

    @Override
    public YesNo convertToEntityAttribute(String dbData) {
        try {
            return YesNo.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            return YesNo.N;
        }
    }
}
