package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvDuAnReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã dự án không được vượt quá 50 ký tự")
	String maDuAn;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên dự án không được vượt quá 250 ký tự")
	String tenDuAn;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Loại không được vượt quá 2 ký tự")
	String loaiDuAn;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Nhóm dự án không được vượt quá 2 ký tự")
	String nhomDuAn;
	@Size(max = 2, message = "Hình thức không được vượt quá 2 ký tự")
	String hinhThuc;
	@Size(max = 50, message = "Mã dự án gốc không được vượt quá 50 ký tự")
	String maDuAnGoc;
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	@Size(max = 50, message = "Mã loại hình kho không được vượt quá 50 ký tự")
	String maLhKho;
	@Size(max = 50, message = "Mã kho không được vượt quá 50 ký tự")
	String maKho;
	@Size(max = 250, message = "Địa điểm không được vượt quá 250 ký tự")
	String diaDiem;
	BigDecimal tongDuToan;
	Date ngayBdau;
	Date ngayKthuc;
	@Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
	String trangThai;
	
	
}
