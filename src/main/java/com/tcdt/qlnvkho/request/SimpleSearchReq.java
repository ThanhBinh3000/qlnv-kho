package com.tcdt.qlnvkho.request;

import lombok.Data;

@Data
public class SimpleSearchReq {

	String name;
	String code;
	String maDvi;
	PaggingReq paggingReq;
}
