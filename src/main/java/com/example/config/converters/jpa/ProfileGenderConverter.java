package com.example.config.converters.jpa;

import com.example.models.Profile;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProfileGenderConverter implements AttributeConverter<Profile.Gender, String>{
    @Override
    public String convertToDatabaseColumn(Profile.Gender gender) {
        switch (gender){
            case MALE:
                return "MALE";
            case FEMALE:
                return "FEMALE";
            case SECRET:
                return "SECRET";
            default:
                throw new IllegalArgumentException("unknown gender "+ gender);
        }
    }

    @Override
    public Profile.Gender convertToEntityAttribute(String dbData) {
        switch (dbData){
            case "MALE":
                return Profile.Gender.MALE;
            case "FEMALE":
                return Profile.Gender.FEMALE;
            case "SECRET":
                return Profile.Gender.SECRET;
            default:
                throw new IllegalArgumentException("unknown value for gender "+dbData);
        }
    }
}
