package com.tcdt.qlnvkho.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumCapDonVi {
	CAP_TONG_CUC(0, "Cấp tổng cục"), CAP_CUC(1, "Cấp cục"), CAP_CHI_CUC(2, "Cấp chi cục");

	private final byte value;

	private final String description;

	EnumCapDonVi(int value, String description) {
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
	public static EnumCapDonVi fromValue(byte value) {
		for (EnumCapDonVi v : EnumCapDonVi.values()) {
			if (v.getValue() == value) {
				return v;
			}
		}
		return null;
	}
}
