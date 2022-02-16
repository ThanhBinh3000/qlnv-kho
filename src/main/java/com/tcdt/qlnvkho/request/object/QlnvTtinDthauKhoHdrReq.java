package com.tcdt.qlnvkho.request.object;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinDthauKhoHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
	String soQdinh;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayKy;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 5, message = "Loại quyết định không được vượt quá 5 ký tự")
	String loaiQdinh;
	
	@Size(max = 50, message = "Số quyết định gốc không được vượt quá 50 ký tự")
	String soQdinhGoc;
	
	@Size(max = 250, message = "Mô tả không được vượt quá 250 ký tự")
	String moTa;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã dự án không được vượt quá 50 ký tự")
	String maDuAn;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên gói thầu không được vượt quá 250 ký tự")
	String tenGoiThau;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên báo không được vượt quá 50 ký tự")
	String tenBao;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayDangBao;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên trang mạng không được vượt quá 50 ký tự")
	String tenPage;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayDangPage;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayPhanhTu;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayPhanhDen;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayMoTu;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayMoDen;
	
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;
	
	private List<QlnvTtinDthauKhoDtlReq> detail;
}
