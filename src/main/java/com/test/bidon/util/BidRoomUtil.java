package com.test.bidon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.socket.TextMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BidRoomUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Logger logger = Logger.getLogger(BidRoomUtil.class.getName());

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "JSON parsing error", e);
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "JSON writing error", e);
            throw new RuntimeException(e);
        }
    }

    public static TextMessage toTextMessage(Object message) {
        return new TextMessage(toJson(message));
    }

    public static <T> T convert(Object source, Class<T> clazz) {
        return objectMapper.convertValue(source, clazz);
    }

}