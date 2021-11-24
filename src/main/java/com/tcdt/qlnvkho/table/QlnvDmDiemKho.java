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
@Table(name = "QLNV_DM_DIEM_KHO")
@Data
public class QlnvDmDiemKho implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DM_DIEM_KHO_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DM_DIEM_KHO_SEQ", allocationSize = 1, name = "QLNV_DM_DIEM_KHO_SEQ")
	private Long id;
	String maDiemKho;
	String tenDiemKho;
	String diaChi;
	String toaDo;
	BigDecimal TongDtich;
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
