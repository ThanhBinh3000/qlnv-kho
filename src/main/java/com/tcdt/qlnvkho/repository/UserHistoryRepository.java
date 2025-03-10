package com.tcdt.qlnvkho.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tcdt.qlnvkho.table.UserHistory;
import com.tcdt.qlnvkho.util.Contains;

public interface UserHistoryRepository extends CrudRepository<UserHistory, Long> {

	@Query(value = "SELECT * FROM USER_HISTORY WHERE (:userName is null or lower(USERNAME) like lower(concat(concat('%', :userName),'%'))) "
			+ "AND (:tuNgay is null or TRUNC(TIMEACTION) > TO_DATE(:tuNgay,'" + Contains.FORMAT_DATE_STR + "')-1) "
			+ "AND (:denNgay is null or TRUNC(TIMEACTION) <= TO_DATE(:denNgay,'" + Contains.FORMAT_DATE_STR
			+ "')) ORDER BY TIMEACTION DESC", countQuery = "SELECT count(1) FROM USER_HISTORY WHERE (:userName is null or lower(USERNAME) like lower(concat(concat('%', :userName),'%'))) "
					+ "AND (:tuNgay is null or TRUNC(TIMEACTION) > TO_DATE(:tuNgay,'" + Contains.FORMAT_DATE_STR
					+ "')-1) " + "AND (:denNgay is null or TRUNC(TIMEACTION) <= TO_DATE(:denNgay,'"
					+ Contains.FORMAT_DATE_STR + "'))", nativeQuery = true)
	Page<UserHistory> selectParams(String userName, String tuNgay, String denNgay, Pageable pageable);

	@Query(value = "SELECT * FROM USER_HISTORY WHERE (:userName is null or lower(USERNAME) like lower(concat(concat('%', :userName),'%'))) "
			+ "AND TRUNC(TIMEACTION) > TO_DATE(:tuNgay,'" + Contains.FORMAT_DATE_STR
			+ "')-1 AND TRUNC(TIMEACTION) <= TO_DATE(:denNgay,'" + Contains.FORMAT_DATE_STR
			+ "') ORDER BY TIMEACTION DESC", nativeQuery = true)
	List<UserHistory> listAllParams(String userName, String tuNgay, String denNgay);

	@Query(value = "SELECT * FROM USER_HISTORY WHERE (:userName is null or lower(USERNAME) like lower(concat(concat('%', :userName),'%'))) "
			+ "AND (:tuNgay is null or TRUNC(TIMEACTION) > TO_DATE(:tuNgay,'" + Contains.FORMAT_DATE_STR
			+ "')-1) AND (:denNgay is null or TRUNC(TIMEACTION) <= TO_DATE(:denNgay,'" + Contains.FORMAT_DATE_STR
			+ "')) ORDER BY TIMEACTION DESC", nativeQuery = true)
	List<UserHistory> selectParams(String userName, String tuNgay, String denNgay);

}