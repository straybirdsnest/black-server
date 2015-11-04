package com.example.config.converters.jpa;

import com.example.models.Activity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActivityTypeConverter implements AttributeConverter<Activity.Type, String>{
    @Override
    public String convertToDatabaseColumn(Activity.Type type) {
        switch (type){
            case MATCH:
                return "MATCH";
            case BLACK:
                return "BLACK";
            default:
                throw new IllegalArgumentException("unknown activity type "+type);
        }
    }

    @Override
    public Activity.Type convertToEntityAttribute(String dbData) {
        switch (dbData){
            case "MATCH":
                return Activity.Type.MATCH;
            case "BLACK":
                return Activity.Type.BLACK;
            default:
                throw new IllegalArgumentException("unknown activity type "+dbData);
        }
    }
}
