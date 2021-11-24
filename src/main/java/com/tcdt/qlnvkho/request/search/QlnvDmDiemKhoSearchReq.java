package com.tcdt.qlnvkho.request.search;

import javax.validation.constraints.NotNull;

import com.tcdt.qlnvkho.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDmDiemKhoSearchReq extends BaseRequest {
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "DK1234")
	String maDiemKho;
}
