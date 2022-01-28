package com.tcdt.qlnvkho.request.object;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinHdongKhoDtl2Req{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayKy;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Căn cứ không được vượt quá 250 ký tự")
	String canCu;
}
