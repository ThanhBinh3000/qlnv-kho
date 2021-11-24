package com.tcdt.qlnvkho.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkho.table.QlnvKhoThuKho;

public interface QlnvKhoThuKhoRepository extends CrudRepository<QlnvKhoThuKho, Long> {
	@Query(value = "SELECT * FROM QLNV_KHO_THUKHO t WHERE (lower(t.MA_KHO) like lower(concat(concat('%', :maKho),'%'))) "
			+ "AND (:trangThai is null or t.TRANG_THAI = :trangThai)", countQuery = "SELECT count(1) FROM QLNV_KHO_THUKHO t "
					+ " WHERE (lower(t.MA_KHO) like lower(concat(concat('%', :maKho),'%'))) "
					+ " AND (:trangThai is null or t.TRANG_THAI = :trangThai)", nativeQuery = true)
	Page<QlnvKhoThuKho> selectParams(String maKho, String trangThai, Pageable pageable);
}
