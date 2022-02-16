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
@Table(name = "QLNV_DU_AN_KHO")
@Data
public class QlnvDuAnKho implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DU_AN_KHO_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DU_AN_KHO_SEQ", allocationSize = 1, name = "QLNV_DU_AN_KHO_SEQ")
	private Long id;
	
	String maDuAn;
	String tenDuAn;
	String nhomDuAn;
	String hinhThuc;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	String ldoTuchoi;
	Date ngayPduyet;
	String nguoiPduyet;
	String maDuAnGoc;
	String maLhKho;
	String maKho;
	String trangThai;
	String loaiDuAn;
	String maDvi;
	String diaDiem;
	BigDecimal tongDuToan;
	Date ngayBdau;
	Date ngayKthuc;
	String cucQuanLy;
	String nguonKinhPhi;
}
