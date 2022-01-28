package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvHdongTdthReq {

	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@NotNull(message = "Không được để trống")
	Date ngayKy;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
	String soHdong;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên hợp đồng không được vượt quá 250 ký tự")
	String tenHdong;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@NotNull(message = "Không được để trống")
	Date tuNgayKhoach;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@NotNull(message = "Không được để trống")
	Date denNgayKhoach;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@NotNull(message = "Không được để trống")
	Date tuNgayThien;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@NotNull(message = "Không được để trống")
	Date denNgayThien;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Sản phẩm không được vượt quá 250 ký tự")
	String sanPham;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Khó khăn không được vượt quá 250 ký tự")
	String khoKhan;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Giai đoạn không được vượt quá 2 ký tự")
	String giaiDoan;
	
	BigDecimal tongTtoan;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Tình trạng không được vượt quá 20 ký tự")
	String tinhTrang;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayBdau;
	
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;
}
