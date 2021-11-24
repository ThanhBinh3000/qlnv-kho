package com.tcdt.qlnvkho.request.search;

import javax.validation.constraints.NotNull;
import com.tcdt.qlnvkho.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvTtinDthauHdrSearchReq extends BaseRequest {
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "QD1234")
	String soQdinh;
	@ApiModelProperty(example = "DA123")
	String maDuAn;

}
