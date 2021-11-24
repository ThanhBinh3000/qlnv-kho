package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvNgan;

public interface QlnvNganRepository extends CrudRepository<QlnvNgan, Long> {

	final String qr = "SELECT * FROM QLNV_NGAN WHERE (:maKho is null or lower(MA_KHO) like lower(concat(concat('%', :maKho),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_NGAN WHERE (:maKho is null or lower(MA_KHO) like lower(concat(concat('%', :maKho),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvNgan> selectParams(@Param("maKho") String maKho, Pageable pageable);

}
