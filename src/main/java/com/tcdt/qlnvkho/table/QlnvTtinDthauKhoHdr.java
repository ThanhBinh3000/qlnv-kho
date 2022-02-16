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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_TTIN_DTHAU_KHO_HDR")
@Data
public class QlnvTtinDthauKhoHdr implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_DTHAU_KHO_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_TTIN_DTHAU_KHO_HDR_SEQ", allocationSize = 1, name = "QLNV_TTIN_DTHAU_KHO_HDR_SEQ")
	private Long id;
	
	String soQdinh;
	Date ngayKy;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	String ldoTuchoi;
	Date ngayPduyet;
	String nguoiPduyet;
	String soQdinhGoc;
	String maDuAn;
	String tenGoiThau;
	String trangThai;
	String loaiQdinh;
	String maDvi;
	String moTa;
	String tenBao;
	Date ngayDangBao;
	String tenPage;
	Date ngayDangPage;
	Date ngayPhanhTu;
	Date ngayPhanhDen;
	Date ngayMoTu;
	Date ngayMoDen;
	
	@OneToMany(
	        mappedBy = "header",
	        		fetch = FetchType.LAZY, cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<QlnvTtinDthauKhoDtl> detailList = new ArrayList<QlnvTtinDthauKhoDtl>();
	
	public void setDetailList(List<QlnvTtinDthauKhoDtl> details) {
        if (this.detailList == null) {
            this.detailList = details;
        } else if(this.detailList != details) { // not the same instance, in other case we can get ConcurrentModificationException from hibernate AbstractPersistentCollection
            this.detailList.clear();
            for (QlnvTtinDthauKhoDtl detail : details) {
             	detail.setHeader(this);
     		}
     		this.detailList.addAll(details);
        }
    }
	
	public QlnvTtinDthauKhoHdr addDetail(QlnvTtinDthauKhoDtl dt) {
		detailList.add(dt);
        dt.setHeader(this);
        return this;
    }
 
    public QlnvTtinDthauKhoHdr removeDetail(QlnvTtinDthauKhoDtl dt) {
    	detailList.remove(dt);
        dt.setHeader(null);
        return this;
    }
}
