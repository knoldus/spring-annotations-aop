package com.knoldus.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.aspect.AOPFilter;
import com.knoldus.aspect.AOPFilterAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AOPHelper {
    private static final String UNKNOWN = "unknown";
    private final Logger logger = LoggerFactory.getLogger(AOPFilterAspect.class);

    private static String safeParse(JsonNode element) {
        return (element == null) ? null : element.textValue();
    }

    private static String readClientId(Object classObj, Field clientIdField) throws IllegalAccessException {
        clientIdField.setAccessible(true);
        Object clientId = clientIdField.get(classObj);
        return (clientId != null ? clientId.toString() : null);
    }

    private static List<Field> getAllFieldsList(final Class<?> eventClass) {
        final List<Field> allFields = new ArrayList<>();
        Class<?> currentClass = eventClass;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            Collections.addAll(allFields, declaredFields);
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    public boolean isAppropriateSmokeTestClientId(JoinPoint joinPoint) throws IllegalAccessException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AOPFilter aopFilter = methodSignature.getMethod().getAnnotation(AOPFilter.class);
        String fieldToFilter = aopFilter.fieldToScan();
        Object value = joinPoint.getArgs()[0];

        ObjectMapper objectMapper = new ObjectMapper();
        String clientId = null;

        List<Field> allFields = getAllFieldsList(value.getClass());

        if (fieldToFilter.contains(".")) {

            try {
                String event = objectMapper.writeValueAsString(value);
                String parentFieldName = fieldToFilter.split("\\.")[0];
                String childFieldName = fieldToFilter.split("\\.")[1];
                clientId = safeParse(objectMapper.readValue(event, JsonNode.class).get(parentFieldName).get(childFieldName));

            } catch (Exception e) {
                logger.warn("Unparseable values are provided in fieldToScan()");
                return true;
            }

        } else {
            Field clientIdField = allFields.stream().filter(field -> field.getName().equals(fieldToFilter)).findFirst().orElse(null);
            if (null != clientIdField) {
                clientId = readClientId(value, clientIdField);
            }
        }

        if (null != clientId) {
            return !clientId.equals(UNKNOWN) && clientId.equals(aopFilter.filterValue());
        }
        return true;
    }

}