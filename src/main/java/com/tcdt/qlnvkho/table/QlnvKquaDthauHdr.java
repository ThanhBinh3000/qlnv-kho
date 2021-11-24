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
@Table(name = "QLNV_KQUA_DTHAU_HDR")
@Data
public class QlnvKquaDthauHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KQUA_DTHAU_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KQUA_DTHAU_HDR_SEQ", allocationSize = 1, name = "QLNV_KQUA_DTHAU_HDR_SEQ")
	private Long id;
	String soQdinhPduyet;
	Date ngayKy;
	Date ngayHluc;
	String soQdinh;
	String soQdinhGoc;
	String loaiQdinh;
	String maDvi;
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
	private List<QlnvKquaDthauDtl> detailList = new ArrayList<QlnvKquaDthauDtl>();
	
	public void setDetailList(List<QlnvKquaDthauDtl> detail) {
        if (this.detailList == null) {
            this.detailList = detail;
        } else if(this.detailList != detail) { // not the same instance, in other case we can get ConcurrentModificationException from hibernate AbstractPersistentCollection
            this.detailList.clear();
            if(detail != null){
                this.detailList.addAll(detail);
            }
        }
    }
	
	public QlnvKquaDthauHdr addDetail(QlnvKquaDthauDtl dt) {
		detailList.add(dt);
        dt.setHeader(this);
        return this;
    }
 
    public QlnvKquaDthauHdr removeDetail(QlnvKquaDthauDtl dt) {
    	detailList.remove(dt);
        dt.setHeader(null);
        return this;
    }
}
