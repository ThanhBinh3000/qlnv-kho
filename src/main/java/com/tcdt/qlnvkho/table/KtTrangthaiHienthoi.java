package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "KT_TRANGTHAI_HIENTHOI")
@Data
public class KtTrangthaiHienthoi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_TRANGTHAI_HIENTHOI_SEQ")
	@SequenceGenerator(sequenceName = "KT_TRANGTHAI_HIENTHOI_SEQ", allocationSize = 1, name = "KT_TRANGTHAI_HIENTHOI_SEQ")
	private Long id;

	String maDonVi;
	String maVthh;
	BigDecimal slHienThoi;
	String nam;
	String tenDonVi;
	String tenVthh;
	String donViTinhId;
	String tenDonViTinh;

}
