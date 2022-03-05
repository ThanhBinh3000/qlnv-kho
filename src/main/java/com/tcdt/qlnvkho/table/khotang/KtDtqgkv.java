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
@Table(name = "KT_DTQGKV")
@Data
public class KtDtqgkv {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KT_DTQGKV_SEQ")
  @SequenceGenerator(sequenceName = "KT_DTQGKV_SEQ", allocationSize = 1, name = "KT_DTQGKV_SEQ")
  Long id;
  String maDtqgkv;
  String tenDtqgkv;
  String diaChi;
  Long tinhthanhId;
  Long quanhuyenId;
  Long phuongxaId;
  String moTa;
  Long quyhoachDuyetId;
  Long dtqgkvHientrangId;
  BigDecimal tichLuongKhaDung;
  BigDecimal tichLuongChua;
  BigDecimal tichLuongChuaLt;
  BigDecimal tichLuongChuaVt;
  BigDecimal tichLuongKdLt;
  BigDecimal tichLuongKdVt;
  BigDecimal tichLuongThietKe;
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
  Date ngayDongboSauCung;
}
