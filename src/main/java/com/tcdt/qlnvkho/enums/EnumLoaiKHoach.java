package com.tcdt.qlnvkho.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumLoaiKHoach {
    TRUNG_HAN(0, "Trung hạn"),
    HANG_NAM(1, "Hàng năm"),
    THUONG_XUYEN(2, "Thường xuyên");

    private final byte value;

    private final String description;

    EnumLoaiKHoach(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @JsonValue
    public byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static EnumLoaiKHoach fromValue(byte value) {
        for (EnumLoaiKHoach v : EnumLoaiKHoach.values()) {
            if (v.getValue() == value) {
                return v;
            }
        }
        return null;
    }
}
