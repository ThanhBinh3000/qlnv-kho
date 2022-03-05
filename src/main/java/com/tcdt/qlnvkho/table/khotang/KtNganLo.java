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
@Table(name = "KT_NGAN_LO")
@Data
public class KtNganLo {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_NGAN_LO_SEQ")
  @SequenceGenerator(sequenceName = "KT_NGAN_LO_SEQ", allocationSize = 1, name = "KT_NGAN_LO_SEQ")
  Long id;
  String maNganlo;
  String tenNganlo;
  Date namSudung;
  String nhiemVu;
  String loaikhoId;
  String chatluongId;
  String tinhtrangId;
  String nganloHientrangId;
  BigDecimal dienTichDat;
  BigDecimal tichLuongTk;
  BigDecimal tichLuongChua;
  Long ngankhoId;
  Long quyhoachDuyetId;
  BigDecimal tichLuongChuaLt;
  BigDecimal tichLuongChuaVt;
  BigDecimal theTichChuaLt;
  BigDecimal tichLuongKhaDung;
  BigDecimal tichLuongKdLt;
  BigDecimal tichLuongKdVt;
  String huongSuDung;
  BigDecimal tichLuongKdLtvt;
  Integer trangThaiTl;
  Integer namNhap;
  BigDecimal tichLuongChuaLtGao;
  BigDecimal tichLuongChuaLtThoc;
  BigDecimal theTichChuaVt;
  BigDecimal theTichTk;
  Long updateStatus;
  Date lastUpdate;
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
}
