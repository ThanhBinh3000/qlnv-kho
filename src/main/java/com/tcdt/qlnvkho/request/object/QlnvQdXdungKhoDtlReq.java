package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvQdXdungKhoDtlReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long idQdHdr;
	@NotNull(message = "Không được để trống")
	@Size(max = 25, message = "Mã đơn vị không được vượt quá 25 ký tự")
	String maDvi;
	@NotNull(message = "Không được để trống")
	BigDecimal dtichMoi;
	@NotNull(message = "Không được để trống")
	BigDecimal dutoanMoi;
	@NotNull(message = "Không được để trống")
	BigDecimal dtichSua;
	@NotNull(message = "Không được để trống")
	BigDecimal dutoanSua;
	String noiDung;
}
