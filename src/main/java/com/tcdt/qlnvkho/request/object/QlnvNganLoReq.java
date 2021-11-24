package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvNganLoReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã ngăn không được vượt quá 20 ký tự")
	String maNgan;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã lô không được vượt quá 20 ký tự")
	String maLo;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên lô không được vượt quá 250 ký tự")
	String tenLo;
	@NotNull(message = "Không được để trống")
	BigDecimal dienTich;
	@Size(max = 2, message = "Tình trạnglô không được vượt quá 2 ký tự")
	String tinhTrang;
	@Size(max = 2, message = "TT lư trữ không được vượt quá 2 ký tự")
	String ttLuuTru;
}
