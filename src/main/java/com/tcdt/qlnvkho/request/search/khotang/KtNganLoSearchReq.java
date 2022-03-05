package com.tcdt.qlnvkho.request.search.khotang;


import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.PaggingReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KtNganLoSearchReq extends BaseRequest {
	String maNganLo;
	String tenNganLo;
	Long nganKhoId;
	PaggingReq paggingReq;
}
