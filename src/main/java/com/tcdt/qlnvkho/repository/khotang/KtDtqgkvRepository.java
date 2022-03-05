package com.tcdt.qlnvkho.repository.khotang;

import com.tcdt.qlnvkho.table.khotang.KtDtqgkv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KtDtqgkvRepository extends CrudRepository<KtDtqgkv, Long> {

}
