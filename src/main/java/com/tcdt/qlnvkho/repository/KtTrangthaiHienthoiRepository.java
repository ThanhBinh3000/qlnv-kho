package com.tcdt.qlnvkho.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkho.entities.KtTrangthaiHienthoiEntity;

public interface KtTrangthaiHienthoiRepository extends CrudRepository<KtTrangthaiHienthoiEntity, Long> {

	@Query(value = "select SUM(SL_HIEN_THOI) as SL_HIEN_THOI, ma_don_vi,nam from KT_TRANGTHAI_HIENTHOI where ma_vthh like lower(concat('%', :maVthh)) and ma_don_vi = :maDvi  GROUP by ma_don_vi,nam order by nam", nativeQuery = true)
	Iterable<KtTrangthaiHienthoiEntity> selectParams(String maDvi, String maVthh);

}
