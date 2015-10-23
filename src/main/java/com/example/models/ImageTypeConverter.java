package com.example.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageTypeConverter implements AttributeConverter<Image.Type, String>{

    @Override
    public String convertToDatabaseColumn(Image.Type type) {
        switch (type){
            case PNG:
                return "PNG";
            case WEBP:
                return "WEBP";
            case BMP:
                return "BMP";
            case JPEG:
                return "JPEG";
            default:
                throw new IllegalArgumentException("Unknown"+type);
        }
    }

    @Override
    public Image.Type convertToEntityAttribute(String dbData) {
        switch (dbData){
            case "PNG":
                return Image.Type.PNG;
            case "WEBP":
                return Image.Type.WEBP;
            case "BMP":
                return Image.Type.BMP;
            case "JPEG":
                return Image.Type.JPEG;
            default:
                throw new IllegalArgumentException("Unknown"+dbData);
        }
    }
}
