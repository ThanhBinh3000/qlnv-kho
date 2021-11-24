package com.tcdt.qlnvkho.request.search;

import javax.validation.constraints.NotNull;

import com.tcdt.qlnvkho.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDuAnSearchReq extends BaseRequest{
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "DA1234")
	String maDuAn;
	@ApiModelProperty(example = "DV123")
	String maDvi;
}
