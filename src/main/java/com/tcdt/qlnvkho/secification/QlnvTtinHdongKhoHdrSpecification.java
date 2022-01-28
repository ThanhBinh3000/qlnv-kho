package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvTtinHdongKhoHdrSearchReq;
import com.tcdt.qlnvkho.table.QlnvTtinHdongKhoHdr;

public class QlnvTtinHdongKhoHdrSpecification {
	public static Specification<QlnvTtinHdongKhoHdr> buildSearchQuery(final QlnvTtinHdongKhoHdrSearchReq objReq) {
		return new Specification<QlnvTtinHdongKhoHdr>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7380669145671281350L;

			@Override
			public Predicate toPredicate(Root<QlnvTtinHdongKhoHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soHdong = objReq.getSoHdong();
				String soQdinh = objReq.getSoQdinh();

				if (StringUtils.isNotBlank(soHdong))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soHdong"), soHdong)));

				if (StringUtils.isNotBlank(soQdinh))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soQdinh"), soQdinh)));

				return predicate;
			}
		};
	}
}
