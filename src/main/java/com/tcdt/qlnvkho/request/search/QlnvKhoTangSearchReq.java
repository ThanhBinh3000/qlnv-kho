package com.tcdt.qlnvkho.request.search;

import javax.validation.constraints.NotNull;

import com.tcdt.qlnvkho.request.BaseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvKhoTangSearchReq extends BaseRequest {
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "KH1234")
	String maKho;
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "01,02,03,04")
	String trangThai;
}
