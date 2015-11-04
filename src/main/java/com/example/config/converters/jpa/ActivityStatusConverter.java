package com.example.config.converters.jpa;

import com.example.models.Activity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActivityStatusConverter implements AttributeConverter<Activity.Status, String>{

    @Override
    public String convertToDatabaseColumn(Activity.Status status) {
        switch (status){
            case READY:
                return "READY";
            case RUNNING:
                return "RUNNING";
            case STOPPED:
                return "STOPPED";
            default:
                throw new IllegalArgumentException("unknown activity status "+status);
        }
    }

    @Override
    public Activity.Status convertToEntityAttribute(String dbData) {
        switch (dbData){
            case "READY":
                return Activity.Status.READY;
            case "RUNNING":
                return Activity.Status.RUNNING;
            case "STOPPED":
                return Activity.Status.STOPPED;
            default:
                throw new IllegalArgumentException("unknown activity status "+dbData);
        }
    }
}
