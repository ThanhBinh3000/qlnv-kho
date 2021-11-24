package com.tcdt.qlnvkho.request;

import lombok.Data;

@Data
public class BaseRequest {
	PaggingReq paggingReq;
	String trangThai;
	String str;
}