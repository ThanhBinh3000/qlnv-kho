package com.tcdt.qlnvkho.repository.khotang;

import com.tcdt.qlnvkho.table.khotang.KtNganKho;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtNganKhoRepository extends CrudRepository<KtNganKho, Long>, KtNganKhoRepositoryCustom {

}