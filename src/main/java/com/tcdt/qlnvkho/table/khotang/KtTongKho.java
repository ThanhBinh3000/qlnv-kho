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
@Table(name = "KT_TONG_KHO")
@Data
public class KtTongKho {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_TONG_KHO_SEQ")
  @SequenceGenerator(sequenceName = "KT_TONG_KHO_SEQ", allocationSize = 1, name = "KT_TONG_KHO_SEQ")
  Long id;
  String maTongKho;
  String tenTongKho;
  String diaChi;
  Long tinhthanhId;
  Long quanhuyenId;
  Long phuongxaId;
  BigDecimal tichLuong;
  String nhiemVu;
  String chatLuongId;
  String tinhTrangId;
  Long hosoPhaply;
  Long hosoKhotang;
  BigDecimal giatriConlai;
  String khoCuId;
  Long capKho;
  Date ngayChuyen;
  Long dtqgkvId;
  Long quyhoachDuyetId;
  String tongkhoHientrangId;
  BigDecimal tichLuongThietKe;
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
