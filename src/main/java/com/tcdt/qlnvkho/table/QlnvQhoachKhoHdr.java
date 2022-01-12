package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_QHOACH_KHO_HDR")
@Data
@NamedEntityGraph(name = "QLNV_QHOACH_KHO_HDR.detailList", attributeNodes = @NamedAttributeNode("detailList"), subgraphs = {
		@NamedSubgraph(name = "QLNV_QHOACH_KHO_HDR.detailList.detailList", attributeNodes = {
				@NamedAttributeNode("detailList") }) })
public class QlnvQhoachKhoHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_QHOACH_KHO_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_QHOACH_KHO_HDR_SEQ", allocationSize = 1, name = "QLNV_QHOACH_KHO_HDR_SEQ")
	private Long id;
	String soQdinh;
	Integer qhTuNam;
	Integer qhDenNam;
	@Temporal(TemporalType.DATE)
	Date ngayQd;
	String loaiQd;
	String soQdinhGoc;
	String moTa;
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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_qh_hdr")
	@JsonManagedReference
	private List<QlnvQhoachKhoDtl> detailList = new ArrayList<>();

	public void setDetailList(List<QlnvQhoachKhoDtl> detailList) {
		this.detailList.clear();
		for (QlnvQhoachKhoDtl detail : detailList) {
			detail.setHeader(this);
		}
		this.detailList.addAll(detailList);
	}

	public void addChild(QlnvQhoachKhoDtl detail) {
		detail.setHeader(this);
		this.detailList.add(detail);
	}
}
