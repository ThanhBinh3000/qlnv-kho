package com.tcdt.qlnvkho.request.search;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDxkhXdungKhoSearchReq extends BaseRequest {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date tuNgayDxuat;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date denNgayDxuat;

	@ApiModelProperty(example = "HNO")
	String maDvi;

	@ApiModelProperty(example = "00")
	String loaiDxuat;

	@ApiModelProperty(example = Contains.MOI_TAO)
	String trangThai;

}
