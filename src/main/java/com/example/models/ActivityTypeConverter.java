package com.example.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActivityTypeConverter implements AttributeConverter<Activity.Type, String> {
    @Override
    public String convertToDatabaseColumn(Activity.Type type) {
        switch (type) {
            case MATCH:
                return "match";
            case BLACK:
                return "black";
            default:
                throw new IllegalArgumentException("Unknown type " + type);
        }
    }

    @Override
    public Activity.Type convertToEntityAttribute(String type) {
        switch (type) {
            case "match":
                return Activity.Type.MATCH;
            case "black":
                return Activity.Type.BLACK;
            default:
                throw new IllegalArgumentException("Unknown type " + type);
        }
    }
}
