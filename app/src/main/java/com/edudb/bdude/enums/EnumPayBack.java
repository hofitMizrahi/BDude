package com.edudb.bdude.enums;

public enum EnumPayBack{

    CONTRIBUTION(1), MONEY_TRANSFER(2), BRING_BACK(3);

    private int value;

    EnumPayBack(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumPayBack getEnumValueByName(int aValue) {
        for (EnumPayBack viewType : EnumPayBack.values()) {
            if (viewType.getValue() == aValue) {
                return viewType;
            }
        }
        return null;
    }
}
