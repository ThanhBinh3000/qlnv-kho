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
public class KtNhaKhoReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Mã không được vượt quá 200 ký tự")
	String maNhakho;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Tên không được vượt quá 200 ký tự")
	String tenNhakho;
//	@Temporal(TemporalType.DATE)
//	Date namSudung;
	String nhiemVu;
	String loaikhoId;
	String chatluongId;
	String tinhtrangId;
	String nhakhoHientrangId;
	BigDecimal dienTichDat;
	BigDecimal tichLuongTk;
	Long diemkhoId;
	Long quyhoachDuyetId;
	//@Lob
	//Blob banVeKt;
	BigDecimal tichLuongChua;
	BigDecimal tichLuongKhaDung;
	BigDecimal tichLuongChuaLt;
	BigDecimal tichLuongChuaVt;
	BigDecimal tichLuongKdLt;
	BigDecimal tichLuongKdVt;
	BigDecimal tichLuongKdLtvt;
	BigDecimal tichLuongChuaLtGao;
	BigDecimal tichLuongChuaLtThoc;
}
