package com.tcdt.qlnvkho.request.search;

import com.tcdt.qlnvkho.request.BaseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDmThukhoSearchReq extends BaseRequest {
	@ApiModelProperty(example = "TK_1")
	String maThukho;
	@ApiModelProperty(example = "Tên thủ kho 1")
	String tenThukho;

}
