package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinHdongDtl1Req{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long ttinHdongId;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Hạng mục không được vượt quá 50 ký tự")
	String hangMuc;
	@NotNull(message = "Không được để trống")
	BigDecimal soLuong;
	@Size(max = 50, message = "Tên phần thầu không được vượt quá 50 ký tự")
	String dviTinh;
	@Size(max = 250, message = "Điều kiện nghiệm thu không được vượt quá 250 ký tự")
	String dkNghiemThu;
	
}
