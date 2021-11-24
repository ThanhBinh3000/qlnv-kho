package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinDthauDtlCtietReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long ttinCtietId;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;
	@Size(max = 20, message = "Điện thoại không được vượt quá 20 ký tự")
	String soDthoai;
	@NotNull(message = "Không được để trống")
	BigDecimal giaGoiThau;
	
	
}
