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
@Table(name = "QLNV_NGAN_LO")
@Data
public class QlnvNganLo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_NGAN_LO_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_NGAN_LO_SEQ", allocationSize = 1, name = "QLNV_NGAN_LO_SEQ")
	private Long id;
	String maLo;
	String tenLo;
	BigDecimal dienTich;
	String tinhTrang;
	String ttLuuTru;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ma_ngan")
    private QlnvNgan ngan;
}
