package com.josep.hris.enums;

public enum CompanyStatusEnum {
    NON_ACTIVE("Non Active", 0),
    ACTIVE("Active", 1);

    private final String key;
    private final Integer value;

    CompanyStatusEnum(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
