package com.tcdt.qlnvkho.request.object.khotang;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

@Data
public class KtDiemKhoReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Mã không được vượt quá 200 ký tự")
	String maDiemkho;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Tên không được vượt quá 200 ký tự")
	String tenDiemkho;
	String diaChi;
	Long tinhthanhId;
	Long quanhuyenId;
	Long phuongxaId;
	String loaikhoId;
	String chatluongId;
	String tinhTrangId;
	String nhiemVu;
	Long tuyenKhoId;
	BigDecimal dienTichDat;
	BigDecimal tichLuongThietKe;
	BigDecimal tichLuongChua;
	String tinhtrangCsvc;
	Long hosoPhaply;
	Long hosoKhotang;
	BigDecimal giatriConlai;
	Long khoCuId;
	BigDecimal capKho;
	//  Long tongkhoId;
	Long quyhoachDuyetId;
	//@Lob
	//Blob soDoMatBang;
	String diemkhoHientrangId;
	BigDecimal tichLuongKhaDung;
	BigDecimal dienTichDatSodo;
	String soGiayCnQsdd;
	BigDecimal tichLuongChuaLt;
	BigDecimal tichLuongChuaVt;
	BigDecimal tichLuongKdLt;
	BigDecimal tichLuongKdVt;
	BigDecimal tichLuongKdLtvt;
	BigDecimal tichLuongChuaLtGao;
	BigDecimal tichLuongChuaLtThoc;
	BigDecimal thanhTien;
}
