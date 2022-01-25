package com.tcdt.qlnvkho.request.search;

import com.tcdt.qlnvkho.request.BaseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvQdXdungKhoHdrSearchReq extends BaseRequest {
	
	@ApiModelProperty(example = "QD1234")
	String soQdinh;
	
	@ApiModelProperty(example = "00")
	String loaiKhoach;
}
