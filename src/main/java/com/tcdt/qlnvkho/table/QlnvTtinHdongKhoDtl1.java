package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "QLNV_TTIN_HDONG_KHO_DTL1")
@Data
public class QlnvTtinHdongKhoDtl1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_HDONG_KHO_DTL1_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_TTIN_HDONG_KHO_DTL1_SEQ", allocationSize = 1, name = "QLNV_TTIN_HDONG_KHO_DTL1_SEQ")
	private Long id;
	String hangMuc;
	BigDecimal soLuong;
	String dviTinh;
	String dkNghiemThu;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ttin_hdong_id")
    private QlnvTtinHdongKhoHdr header;
}
