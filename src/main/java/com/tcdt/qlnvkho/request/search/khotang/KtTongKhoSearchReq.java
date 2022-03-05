package com.tcdt.qlnvkho.request.search.khotang;


import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.PaggingReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KtTongKhoSearchReq extends BaseRequest {
	String maTongKho;
	String tenTongKho;
	Long dtqgkvId;
	PaggingReq paggingReq;
}
