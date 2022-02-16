package com.tcdt.qlnvkho.secification;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvkho.request.search.QlnvDxkhXdungKhoSearchReq;
import com.tcdt.qlnvkho.table.QlnvDxkhXdungKho;

public class QlnvDxkhXdungKhoSpecification {
	public static Specification<QlnvDxkhXdungKho> buildSearchQuery(final QlnvDxkhXdungKhoSearchReq objReq, List<String> listDeXuat) {
		return new Specification<QlnvDxkhXdungKho>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3571167956165654062L;

			@Override
			public Predicate toPredicate(Root<QlnvDxkhXdungKho> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String loaiDxuat = objReq.getLoaiDxuat();
				Date tuNgayDxuat = objReq.getTuNgayDxuat();
				Date denNgayDxuat = objReq.getDenNgayDxuat();
				String trangThai = objReq.getTrangThai();
				String maDvi = objReq.getMaDvi();
				
				root.fetch("children", JoinType.LEFT);

				if (ObjectUtils.isNotEmpty(tuNgayDxuat) && ObjectUtils.isNotEmpty(denNgayDxuat)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayDxuat"), tuNgayDxuat)));
					predicate.getExpressions().add(builder.and(
							builder.lessThan(root.get("ngayDxuat"), new DateTime(denNgayDxuat).plusDays(1).toDate())));
				}

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));

				if (StringUtils.isNotBlank(trangThai))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("trangThai"), trangThai)));

				if (StringUtils.isNotBlank(loaiDxuat))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("loaiDxuat"), loaiDxuat)));
				
				predicate.getExpressions().add(root.get("loaiDxuat").in(listDeXuat));

				return predicate;
			}
		};
	}

	public static Specification<QlnvDxkhXdungKho> buildFindByIdQuery(final @Valid Long ids) {
		return new Specification<QlnvDxkhXdungKho>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5640036112400303704L;

			@Override
			public Predicate toPredicate(Root<QlnvDxkhXdungKho> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ids == 0)
					return predicate;

				Join<Object, Object> fetchParent = (Join<Object, Object>) root.join("children", JoinType.LEFT);
				predicate.getExpressions()
						.add(builder.and(builder.equal(fetchParent.get("dataType"), QlnvDxkhXdungKho.TABLE_NAME)));
				predicate.getExpressions().add(builder.and(builder.equal(root.get("id"), ids)));

				return predicate;
			}
		};
	}
}
