package com.tcdt.qlnvkho.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_TTIN_HDONG_KHO_HDR")
@Data
public class QlnvTtinHdongKhoHdr implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_HDONG_KHO_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_TTIN_HDONG_KHO_HDR_SEQ", allocationSize = 1, name = "QLNV_TTIN_HDONG_KHO_HDR_SEQ")
	private Long id;
	String soQdinh;
	Date ngayKy;
	Date ngayHluc;
	Date ngayHetHluc;
	String soHdong;
	String soHieu;
	String tenHdong;
	String dviThien;
	String mstDvi;
	String diaChi;
	String nguoiDaiDien;
	String soDthoai;
	BigDecimal giaTriHdong;
	BigDecimal vat;
	String tthaiHdong;
	String trangThai;
	String maDvi;
	String ldoTuchoi;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	Date ngayPduyet;
	String nguoiPduyet;

	// @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@OneToMany(mappedBy = "header", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QlnvTtinHdongKhoDtl1> detailList = new ArrayList<QlnvTtinHdongKhoDtl1>();
	
	@OneToMany(mappedBy = "header1", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QlnvTtinHdongKhoDtl2> detailListPl = new ArrayList<QlnvTtinHdongKhoDtl2>();

	public void setDetailList(List<QlnvTtinHdongKhoDtl1> details) {
		if (this.detailList == null) {
			this.detailList = details;
		} else if (this.detailList != details) { // not the same instance, in other case we can get
													// ConcurrentModificationException from hibernate
													// AbstractPersistentCollection
			this.detailList.clear();
			for (QlnvTtinHdongKhoDtl1 detail : details) {
				detail.setHeader(this);
			}
			this.detailList.addAll(details);

		}
	}

	public void setDetailListPl(List<QlnvTtinHdongKhoDtl2> details) {
		if (this.detailListPl == null) {
			this.detailListPl = details;
		} else if (this.detailListPl != details) { // not the same instance, in other case we can get
													// ConcurrentModificationException from hibernate
													// AbstractPersistentCollection
			this.detailListPl.clear();
			for (QlnvTtinHdongKhoDtl2 detail : details) {
				detail.setHeader1(this);
			}
			this.detailListPl.addAll(details);
		}
	}

	public QlnvTtinHdongKhoHdr addDetail(QlnvTtinHdongKhoDtl1 dt) {
		detailList.add(dt);
		dt.setHeader(this);
		return this;
	}

	public QlnvTtinHdongKhoHdr removeDetail(QlnvTtinHdongKhoDtl1 dt) {
		detailList.remove(dt);
		dt.setHeader(null);
		return this;
	}

	public QlnvTtinHdongKhoHdr addDetailPl(QlnvTtinHdongKhoDtl2 dt) {
		detailListPl.add(dt);
		dt.setHeader1(this);
		return this;
	}

	public QlnvTtinHdongKhoHdr removeDetailPl(QlnvTtinHdongKhoDtl2 dt) {
		detailListPl.remove(dt);
		dt.setHeader1(null);
		return this;
	}
}
