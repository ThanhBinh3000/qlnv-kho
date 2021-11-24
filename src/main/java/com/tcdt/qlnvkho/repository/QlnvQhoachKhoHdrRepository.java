package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvQhoachKhoHdr;

public interface QlnvQhoachKhoHdrRepository extends CrudRepository<QlnvQhoachKhoHdr, Long> {

	final String qr = "SELECT * FROM QLNV_QHOACH_KHO_HDR WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_QHOACH_KHO_HDR WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%')))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvQhoachKhoHdr> selectParams(@Param("soQdinh") String soQdinh, Pageable pageable);

	// Iterable<QlnvQhoachKhoHdr> findByStatus(String ttNhomHoatdong);
}
