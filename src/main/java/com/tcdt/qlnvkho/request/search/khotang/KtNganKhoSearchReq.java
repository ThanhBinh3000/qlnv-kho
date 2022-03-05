package com.tcdt.qlnvkho.request.search.khotang;


import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.PaggingReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KtNganKhoSearchReq extends BaseRequest {
	String maNganKho;
	String tenNganKho;
	Long nhaKhoId;
	PaggingReq paggingReq;
}
