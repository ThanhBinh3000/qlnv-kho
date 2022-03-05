package com.tcdt.qlnvkho.request.search.khotang;


import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.PaggingReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KtNhaKhoSearchReq extends BaseRequest {
	String maNhaKho;
	String tenNhaKho;
	Long diemKhoId;
	PaggingReq paggingReq;
}
