package com.tcdt.qlnvkho.request.object;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class KtTrangthaiHienthoiReq {
	@NotNull(message = "Không được để trống")
	String maDvi;
	@NotNull(message = "Không được để trống")
	String maVthh;
}
