package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKhoTangReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã điểm kho không được vượt quá 20 ký tự")
	String maDiemKho;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã kho không được vượt quá 20 ký tự")
	String maKho;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên kho không được vượt quá 250 ký tự")
	String tenKho;
	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;
	@Size(max = 50, message = "Toạ độ không được vượt quá 50 ký tự")
	String toaDo;
	BigDecimal TongDtich;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Mã loại hình kho không được vượt quá 20 ký tự")
	String maLhKho;
	@NotNull(message = "Không được để trống")
	@Size(max = 25, message = "Mã đơn vị không được vượt quá 25 ký tự")
	String maDvi;
	@Size(max = 2, message = "Tình trạng không được vượt quá 2 ký tự")
	String tinhTrang;
	@Size(max = 20, message = "Mã kho không được vượt quá 20 ký tự")
	String maKhoCu;
	@Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
	String soQdinh;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayQdinh;

}
