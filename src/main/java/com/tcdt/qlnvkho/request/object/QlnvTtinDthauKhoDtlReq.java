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
public class QlnvTtinDthauKhoDtlReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	Long idHdr;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Số hiệu không được vượt quá 20 ký tự")
	String soHieu;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên phần thầu không được vượt quá 50 ký tự")
	String tenPhanThau;
	
	@Size(max = 250, message = "Hạng mục không được vượt quá 250 ký tự")
	String hangMuc;
	
	BigDecimal soLuong;
	
	BigDecimal donGia;
	
	BigDecimal giaPhanThau;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Hình thức ĐT không được vượt quá 2 ký tự")
	String hthucDthau;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Phương thức ĐT không được vượt quá 2 ký tự")
	String pthucDthau;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Hình thức HĐ không được vượt quá 2 ký tự")
	String hthucHdong;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date tgianThien;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayLcntTu;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayLcntDen;
}
