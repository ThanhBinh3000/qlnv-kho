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
@Table(name = "QLNV_TTIN_DTHAU_DTL_CTIET")
@Data
public class QlnvTtinDthauDtlCtiet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_DTHAU_DTL_CTIET_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_TTIN_DTHAU_DTL_CTIET_SEQ", allocationSize = 1, name = "QLNV_TTIN_DTHAU_DTL_CTIET_SEQ")
	private Long id;
	String maDvi;
	String diaChi;
	String soDthoai;
	BigDecimal giaGoiThau;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ttin_ctiet_id")
    private QlnvTtinDthauDtl dtl;
}
