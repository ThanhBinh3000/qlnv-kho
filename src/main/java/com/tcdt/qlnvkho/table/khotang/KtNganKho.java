package com.tcdt.qlnvkho.table.khotang;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "KT_NGAN_KHO")
@Data
public class KtNganKho {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_NGAN_KHO_SEQ")
  @SequenceGenerator(sequenceName = "KT_NGAN_KHO_SEQ", allocationSize = 1, name = "KT_NGAN_KHO_SEQ")
  private Long id;
  private String maNgankho;
  private String tenNgankho;
  private Date namSudung;
  private String nhiemVu;
  private String loaikhoId;
  private String chatluongId;
  private String tinhtrangId;
  private String ngankhoHientrangId;
  private BigDecimal dienTichDat;
  private BigDecimal tichLuongTk;
  private BigDecimal tichLuongChua;
  private Long nhakhoId;
  private Long quyhoachDuyetId;
  private BigDecimal tichLuongChuaLt;
  private BigDecimal tichLuongChuaVt;
  private BigDecimal theTichChuaLt;
  private BigDecimal tichLuongKhaDung;
  private BigDecimal tichLuongKdLt;
  private BigDecimal tichLuongKdVt;
  private String huongSuDung;
  private BigDecimal tichLuongKdLtvt;
  private Integer trangThaiTl;
  private Integer namNhap;
  private BigDecimal tichLuongChuaLtGao;
  private BigDecimal tichLuongChuaLtThoc;
  private BigDecimal theTichChuaVt;
  private BigDecimal theTichTk;
  private Long updateStatus;
  private Date lastUpdate;
  private String trangThai;
  private Date ngayTao;
  private String nguoiTao;
  private Date ngaySua;
  private String nguoiSua;
  private String ldoTuchoi;
  private Date ngayGuiDuyet;
  private String nguoiGuiDuyet;
  private Date ngayPduyet;
  private String nguoiPduyet;
}
