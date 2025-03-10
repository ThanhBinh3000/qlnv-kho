package com.tcdt.qlnvkho.util;

import java.util.Map;

public class Contains {
	public static final String FORMAT_DATE_STR = "dd/MM/yyyy";
	public static final String FORMAT_DATE_TIME_STR = "dd/MM/yyyy HH:mm:ss";

	// Trang thai response
	public static final int RESP_SUCC = 0;
	public static final int RESP_FAIL = 1;

	public static final String TYPE_USER_BACKEND = "BE";
	public static final String TYPE_USER_FRONTEND = "FE";

	// Trang thai bao cao
	public static final String CHO_PHE_DUYET = "00";
	public static final String CHO_KHOA_SO = "01";
	public static final String TU_CHOI_PD = "02";
	public static final String DA_KHOA = "03";
	public static final String TU_CHOI_KHOA = "04";
	public static final String MO_SO = "05";
	public static final String HUY_PDUYET = "06";

	// Trang thai phe duyet kho tang
	public static final String TAO_MOI = "00";
	public static final String CHO_DUYET = "01";
	public static final String DUYET = "02";
	public static final String TU_CHOI = "03";
	public static final String HUY = "04";

	// Trang thai
	public static final String MOI_TAO = "00";
	public static final String HOAT_DONG = "01";
	public static final String NGUNG_HOAT_DONG = "02";
	public static final String TAM_KHOA = "03";

	// Cap trang don vi
	public static final String CAP_TONG_CUC = "1";
	public static final String CAP_CUC = "2";
	public static final String CAP_CHI_CUC = "3";

	// Loai dieu chinh quyet dinh
	public static final String CHUA_DC = "0";
	public static final String DC_GIA = "1";
	public static final String DC_SO_LUONG = "2";
	public static final String DC_THOI_GIAN = "3";
	public static final String DC_KHAC = "4";

	public static final String QUYET_DINH = "QD";
	public static final String QUYET_DINH_DC = "DC";

	// Loai quyet dinh
	public static final String QD_MOI = "00";
	public static final String QD_DIEU_CHINH = "01";

	// Loai Du An Kho
	public static final String DA_KHO_XAY_MOI = "00";
	public static final String DA_KHO_CAI_TAO = "01";

	// Nhom Du An Kho
	public static final String DA_KHO_NHOM_A = "A";
	public static final String DA_KHO_NHOM_B = "B";
	public static final String DA_KHO_NHOM_C = "C";

	// Hinh thuc Du An Kho
	public static final String DA_KHO_HT_TAO_MOI = "00";
	public static final String DA_KHO_HT_DIEU_CHINH = "01";

	// Nguon kinh phi Du An Kho
	public static final String DA_KHO_NKP_NSNN = "00";
	public static final String DA_KHO_NKP_ODA = "01";
	public static final String DA_KHO_NKP_NTN = "02";

	// Hinh thuc dau thau kho
	public static final String DT_KHO_HT_DT_RONG_RAI = "00";
	public static final String DT_KHO_HT_DT_HAN_CHE = "01";
	public static final String DT_KHO_HT_DT_CHI_DINH_THAU = "02";
	public static final String DT_KHO_HT_DT_CHAO_HANG_CANH_TRANH = "03";
	public static final String DT_KHO_HT_DT_MUA_SAM_TRUC_TIEP = "04";
	public static final String DT_KHO_HT_DT_TU_THUC_HIEN = "05";
	public static final String DT_KHO_HT_DT_TU_LUA_CHON_TRONG_THDB = "06";
	public static final String DT_KHO_HT_DT_TU_THAM_GIA_THUC_HIEN_CUA_CD = "07";

	// Phuong thuc dau thau kho
	public static final String DT_KHO_PT_DT_1_GIAI_DOAN_1_GHS = "01";
	public static final String DT_KHO_PT_DT_1_GIAI_DOAN_2_GHS = "02";
	public static final String DT_KHO_PT_DT_2_GIAI_DOAN_1_GHS = "03";
	public static final String DT_KHO_PT_DT_2_GIAI_DOAN_2_GHS = "04";

	// Hinh Thuc hop dong dau thau kho
	public static final String DT_KHO_HT_HD_TRON_GOI = "01";
	public static final String DT_KHO_HT_HD_THEO_DON_GIA = "02";
	public static final String DT_KHO_HT_HD_THEO_DON_GIA_DIEU = "03";

	// Trang thai hoan thanh tien do thuc hien hop dong sua chua kho tang
	public static final String TDTH_HD_HOAN_THANH = "00";
	public static final String TDTH_HD_CHUA_HOAN_THANH = "01";
	public static final String TDTH_HD_TAM_DUNG = "02";

	// Loai de xuat
	public static final String DXKH_TRUNG_HAN = "00";
	public static final String DXKH_HANG_NAM = "01";
	public static final String DXKH_SC_HNAM = "02";
	public static final String DXKH_SC_TXUYEN = "03";
	public static final String TH_DX_CCDC_PVC = "04";
	public static final String TH_DX_MMOC = "05";
	public static final String TH_NHC_BHIEM = "06";

	// Loai ke hoach
	public static final String KH_HANG_NAM = "00";
	public static final String KH_THUONG_XUYEN = "01";
	public static final String KH_TRUNG_HAN = "02";

	public static final Map<String, String> mpLoaiDX;
	static {
		mpLoaiDX = Maps.<String, String>buildMap()
				.put(Contains.DXKH_TRUNG_HAN, "Đề xuất kế hoạch xây dựng kho tàng trung hạn")
				.put(Contains.DXKH_HANG_NAM, "Đề xuất kế hoạch xây dựng kho tàng hàng năm")
				.put(Contains.DXKH_SC_HNAM, "Đề xuất kế hoạch sửa chữa lớn kho tàng hàng năm")
				.put(Contains.DXKH_SC_TXUYEN, "Đề xuất kế hoạch sửa chữa kho tàng thường xuyên").get();
	}

	public static String getLoaiDx(String key) {
		return Contains.mpLoaiDX.get(key);
	}

	public static final Map<String, String> mpLoaiDxTh;
	static {
		mpLoaiDxTh = Maps.<String, String>buildMap().put(Contains.TH_DX_CCDC_PVC, "Tổng hợp đề xuất CCDC và màng PVC")
				.put(Contains.TH_DX_MMOC, "Tổng hợp đề xuất máy móc thiết bị")
				.put(Contains.TH_NHC_BHIEM, "Tổng hợp đề xuất nhu cầu tham gia bảo hiểm").get();
	}

	public static String getLoaiDxTh(String key) {
		return Contains.mpLoaiDxTh.get(key);
	}
}
