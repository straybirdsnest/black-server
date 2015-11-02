package com.example.models.core;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<User.Gender,String >{

    @Override
    public String convertToDatabaseColumn(User.Gender gender) {
        switch (gender){
            case MALE:
                return "male";
            case FEMALE:
                return "female";
            case SECRET:
                return "secret";
            default:
                throw new IllegalArgumentException("Unknown gender "+gender);
        }
    }

    @Override
    public User.Gender convertToEntityAttribute(String dbData) {
        switch(dbData){
            case "male":
                return User.Gender.MALE;
            case "female":
                return User.Gender.FEMALE;
            case "secret":
                return User.Gender.SECRET;
            default:
                throw new IllegalArgumentException("Unknown gender "+dbData);
        }
    }
}
