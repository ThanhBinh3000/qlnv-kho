package com.tcdt.qlnvkho.repository.khotang;

import com.tcdt.qlnvkho.table.khotang.KtNganLo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtNganLoRepository extends CrudRepository<KtNganLo, Long>, KtNganLoRepositoryCustom {

}
