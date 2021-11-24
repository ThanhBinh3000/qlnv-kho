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
@Table(name = "QLNV_DU_AN")
@Data
public class QlnvDuAn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DU_AN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DU_AN_SEQ", allocationSize = 1, name = "QLNV_DU_AN_SEQ")
	private Long id;
	String maDuAn;
	String tenDuAn;
	String loaiDuAn;
	String nhomDuAn;
	String hinhThuc;
	String maDuAnGoc;
	String maDvi;
	String maLhKho;
	String maKho;
	String diaDiem;
	BigDecimal tongDuToan;
	Date ngayBdau;
	Date ngayKthuc;
	String trangThai;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String ldoTuchoi;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	Date ngayPduyet;
	String nguoiPduyet;
	
}
