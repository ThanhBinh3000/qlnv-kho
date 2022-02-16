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
public class QlnvDxkhXdungKhoReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Loại đề xuất không được vượt quá 2 ký tự")
	String loaiDxuat;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayDxuat;

	List<FileDinhKemReq> fileDinhKems;
}
