package com.tcdt.qlnvkho.repository.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcdt.qlnvkho.table.catalog.QlnvDmCongcu;

@Repository
public interface QlnvDmCongcuRepository extends CrudRepository<QlnvDmCongcu, Long> {
	@Query(value = "SELECT * FROM QLNV_DM_CONGCU t WHERE (:maCcu is null or lower(t.MA_CCU) like lower(concat(concat('%', :maCcu),'%'))) "
			+ "AND (:tenCcu is null or lower(t.TEN_CCU) like lower(concat(concat('%', :tenCcu),'%'))) AND (:trangThai is null or t.TRANG_THAI = :trangThai)", countQuery = "SELECT count(1) FROM QLNV_DM_CONGCU t "
					+ "WHERE (:maCcu is null or lower(t.MA_CCU) like lower(concat(concat('%', :maCcu),'%'))) "
					+ "AND (:tenCcu is null or lower(t.TEN_CCU) like lower(concat(concat('%', :tenCcu),'%'))) AND (:trangThai is null or t.TRANG_THAI = :trangThai)", nativeQuery = true)
	Page<QlnvDmCongcu> selectParams(String maCcu, String tenCcu, String trangThai, Pageable pageable);

	QlnvDmCongcu findByMaCcu(String maCcu);

	Iterable<QlnvDmCongcu> findByTrangThai(String hoatDong);

}
