package com.tcdt.qlnvkho.request;

import lombok.Data;

@Data
public class SimpleSearchReq {
	
	Integer limit;
	Integer page;
	String name;
	String code;
}
