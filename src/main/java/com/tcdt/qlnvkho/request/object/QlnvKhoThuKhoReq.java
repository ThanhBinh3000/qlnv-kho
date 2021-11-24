package com.tcdt.qlnvkho.request.object;

import java.util.Date;

import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKhoThuKhoReq {
	@ApiModelProperty(notes = "Mã kho")
	String maKho;
	String maThukho;
	String tenThukho;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Past
	Date tuNgayGiao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Past
	Date denNgayGiao;
}
