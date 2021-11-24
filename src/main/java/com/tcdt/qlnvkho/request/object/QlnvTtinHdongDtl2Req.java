package com.tcdt.qlnvkho.request.object;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinHdongDtl2Req{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long ttinHdongId;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Loại đính chính không được vượt quá 2 ký tự")
	String loaiDchinh;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String noiDung;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số phụ lục không được vượt quá 50 ký tự")
	String soPluc;
	@NotNull(message = "Không được để trống")
	Date ngayKy;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Căn cứ không được vượt quá 250 ký tự")
	String canCu;
}
