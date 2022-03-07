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
public class KtNganLoReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Mã không được vượt quá 200 ký tự")
	String maNganlo;
	@NotNull(message = "Không được để trống")
	@Size(max = 200, message = "Tên không được vượt quá 200 ký tự")
	String tenNganlo;
	//@Temporal(TemporalType.DATE)
	//Date namSudung;
	String nhiemVu;
	String loaikhoId;
	String chatluongId;
	String tinhtrangId;
	String nganloHientrangId;
	BigDecimal dienTichDat;
	BigDecimal tichLuongTk;
	BigDecimal tichLuongChua;
	Long ngankhoId;
	Long quyhoachDuyetId;
	BigDecimal tichLuongChuaLt;
	BigDecimal tichLuongChuaVt;
	BigDecimal theTichChuaLt;
	BigDecimal tichLuongKhaDung;
	BigDecimal tichLuongKdLt;
	BigDecimal tichLuongKdVt;
	String huongSuDung;
	BigDecimal tichLuongKdLtvt;
	Integer trangThaiTl;
	Integer namNhap;
	BigDecimal tichLuongChuaLtGao;
	BigDecimal tichLuongChuaLtThoc;
	BigDecimal theTichChuaVt;
	BigDecimal theTichTk;
}
