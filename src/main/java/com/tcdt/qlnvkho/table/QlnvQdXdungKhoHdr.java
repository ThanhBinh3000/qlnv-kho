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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Entity
@Table(name = "QLNV_QD_XDUNG_KHO_HDR")
@Data
@NamedEntityGraph(name = "QLNV_QD_XDUNG_KHO_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvQdXdungKhoHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_QD_XDUNG_KHO_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_QD_XDUNG_KHO_HDR_SEQ", allocationSize = 1, name = "QLNV_QD_XDUNG_KHO_HDR_SEQ")
	private Long id;
	String soQdinh;
	Integer qhTuNam;
	Integer qhDenNam;
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
	String loaiKhoach;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_qd_hdr")
	private List<QlnvQdXdungKhoDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvQdXdungKhoDtl> children) {
		this.children.clear();
		for (QlnvQdXdungKhoDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvQdXdungKhoDtl child) {
		child.setParent(this);
		this.children.add(child);
	}
}
