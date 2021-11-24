package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvTtinHdongHdr;

public interface QlnvTtinHdongHdrRepository extends CrudRepository<QlnvTtinHdongHdr, Long> {

	final String qr = "SELECT * FROM QLNV_TTIN_HDONG_HDR WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%'))) AND (:soHdong is null or lower(SO_HDONG) like lower(concat(concat('%', :soHdong),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_TTIN_HDONG_HDR WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%'))) AND (:soHdong is null or lower(SO_HDONG) like lower(concat(concat('%', :soHdong),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvTtinHdongHdr> selectParams(@Param("soQdinh") String soQdinh, @Param("soHdong") String soHdong,
			Pageable pageable);

}
