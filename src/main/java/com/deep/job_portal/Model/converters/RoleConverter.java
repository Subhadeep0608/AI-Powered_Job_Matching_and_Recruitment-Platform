package com.deep.job_portal.Model.converters;

import com.deep.job_portal.Model.enums.Role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role == null ? null : role.name().toLowerCase();
    }

    @Override
    public Role convertToEntityAttribute(String value) {
        return value == null ? null : Role.valueOf(value.toUpperCase());
    }
}
