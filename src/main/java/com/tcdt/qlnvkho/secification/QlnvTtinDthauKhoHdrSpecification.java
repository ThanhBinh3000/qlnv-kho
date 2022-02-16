package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvTtinDthauKhoHdrSearchReq;
import com.tcdt.qlnvkho.table.QlnvTtinDthauKhoHdr;

public class QlnvTtinDthauKhoHdrSpecification {

	public static Specification<QlnvTtinDthauKhoHdr> buildSearchQuery(final QlnvTtinDthauKhoHdrSearchReq objReq) {
		return new Specification<QlnvTtinDthauKhoHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2158801829092610300L;

			@Override
			public Predicate toPredicate(Root<QlnvTtinDthauKhoHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String maDuAn = objReq.getMaDuAn();
				String soQdinh = objReq.getSoQdinh();

				root.fetch("detailList", JoinType.LEFT);
				if (StringUtils.isNotBlank(maDuAn))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDuAn"), maDuAn)));

				if (StringUtils.isNotBlank(soQdinh))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soQdinh"), soQdinh)));

				return predicate;
			}
		};
	}
}
