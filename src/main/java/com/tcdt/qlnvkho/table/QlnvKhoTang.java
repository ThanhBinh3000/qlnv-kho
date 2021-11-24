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

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHO_TANG")
@Data
public class QlnvKhoTang implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHO_TANG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHO_TANG_SEQ", allocationSize = 1, name = "QLNV_KHO_TANG_SEQ")
	private Long id;
	String maDiemKho;
	String maKho;
	String tenKho;
	String diaChi;
	String toaDo;
	BigDecimal TongDtich;
	String maLhKho;
	String maDvi;
	String tinhTrang;
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
	String maKhoCu;
	String soQdinh;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	Date ngayQdinh;
}
