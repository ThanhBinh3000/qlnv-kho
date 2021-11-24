package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvKhoTang;

public interface QlnvKhoTangRepository extends CrudRepository<QlnvKhoTang, Long> {

	final String qr = "SELECT * FROM QLNV_KHO_TANG WHERE (:maKho is null or lower(MA_KHO) like lower(concat(concat('%', :maKho),'%'))) AND (:tthai is null or lower(TRANG_THAI) like lower(concat(concat('%', :tthai),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_KHO_TANG WHERE (:maKho is null or lower(MA_KHO) like lower(concat(concat('%', :maKho),'%'))) AND (:tthai is null or lower(TRANG_THAI) like lower(concat(concat('%', :tthai),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvKhoTang> selectParams(@Param("maKho") String maKho, @Param("tthai") String tthai, Pageable pageable);

	QlnvKhoTang findByMaKho(String maKho);
}
