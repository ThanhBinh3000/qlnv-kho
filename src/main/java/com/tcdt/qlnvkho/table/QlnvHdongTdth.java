package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_HDONG_TDTH")
@Data
public class QlnvHdongTdth implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HDONG_TDTH_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_HDONG_TDTH_SEQ", allocationSize = 1, name = "QLNV_HDONG_TDTH_SEQ")
	private Long id;
	
	String soHdong;
	String tenHdong;
	Date tuNgayKhoach;
	Date denNgayKhoach;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	String ldoTuchoi;
	Date ngayPduyet;
	String nguoiPduyet;
	Date tuNgayThien;
	Date denNgayThien;
	String sanPham;
	String khoKhan;
	String trangThai;
	String giaiDoan;
	String maDvi;
	BigDecimal tongTtoan;
	Date ngayBdau;
	String tinhTrang;
	
}
