package com.josep.hris.enums;

public enum RoleEnum {
    SUPER_ADMIN("Super Admin", 1L),
    ADMIN("Admin", 2L);

    private final String key;
    private final Long value;

    RoleEnum(String key, Long value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Long getValue() {
        return value;
    }
}
