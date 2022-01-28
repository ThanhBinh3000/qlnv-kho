package com.tcdt.qlnvkho.request.search;

import com.tcdt.qlnvkho.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QlnvDuAnKhoSearchReq extends BaseRequest {
	String maDuAn;
	String cucDuTru;
}
