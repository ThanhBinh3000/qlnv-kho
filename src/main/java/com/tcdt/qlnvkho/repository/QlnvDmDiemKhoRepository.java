package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvDmDiemKho;

public interface QlnvDmDiemKhoRepository extends CrudRepository<QlnvDmDiemKho, Long> {

	final String qr = "SELECT * FROM QLNV_DM_DIEM_KHO WHERE (:maDiemKho is null or lower(MA_DIEM_KHO) like lower(concat(concat('%', :maDiemKho),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_DM_DIEM_KHO WHERE (:maDiemKho is null or lower(MA_DIEM_KHO) like lower(concat(concat('%', :maDiemKho),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvDmDiemKho> selectParams(@Param("maDiemKho") String maDiemKho, Pageable pageable);

	QlnvDmDiemKho findByMaDiemKho(String maDiemKho);
}
