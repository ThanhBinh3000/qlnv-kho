package com.tcdt.qlnvkho.request.search;

import com.tcdt.qlnvkho.request.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDmLoaiKhoSearchReq extends BaseRequest{
	@ApiModelProperty(example = "KHO_2")
	String maLhKho;
	@ApiModelProperty(example = "Tên loại kho 2")
	String tenLhKho;

}
