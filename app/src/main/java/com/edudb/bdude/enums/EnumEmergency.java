package com.edudb.bdude.enums;

public enum EnumEmergency {

    AGE_AT_RISK(1), ISOLATE(2), SICK(3);

    private int value;

    EnumEmergency(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumEmergency getEnumValueByName(int aValue) {
        for (EnumEmergency viewType : EnumEmergency.values()) {
            if (viewType.getValue() == aValue) {
                return viewType;
            }
        }
        return null;
    }
}
