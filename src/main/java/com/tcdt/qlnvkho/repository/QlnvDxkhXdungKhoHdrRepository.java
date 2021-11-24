package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tcdt.qlnvkho.table.QlnvDxkhXdungKhoHdr;

public interface QlnvDxkhXdungKhoHdrRepository extends CrudRepository<QlnvDxkhXdungKhoHdr, Long> {

	final String qr = "SELECT * FROM QLNV_DXKH_XDUNG_KHO_HDR WHERE (:maDvi is null or lower(MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) AND (:soDeNghi is null or lower(SO_DE_NGHI) like lower(concat(concat('%', :soDeNghi),'%'))) AND (:khTuNam is null or KH_TU_NAM >= to_number(:khTuNam)) AND (:khDenNam is null or KH_DEN_NAM <= to_number(:khDenNam))";
	final String qrCount = "SELECT count(1) FROM QLNV_DXKH_XDUNG_KHO_HDR WHERE (:maDvi is null or lower(MA_DVI) like lower(concat(concat('%', :maDvi),'%'))) AND (:soDeNghi is null or lower(SO_DE_NGHI) like lower(concat(concat('%', :soDeNghi),'%'))) AND (:khTuNam is null or KH_TU_NAM >= to_number(:khTuNam)) AND (:khDenNam is null or KH_DEN_NAM <= to_number(:khDenNam))";

	@Query(value = qr, countQuery = qrCount, nativeQuery = true)
	Page<QlnvDxkhXdungKhoHdr> selectParams(@Param("maDvi") String maDvi, @Param("soDeNghi") String soDeNghi,
			@Param("khTuNam") Integer khTuNam, @Param("khDenNam") Integer khDenNam, Pageable pageable);

}
