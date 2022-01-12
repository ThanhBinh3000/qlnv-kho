package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import lombok.Data;

@Entity
@Table(name = "QLNV_KHO_THUKHO")
@Data
public class QlnvKhoThuKho implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KHO_TANG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KHO_TANG_SEQ", allocationSize = 1, name = "QLNV_KHO_TANG_SEQ")
	private Long id;
	String maThukho;
	String tenThukho;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date tuNgayGiao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date denNgayGiao;
	String trangThai;
	String maKho;
	Date ngayTao;
	String nguoiTao;

}
