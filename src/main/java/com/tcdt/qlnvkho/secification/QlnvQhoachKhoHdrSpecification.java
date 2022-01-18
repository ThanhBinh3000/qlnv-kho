package com.tcdt.qlnvkho.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.SimpleSearchReq;
import com.tcdt.qlnvkho.table.QlnvQhoachKhoHdr;

public class QlnvQhoachKhoHdrSpecification {
	public static Specification<QlnvQhoachKhoHdr> buildSearchQuery(final SimpleSearchReq objReq) {
		return new Specification<QlnvQhoachKhoHdr>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3571167956165654062L;

			@Override
			public Predicate toPredicate(Root<QlnvQhoachKhoHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soQdinh = objReq.getCode();

				if (StringUtils.isNotEmpty(soQdinh))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soQdinh")), "%" + soQdinh.toLowerCase() + "%"));

				return predicate;
			}
		};
	}

	public static Specification<QlnvQhoachKhoHdr> buildSearchChildQuery(final @Valid SimpleSearchReq objReq) {
		return new Specification<QlnvQhoachKhoHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5640036112400303704L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<QlnvQhoachKhoHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soQdinh = objReq.getCode();
				String maDvi = objReq.getMaDvi();

				Join<Object, Object> fetchParent = (Join<Object, Object>) root.fetch("detailList", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soQdinh))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soQdinh")), "%" + soQdinh.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(fetchParent.get("maDvi"), maDvi)));

				return predicate;
			}
		};
	}

	public static Specification<QlnvQhoachKhoHdr> buildFindByIdQuery(final @Valid IdSearchReq objReq) {
		return new Specification<QlnvQhoachKhoHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1609160279354911278L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<QlnvQhoachKhoHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				Long id = objReq.getId();
				String maDvi = objReq.getMaDvi();

				Join<Object, Object> fetchParent = (Join<Object, Object>) root.fetch("detailList", JoinType.LEFT);

				predicate.getExpressions().add(builder.and(builder.equal(root.get("id"), id)));
				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(fetchParent.get("maDvi"), maDvi)));

				return predicate;
			}
		};
	}
}
