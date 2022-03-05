package com.tcdt.qlnvkho.table.khotang;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "KT_DIEM_KHO")
@Data
public class KtDiemKho {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_DIEM_KHO_SEQ")
  @SequenceGenerator(sequenceName = "KT_DIEM_KHO_SEQ", allocationSize = 1, name = "KT_DIEM_KHO_SEQ")
  Long id;
  String maDiemkho;
  String tenDiemkho;
  String diaChi;
  Long tinhthanhId;
  Long quanhuyenId;
  Long phuongxaId;
  Date namSudung;
  String loaikhoId;
  String chatluongId;
  String tinhTrangId;
  String nhiemVu;
  Long tuyenKhoId;
  BigDecimal dienTichDat;
  BigDecimal tichLuongThietKe;
  BigDecimal tichLuongChua;
  String tinhtrangCsvc;
  Long hosoPhaply;
  Long hosoKhotang;
  BigDecimal giatriConlai;
  Long khoCuId;
  BigDecimal capKho;
  Date ngayChuyen;
  Long tongkhoId;
  Long quyhoachDuyetId;
  @Lob
  Blob soDoMatBang;
  String diemkhoHientrangId;
  BigDecimal tichLuongKhaDung;
  BigDecimal dienTichDatSodo;
  String soGiayCnQsdd;
  BigDecimal tichLuongChuaLt;
  BigDecimal tichLuongChuaVt;
  BigDecimal tichLuongKdLt;
  BigDecimal tichLuongKdVt;
  BigDecimal tichLuongKdLtvt;
  BigDecimal tichLuongChuaLtGao;
  BigDecimal tichLuongChuaLtThoc;
  BigDecimal thanhTien;
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
