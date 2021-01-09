package com.josep.hris.enums;

public enum CompanySetupInitialDataEnum {
    DONE("Active", 1),
    NOT_YET("Non Active", 0);

    private final String key;
    private final Integer value;

    CompanySetupInitialDataEnum(String key, Integer value) {
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
