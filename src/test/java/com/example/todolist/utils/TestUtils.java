package com.example.todolist.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param obj object to be converted into a JSON String
     * @return the object converted in String Format
     */
    public static String convertObjToJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
