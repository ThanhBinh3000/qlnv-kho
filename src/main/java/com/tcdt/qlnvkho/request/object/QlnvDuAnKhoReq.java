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
public class QlnvDuAnKhoReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã dự án không được vượt quá 50 ký tự")
	String maDuAn;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên dự án không được vượt quá 50 ký tự")
	String tenDuAn;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Nhóm dự án không được vượt quá 02 ký tự")
	@ApiModelProperty(example = Contains.DA_KHO_NHOM_A)
	String nhomDuAn;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Hình thức không được vượt quá 02 ký tự")
	@ApiModelProperty(example = Contains.DA_KHO_HT_TAO_MOI)
	String hinhThuc;
	
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;
	
	@Size(max = 50, message = "Mã dự án gốc không được vượt quá 50 ký tự")
	String maDuAnGoc;
	
	@Size(max = 50, message = "Mã LH kho không được vượt quá 50 ký tự")
	String maLhKho;
	
	@Size(max = 50, message = "Mã kho không được vượt quá 50 ký tự")
	String maKho;
	
	@Size(max = 02, message = "Mã kho không được vượt quá 02 ký tự")
	@ApiModelProperty(example = Contains.DA_KHO_CAI_TAO)
	String loaiDuAn;
	
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	
	@Size(max = 250, message = "Địa điểm không được vượt quá 250 ký tự")
	String diaDiem;
	
	BigDecimal tongDuToan;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayBdau;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayKthuc;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Cục quản lý không được vượt quá 50 ký tự")
	String cucQuanLy;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 02, message = "Cục quản lý không được vượt quá 02 ký tự")
	@ApiModelProperty(example = Contains.DA_KHO_NKP_NSNN)
	String nguonKinhPhi;
}
