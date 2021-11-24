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
@Table(name = "QLNV_TTIN_HDONG_HDR")
@Data
public class QlnvTtinHdongHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_HDONG_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_TTIN_HDONG_HDR_SEQ", allocationSize = 1, name = "QLNV_TTIN_HDONG_HDR_SEQ")
	private Long id;
	String soHdong;
	Date ngayKy;
	Date ngayHluc;
	Date ngayHetHluc;
	String soQdinh;
	String soHieu;
	String tenHdong;
	String chuDtu;
	String dviThien;
	String mstDvi;
	String diaChi;
	String nguoiDaiDien;
	String soDthoai;
	BigDecimal giaTriHdong;
	BigDecimal vat;
	String tthaiHdong;
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
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@OneToMany(
	        mappedBy = "header",
	        		fetch = FetchType.LAZY, cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	
	private List<QlnvTtinHdongDtl1> detailList = new ArrayList<QlnvTtinHdongDtl1>();
	@OneToMany(
	        mappedBy = "header1",
	        		fetch = FetchType.LAZY, cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<QlnvTtinHdongDtl2> detailListPl = new ArrayList<QlnvTtinHdongDtl2>();
	
	public void setDetailList(List<QlnvTtinHdongDtl1> detail) {
        if (this.detailList == null) {
            this.detailList = detail;
        } else if(this.detailList != detail) { // not the same instance, in other case we can get ConcurrentModificationException from hibernate AbstractPersistentCollection
            this.detailList.clear();
            if(detail != null){
                this.detailList.addAll(detail);
            }
        }
    }
	public void setDetailListPl(List<QlnvTtinHdongDtl2> detail) {
        if (this.detailListPl == null) {
            this.detailListPl = detail;
        } else if(this.detailListPl != detail) { // not the same instance, in other case we can get ConcurrentModificationException from hibernate AbstractPersistentCollection
            this.detailListPl.clear();
            if(detail != null){
                this.detailListPl.addAll(detail);
            }
        }
    }
	
	public QlnvTtinHdongHdr addDetail(QlnvTtinHdongDtl1 dt) {
		detailList.add(dt);
        dt.setHeader(this);
        return this;
    }
 
    public QlnvTtinHdongHdr removeDetail(QlnvTtinHdongDtl1 dt) {
    	detailList.remove(dt);
        dt.setHeader(null);
        return this;
    }
	
	public QlnvTtinHdongHdr addDetailPl(QlnvTtinHdongDtl2 dt) {
		detailListPl.add(dt);
        dt.setHeader1(this);
        return this;
    }
 
    public QlnvTtinHdongHdr removeDetailPl(QlnvTtinHdongDtl2 dt) {
    	detailListPl.remove(dt);
        dt.setHeader1(null);
        return this;
    }
}
