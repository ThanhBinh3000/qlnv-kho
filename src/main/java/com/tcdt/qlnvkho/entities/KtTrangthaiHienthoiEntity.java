package com.tcdt.qlnvkho.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class KtTrangthaiHienthoiEntity {

	@Id
	String nam;
	String maDonVi;
	BigDecimal slHienThoi;
}
