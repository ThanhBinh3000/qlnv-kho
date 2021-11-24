package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvDmDiemKhoReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã điểm kho không được vượt quá 20 ký tự")
	String maDiemKho;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên điểm kho không được vượt quá 250 ký tự")
	String tenDiemKho;
	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;
	@Size(max = 50, message = "Toạ độ không được vượt quá 50 ký tự")
	String toaDo;
	BigDecimal TongDtich;
}
