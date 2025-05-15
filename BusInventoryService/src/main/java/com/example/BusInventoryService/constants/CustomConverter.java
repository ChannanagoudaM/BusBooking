package com.example.BusInventoryService.constants;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class CustomConverter implements AttributeConverter<List<BusType>, String> {

    @Override
    public String convertToDatabaseColumn(List<BusType> attribute) {
        if (attribute == null || attribute.isEmpty()) return "";
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<BusType> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return new ArrayList<>();
        return Arrays.stream(dbData.split(","))
                .map(String::trim)
                .map(BusType::valueOf)
                .collect(Collectors.toList());
    }
}
