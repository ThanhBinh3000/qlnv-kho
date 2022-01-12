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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_QHOACH_KHO_DTL")
@Data
public class QlnvQhoachKhoDtl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_QHOACH_KHO_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_QHOACH_KHO_DTL_SEQ", allocationSize = 1, name = "QLNV_QHOACH_KHO_DTL_SEQ")
	private Long id;
	String maDvi;
	String diaDiem;
	String hinhThuc;
	String maLhKho;
	BigDecimal tongDtich;
	BigDecimal tongDutoan;
	String noiDung;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_qh_hdr")
	@JsonBackReference
    private QlnvQhoachKhoHdr header;
}
