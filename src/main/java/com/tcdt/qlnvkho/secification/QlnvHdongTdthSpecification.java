package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvHdongTdthSearchReq;
import com.tcdt.qlnvkho.table.QlnvHdongTdth;

public class QlnvHdongTdthSpecification {
	public static Specification<QlnvHdongTdth> buildSearchQuery(final QlnvHdongTdthSearchReq objReq) {
		return new Specification<QlnvHdongTdth>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -5785978485294063868L;

			@Override
			public Predicate toPredicate(Root<QlnvHdongTdth> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soHdong = objReq.getSoHdong();
				if (StringUtils.isNotBlank(soHdong))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soHdong"), soHdong)));
				
				return predicate;
			}
		};
	}
}
