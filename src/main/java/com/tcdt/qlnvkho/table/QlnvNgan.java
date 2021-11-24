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
@Table(name = "QLNV_NGAN")
@Data
public class QlnvNgan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_NGAN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_NGAN_SEQ", allocationSize = 1, name = "QLNV_NGAN_SEQ")
	private Long id;
	String maKho;
	String maNgan;
	String tenNgan;
	BigDecimal dienTich;
	String tinhTrang;
	String ttLuuTru;
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
	        mappedBy = "ngan",
	        		fetch = FetchType.LAZY, cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<QlnvNganLo> loList = new ArrayList<QlnvNganLo>();
	
	public void setDetailList(List<QlnvNganLo> lo) {
        if (this.loList == null) {
            this.loList = lo;
        } else if(this.loList != lo) { // not the same instance, in other case we can get ConcurrentModificationException from hibernate AbstractPersistentCollection
            this.loList.clear();
            if(lo != null){
                this.loList.addAll(lo);
            }
        }
    }
	
	public QlnvNgan addLo(QlnvNganLo dt) {
		loList.add(dt);
        dt.setNgan(this);
        return this;
    }
 
    public QlnvNgan removeLo(QlnvNganLo dt) {
    	loList.remove(dt);
        dt.setNgan(null);
        return this;
    }
}
