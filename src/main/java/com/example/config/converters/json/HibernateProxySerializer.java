package com.example.config.converters.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.io.IOException;

public class HibernateProxySerializer extends JsonSerializer<HibernateProxy> {
    @Override
    public void serialize(HibernateProxy proxy, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        LazyInitializer init = proxy.getHibernateLazyInitializer();
        System.out.println(init.getEntityName());
        SessionImplementor session = init.getSession();

    }
}
