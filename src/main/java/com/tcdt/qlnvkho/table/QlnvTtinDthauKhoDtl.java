package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "QLNV_TTIN_DTHAU_KHO_DTL")
@Data
public class QlnvTtinDthauKhoDtl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_DTHAU_KHO_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_TTIN_DTHAU_KHO_DTL_SEQ", allocationSize = 1, name = "QLNV_TTIN_DTHAU_KHO_DTL_SEQ")
	private Long id;
	
	String soHieu;
	String tenPhanThau;
	String hangMuc;
	BigDecimal soLuong;
	BigDecimal donGia;
	BigDecimal giaPhanThau;
	String hthucDthau;
	String pthucDthau;
	String hthucHdong;
	Date tgianThien;
	Date ngayLcntTu;
	Date ngayLcntDen;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_hdr")
    private QlnvTtinDthauKhoHdr header;
}
