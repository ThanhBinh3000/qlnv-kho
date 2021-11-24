package com.tcdt.qlnvkho.request.search;

import com.tcdt.qlnvkho.request.BaseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDxkhXdungKhoHdrSearchReq extends BaseRequest {
	Integer limit;
	Integer page;
	@ApiModelProperty(example = "DV1")
	String maDvi;
	@ApiModelProperty(example = "QD1234")
	String soDeNghi;
	@ApiModelProperty(example = "2020")
	Integer khTuNam;
	@ApiModelProperty(example = "2022")
	Integer khDenNam;
}
