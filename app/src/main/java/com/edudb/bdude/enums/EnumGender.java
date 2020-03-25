package com.edudb.bdude.enums;

public enum EnumGender {

    MALE("male"), FEMALE("female");

    private String value;

    EnumGender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EnumGender getEnumValueByName(String aValue) {
        for (EnumGender viewType : EnumGender.values()) {
            if (viewType.getValue().equals(aValue)) {
                return viewType;
            }
        }
        return null;
    }
}
