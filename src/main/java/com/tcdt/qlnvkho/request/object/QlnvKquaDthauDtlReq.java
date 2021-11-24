package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKquaDthauDtlReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long kquaDthauId;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Số hiệu không được vượt quá 20 ký tự")
	String soHieu;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên phần thầu không được vượt quá 50 ký tự")
	String tenPhanThau;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Hạng mục không được vượt quá 250 ký tự")
	String tenDvi;
	BigDecimal tongTien;
	BigDecimal vat;
	
}
