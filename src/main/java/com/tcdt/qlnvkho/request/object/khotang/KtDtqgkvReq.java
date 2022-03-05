package com.tcdt.qlnvkho.request.object.khotang;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class KtDtqgkvReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Mã không được vượt quá 200 ký tự")
	String maDtqgkv;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Tên không được vượt quá 200 ký tự")
	String tenDtqgkv;
	String diaChi;
	Long tinhthanhId;
	Long quanhuyenId;
	Long phuongxaId;
	String moTa;
	Long quyhoachDuyetId;
	Long dtqgkvHientrangId;
	BigDecimal tichLuongKhaDung;
	BigDecimal tichLuongChua;
	BigDecimal tichLuongChuaLt;
	BigDecimal tichLuongChuaVt;
	BigDecimal tichLuongKdLt;
	BigDecimal tichLuongKdVt;
	BigDecimal tichLuongThietKe;
	BigDecimal tichLuongKdLtvt;
	BigDecimal tichLuongChuaLtGao;
	BigDecimal tichLuongChuaLtThoc;
	String ldoTuchoi;
}
