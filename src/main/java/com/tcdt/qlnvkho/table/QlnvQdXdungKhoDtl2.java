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
@Table(name = "QLNV_QD_XDUNG_KHO_DTL")
@Data
public class QlnvQdXdungKhoDtl2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_QD_XDUNG_KHO_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_QD_XDUNG_KHO_DTL_SEQ", allocationSize = 1, name = "QLNV_QD_XDUNG_KHO_DTL_SEQ")
	private Long id;
	String maDvi;
	BigDecimal dtichMoi;
	BigDecimal dutoanMoi;
	BigDecimal dtichSua;
	BigDecimal dutoanSua;
	String noiDung;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_qd_hdr")
    private QlnvQdXdungKhoHdr2 header;
}
