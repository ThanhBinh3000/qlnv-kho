package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvQdXdungKhoHdrSearchReq;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoHdr2;

public class QlnvQdXdungKhoHdr2Specification {
	/**
	 * Search kế hoạch hàng năm thưởng xuyên được tổng cục duyệt
	 */
	public static Specification<QlnvQdXdungKhoHdr2> buildSearchQueryHnTxdtcd(final QlnvQdXdungKhoHdrSearchReq objReq) {
		return new Specification<QlnvQdXdungKhoHdr2>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2259387517836689212L;

			@Override
			public Predicate toPredicate(Root<QlnvQdXdungKhoHdr2> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soQdinh = objReq.getSoQdinh();
				String loaiKhoach = objReq.getLoaiKhoach();
				
				if (StringUtils.isNotEmpty(soQdinh))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soQdinh")), "%" + soQdinh.toLowerCase() + "%"));
				
				if (StringUtils.isNotBlank(loaiKhoach))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("loaiKhoach"), loaiKhoach)));

				return predicate;
			}
		};
	}
}
