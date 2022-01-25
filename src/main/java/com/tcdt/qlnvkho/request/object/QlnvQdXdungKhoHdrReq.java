package com.tcdt.qlnvkho.request.object;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvQdXdungKhoHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
	String soQdinh;
	
	@NotNull(message = "Không được để trống")
	Integer qhTuNam;
	
	@NotNull(message = "Không được để trống")
	Integer qhDenNam;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayQd;
	
	@Size(max = 1, message = "Loại quyết định không được vượt quá 1 ký tự")
	String loaiQd;
	
	@Size(max = 50, message = "Số quyết định gốc không được vượt quá 50 ký tự")
	String soQdinhGoc;
	
	@Size(max = 250, message = "Mô tả không được vượt quá 250 ký tự")
	String moTa;
	
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;
	
	@Size(max = 2, message = "Loại kế hoạch không được vượt quá 2 ký tự")
	String loaiKhoach;
	
	private List<QlnvQdXdungKhoDtlReq> detail;
}
