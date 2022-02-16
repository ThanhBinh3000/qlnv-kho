package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvDuAnKhoSearchReq;
import com.tcdt.qlnvkho.table.QlnvDuAnKho;

public class QlnvDuAnKhoSpecification {

	public static Specification<QlnvDuAnKho> buildSearchQuery(final QlnvDuAnKhoSearchReq objReq) {
		return new Specification<QlnvDuAnKho>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 2158801829092610300L;

			@Override
			public Predicate toPredicate(Root<QlnvDuAnKho> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String maDuAn = objReq.getMaDuAn();
				String cucDuTru = objReq.getCucDuTru();

				if (StringUtils.isNotBlank(maDuAn))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDuAn"), maDuAn)));
				
				if (StringUtils.isNotBlank(cucDuTru))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("cucQuanLy"), cucDuTru)));

				return predicate;
			}
		};
	}
}
