package com.tcdt.qlnvkho.request.object.khotang;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class KtTongKhoReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Mã không được vượt quá 200 ký tự")
	String maTongKho;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Tên không được vượt quá 200 ký tự")
	String tenTongKho;
	String diaChi;
	Long tinhthanhId;
	Long quanhuyenId;
	Long phuongxaId;
	BigDecimal tichLuong;
	String nhiemVu;
	String chatLuongId;
	String tinhTrangId;
	Long hosoPhaply;
	Long hosoKhotang;
	BigDecimal giatriConlai;
	String khoCuId;
	Long capKho;
	Long dtqgkvId;
	Long quyhoachDuyetId;
	String tongkhoHientrangId;
	BigDecimal tichLuongThietKe;
	BigDecimal tichLuongKhaDung;
	BigDecimal tichLuongChuaLt;
	BigDecimal tichLuongChuaVt;
	BigDecimal tichLuongKdLt;
	BigDecimal tichLuongKdVt;
	BigDecimal tichLuongKdLtvt;
	BigDecimal tichLuongChuaLtGao;
	BigDecimal tichLuongChuaLtThoc;
	String ldoTuchoi;
}
