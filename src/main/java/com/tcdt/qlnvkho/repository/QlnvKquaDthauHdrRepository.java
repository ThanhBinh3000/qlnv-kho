package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvKquaDthauHdr;

public interface QlnvKquaDthauHdrRepository extends BaseRepository<QlnvKquaDthauHdr, Long> {

	final String qr = "SELECT * FROM QLNV_KQUA_DTHAU_HDR WHERE (:soQdinhPduyet is null or lower(SO_QDINH_PDUYET) like lower(concat(concat('%', :soQdinhPduyet),'%'))) AND (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%')))";
	final String qrCount = "SELECT count(1) FROM QLNV_KQUA_DTHAU_HDR WHERE (:soQdinhPduyet is null or lower(SO_QDINH_PDUYET) like lower(concat(concat('%', :soQdinhPduyet),'%'))) AND (:soQdinh is null or lower(SO_QDINH) like lower(concat(concat('%', :soQdinh),'%')))";

	@Deprecated
	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvKquaDthauHdr> selectParams(@Param("soQdinhPduyet") String soQdinhPduyet, @Param("soQdinh") String soQdinh,
			Pageable pageable);

}
