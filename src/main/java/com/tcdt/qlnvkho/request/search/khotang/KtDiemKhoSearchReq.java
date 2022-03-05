package com.tcdt.qlnvkho.request.search.khotang;


import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.PaggingReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KtDiemKhoSearchReq extends BaseRequest {
	String maDiemKho;
	String tenDiemKho;
	Long tongKhoId;
	PaggingReq paggingReq;
}
