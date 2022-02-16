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
import com.tcdt.qlnvkho.request.search.QlnvQdXdungKhoHdrSearchReq;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoHdr;

public class QlnvQdXdungKhoHdrSpecification {
	public static Specification<QlnvQdXdungKhoHdr> buildSearchQuery(final @Valid QlnvQdXdungKhoHdrSearchReq objReq) {
		return new Specification<QlnvQdXdungKhoHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvQdXdungKhoHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soQdinh = objReq.getSoQdinh();
				String loaiKhoach = objReq.getLoaiKhoach();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soQdinh))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soQdinh")), "%" + soQdinh.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(loaiKhoach))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("loaiKhoach"), loaiKhoach)));

				return predicate;
			}
		};
	}

	public static Specification<QlnvQdXdungKhoHdr> buildFindByIdQuery(final @Valid IdSearchReq objReq) {
		return new Specification<QlnvQdXdungKhoHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1609160279354911278L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<QlnvQdXdungKhoHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				Long id = objReq.getId();
				String maDvi = objReq.getMaDvi();

				Join<Object, Object> fetchParent = (Join<Object, Object>) root.fetch("children", JoinType.LEFT);

				predicate.getExpressions().add(builder.and(builder.equal(root.get("id"), id)));
				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(fetchParent.get("maDvi"), maDvi)));

				return predicate;
			}
		};
	}
}
