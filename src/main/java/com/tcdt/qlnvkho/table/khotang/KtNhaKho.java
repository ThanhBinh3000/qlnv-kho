package com.tcdt.qlnvkho.table.khotang;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "KT_NHA_KHO")
@Data
public class KtNhaKho {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_NHA_KHO_SEQ")
  @SequenceGenerator(sequenceName = "KT_NHA_KHO_SEQ", allocationSize = 1, name = "KT_NHA_KHO_SEQ")
  Long id;
  String maNhakho;
  String tenNhakho;
  Date namSudung;
  String nhiemVu;
  String loaikhoId;
  String chatluongId;
  String tinhtrangId;
  String nhakhoHientrangId;
  BigDecimal dienTichDat;
  BigDecimal tichLuongTk;
  Long diemkhoId;
  Long quyhoachDuyetId;
  @Lob
  Blob banVeKt;
  BigDecimal tichLuongChua;
  BigDecimal tichLuongKhaDung;
  BigDecimal tichLuongChuaLt;
  BigDecimal tichLuongChuaVt;
  BigDecimal tichLuongKdLt;
  BigDecimal tichLuongKdVt;
  BigDecimal tichLuongKdLtvt;
  BigDecimal tichLuongChuaLtGao;
  BigDecimal tichLuongChuaLtThoc;
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
