package com.example.config.converters.jpa;

import com.example.models.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProfileGenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        switch (gender) {
            case MALE:
                return "MALE";
            case FEMALE:
                return "FEMALE";
            case SECRET:
                return "SECRET";
            default:
                throw new IllegalArgumentException("unknown gender " + gender);
        }
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "MALE":
                return Gender.MALE;
            case "FEMALE":
                return Gender.FEMALE;
            case "SECRET":
                return Gender.SECRET;
            default:
                throw new IllegalArgumentException("unknown value for gender " + dbData);
        }
    }
}
