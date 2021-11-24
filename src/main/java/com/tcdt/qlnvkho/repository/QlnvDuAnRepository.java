package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvDuAn;

public interface QlnvDuAnRepository extends CrudRepository<QlnvDuAn, Long> {

	final String qr = "SELECT * FROM QLNV_DU_AN WHERE (:maDuAn is null or lower(MA_DU_AN) like lower(concat(concat('%', :maDuAn),'%'))) AND (:maDvi is null or lower(MA_DVI) like lower(concat(concat('%', :maDvi),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_DU_AN WHERE (:maDuAn is null or lower(MA_DU_AN) like lower(concat(concat('%', :maDuAn),'%'))) AND (:maDvi is null or lower(MA_DVI) like lower(concat(concat('%', :maDvi),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvDuAn> selectParams(@Param("maDuAn") String maKho, @Param("maDvi") String maDvi, Pageable pageable);
}
