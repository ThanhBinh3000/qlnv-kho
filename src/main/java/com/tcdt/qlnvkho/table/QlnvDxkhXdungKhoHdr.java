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
@Table(name = "QLNV_DXKH_XDUNG_KHO_HDR")
@Data
public class QlnvDxkhXdungKhoHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DXKH_XAY_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DXKH_XAY_SEQ", allocationSize = 1, name = "QLNV_DXKH_XAY_SEQ")
	private Long id;
	String soDeNghi;
	Integer khTuNam;
	Integer khDenNam;
	Date ngayDeNghi;
	String noiDung;
	BigDecimal tongDuToan;
	String maDvi;
	String tthaiCuc;
	String tthaiTong;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String ldoTuchoi;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	Date ngayPduyet;
	String nguoiPduyet;
	Date tcNgaySua;
	String tcNguoiSua;
	String tcLdoTuchoi;
	Date tcNgayGuiDuyet;
	String tcNguoiGuiDuyet;
	Date tcNgayPduyet;
	String tcNguoiPduyet;
	@OneToMany(
	        mappedBy = "header",
	        		fetch = FetchType.LAZY, cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<QlnvDxkhXdungKhoDtl> detailList = new ArrayList<QlnvDxkhXdungKhoDtl>();
	
	public void setDetailList(List<QlnvDxkhXdungKhoDtl> detail) {
        if (this.detailList == null) {
            this.detailList = detail;
        } else if(this.detailList != detail) { // not the same instance, in other case we can get ConcurrentModificationException from hibernate AbstractPersistentCollection
            this.detailList.clear();
            if(detail != null){
                this.detailList.addAll(detail);
            }
        }
    }
	
	public QlnvDxkhXdungKhoHdr addDetail(QlnvDxkhXdungKhoDtl dt) {
		detailList.add(dt);
        dt.setHeader(this);
        return this;
    }
 
    public QlnvDxkhXdungKhoHdr removeDetail(QlnvDxkhXdungKhoDtl dt) {
    	detailList.remove(dt);
        dt.setHeader(null);
        return this;
    }
}
