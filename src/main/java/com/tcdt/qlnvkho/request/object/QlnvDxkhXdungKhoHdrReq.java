package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvDxkhXdungKhoHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Số quyết định không được vượt quá 20 ký tự")
	String soDeNghi;
	@NotNull(message = "Không được để trống")
	Integer khTuNam;
	@NotNull(message = "Không được để trống")
	Integer khDenNam;
	Date ngayDeNghi;
	@Size(max = 250, message = "Mô tả không được vượt quá 250 ký tự")
	String noiDung;
	BigDecimal tongDuToan;
	@NotNull(message = "Không được để trống")
	@Size(max = 25, message = "Mã đơn vị không được vượt quá 25 ký tự")
	String maDvi;
	String tthaiCuc;
	String tthaiTong;
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String tcLdoTuchoi;
	private List<QlnvDxkhXdungKhoDtlReq> detail;
}
