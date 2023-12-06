package com.example.todolist.utils.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Resource {

    private String name;

    private List<Field> fields;

    public String getFieldsString() {
        List<String> fieldValues = new ArrayList<>();

        this.fields.forEach(field ->
            fieldValues.add(String.format("[%s]: '%s'", field.getName(), field.getValue()))
        );

        if (fieldValues.size() < 3) {
            return String.join(" and ", fieldValues);
        }

        // List has at least three elements
        int lastIdx = fieldValues.size() - 1;
        return String.join(", ", fieldValues.subList(0, lastIdx)) +
                ", and " +
                fieldValues.get(lastIdx);
    }

}
