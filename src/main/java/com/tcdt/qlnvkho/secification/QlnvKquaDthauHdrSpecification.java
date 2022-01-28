package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvKquaDthauHdrSearchReq;
import com.tcdt.qlnvkho.table.QlnvKquaDthauHdr;

public class QlnvKquaDthauHdrSpecification {
	public static Specification<QlnvKquaDthauHdr> buildSearchQuery(final QlnvKquaDthauHdrSearchReq objReq) {
		return new Specification<QlnvKquaDthauHdr>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2922059055143008516L;

			@Override
			public Predicate toPredicate(Root<QlnvKquaDthauHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soQdinhPduyet = objReq.getSoQdinhPduyet();
				String soQdinh = objReq.getSoQdinh();

				root.fetch("detailList", JoinType.LEFT);
				if (StringUtils.isNotBlank(soQdinhPduyet))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soQdinhPduyet"), soQdinhPduyet)));

				if (StringUtils.isNotBlank(soQdinh))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soQdinh"), soQdinh)));

				return predicate;
			}
		};
	}
}
