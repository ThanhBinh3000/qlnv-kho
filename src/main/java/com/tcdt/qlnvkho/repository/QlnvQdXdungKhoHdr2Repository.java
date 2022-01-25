package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvQdXdungKhoHdr2;

public interface QlnvQdXdungKhoHdr2Repository extends BaseRepository<QlnvQdXdungKhoHdr2, Long> {

	final String querySelect = "SELECT *";
	final String queryCount = "SELECT count(1)";
	final String queryTable = " FROM QLNV_QD_XDUNG_KHO_HDR"
			+ " WHERE (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%')))";
	
	@Query(value = querySelect + queryTable, countQuery = queryCount + queryTable, nativeQuery = true)
	Page<QlnvQdXdungKhoHdr2> selectParams(@Param("soQdinh") String soQdinh, Pageable pageable);

}
