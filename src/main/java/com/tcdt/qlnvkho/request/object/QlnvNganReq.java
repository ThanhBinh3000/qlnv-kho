package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvNganReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã kho không được vượt quá 20 ký tự")
	String maKho;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã ngăn không được vượt quá 20 ký tự")
	String maNgan;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên ngăn không được vượt quá 250 ký tự")
	String tenNgan;
	@NotNull(message = "Không được để trống")
	BigDecimal dienTich;
	String tinhTrang;
	String ttLuuTru;
	private List<QlnvNganLoReq> detail;
}
