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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = QlnvDxkhXdungKho.TABLE_NAME)
@Data
@NamedEntityGraph(name = "QLNV_DXKH_XDUNG_KHO.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvDxkhXdungKho implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "QLNV_DXKH_XDUNG_KHO";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DXKH_XDUNG_KHO_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DXKH_XDUNG_KHO_SEQ", allocationSize = 1, name = "QLNV_DXKH_XDUNG_KHO_SEQ")
	private Long id;

	String maDvi;
	String loaiDxuat;
	@Temporal(TemporalType.DATE)
	Date ngayDxuat;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String trangThai;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "dataId")
	@JsonManagedReference
	@Where(clause = "data_type='" + QlnvDxkhXdungKho.TABLE_NAME + "'")
	private List<FileDKemJoinDxkhXdungKho> children = new ArrayList<>();

	public void setChildren(List<FileDKemJoinDxkhXdungKho> children) {
		this.children.clear();
		for (FileDKemJoinDxkhXdungKho child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(FileDKemJoinDxkhXdungKho child) {
		child.setParent(this);
		this.children.add(child);
	}
}
