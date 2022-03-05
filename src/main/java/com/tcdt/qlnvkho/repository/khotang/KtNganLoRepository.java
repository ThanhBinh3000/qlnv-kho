package com.tcdt.qlnvkho.repository.khotang;

import com.tcdt.qlnvkho.table.khotang.KtNganKho;
import com.tcdt.qlnvkho.table.khotang.KtNganLo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KtNganLoRepository extends CrudRepository<KtNganLo, Long> {
    final String qr = "SELECT * FROM KT_NGAN_LO WHERE (:ma is null or lower(MA_NGAN_LO) like lower(concat(concat('%', :ma),'%'))) AND (:ten is null or lower(TEN_NGAN_LO) like lower(concat(concat('%', :ten),'%'))) and (:id is null or NGANKHO_ID = :id)";
    final String qrCount = "SELECT count(1) FROM KT_NGAN_LO WHERE (:ma is null or lower(MA_NGAN_LO) like lower(concat(concat('%', :ma),'%'))) AND (:ten is null or lower(TEN_NGAN_LO) like lower(concat(concat('%', :ten),'%'))) and (:id is null or NGANKHO_ID = :id)";

    @Query(value = qr, countQuery = qrCount, nativeQuery = true)
    Page<KtNganLo> selectParams(@Param("ma") String ma, @Param("ten") String ten, @Param("id") Long id, Pageable pageable);
}
