package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvTtinDthauHdr;

public interface QlnvTtinDthauHdrRepository extends CrudRepository<QlnvTtinDthauHdr, Long> {

	final String qr = "SELECT * FROM QLNV_TTIN_DTHAU_HDR WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%'))) AND (:maDuAn is null or lower(MA_DU_AN) like lower(concat(concat('%', :maDuAn),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_TTIN_DTHAU_HDR WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%'))) AND (:maDuAn is null or lower(MA_DU_AN) like lower(concat(concat('%', :maDuAn),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvTtinDthauHdr> selectParams(@Param("soQdinh") String soQdinh, @Param("maDuAn") String maDuAn,
			Pageable pageable);

}
