package com.example.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActivityStatusConverter implements AttributeConverter<Activity.Status, String> {

    @Override
    public String convertToDatabaseColumn(Activity.Status status) {
        switch (status) {
            case READY:
                return "ready";
            case RUNNING:
                return "running";
            case STOPPED:
                return "stopped";
            default:
                throw new IllegalArgumentException("Unknown status " + status);
        }
    }

    @Override
    public Activity.Status convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "ready":
                return Activity.Status.READY;
            case "running":
                return Activity.Status.RUNNING;
            case "stopped":
                return Activity.Status.STOPPED;
            default:
                throw new IllegalArgumentException("Unknown status " + dbData);
        }
    }
}
