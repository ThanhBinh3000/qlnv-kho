package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.ParamDef;

import lombok.Data;

@Entity
@Table(name = "QLNV_QD_XDUNG_KHO_HDR")
@Data
@FilterDef(name = "pFilter", parameters = @ParamDef(name = "maDvi", type = "string"))
public class QlnvQdXdungKhoHdr2 implements Serializable {
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
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "QLNV_QD_XDUNG_KHO_DTL", joinColumns = @JoinColumn(name = "id_qd_hdr"), inverseJoinColumns = @JoinColumn(name = "id"))
	@FilterJoinTable(name = "pFilter", condition = ":maDvi = ma_dvi")
	private List<QlnvQdXdungKhoDtl2> detailList = new ArrayList<QlnvQdXdungKhoDtl2>();

	public void setDetailList(List<QlnvQdXdungKhoDtl2> detail) {
		if (this.detailList == null) {
			this.detailList = detail;
		} else if (this.detailList != detail) {
			this.detailList.clear();
			if (detail != null) {
				this.detailList.addAll(detail);
			}
		}
	}

	public QlnvQdXdungKhoHdr2 addDetail(QlnvQdXdungKhoDtl2 dt) {
		detailList.add(dt);
		dt.setHeader(this);
		return this;
	}

	public QlnvQdXdungKhoHdr2 removeDetail(QlnvQdXdungKhoDtl2 dt) {
		detailList.remove(dt);
		dt.setHeader(null);
		return this;
	}
}
