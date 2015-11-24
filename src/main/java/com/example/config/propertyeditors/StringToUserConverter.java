package com.example.config.propertyeditors;

import com.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class StringToUserConverter implements Converter<String, User> {
    private static final Logger logger = LoggerFactory.getLogger(StringToUserConverter.class);

    @Override
    public User convert(String source) {
        logger.debug(source);
        return new User();
    }
}
