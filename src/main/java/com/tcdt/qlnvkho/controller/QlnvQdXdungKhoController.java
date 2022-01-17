package com.tcdt.qlnvkho.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkho.enums.EnumResponse;
import com.tcdt.qlnvkho.repository.QlnvQdXdungKhoHdr2Repository;
import com.tcdt.qlnvkho.repository.QlnvQdXdungKhoHdrRepository;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.SimpleSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvQdXdungKhoDtlReq;
import com.tcdt.qlnvkho.request.object.QlnvQdXdungKhoHdrReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoDtl;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoHdr;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoHdr2;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.ObjectMapperUtils;
import com.tcdt.qlnvkho.util.PaginationSet;
import com.tcdt.qlnvkho.util.PathContains;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/business/qd-xdung-kho")
@Api(tags = "Quyết định xây dựng kho tàng")
public class QlnvQdXdungKhoController extends BaseController {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private QlnvQdXdungKhoHdrRepository qlnvQdXdungKhoHdrRepository;

	@Autowired
	private QlnvQdXdungKhoHdr2Repository qlnvQdXdungKhoHdr2Repository;

	@ApiOperation(value = "Tạo mới Quyết định xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(HttpServletRequest request, @Valid @RequestBody QlnvQdXdungKhoHdrReq objReq) {
		Resp resp = new Resp();
		try {
			List<QlnvQdXdungKhoDtlReq> dtlReqList = objReq.getDetail();

			QlnvQdXdungKhoHdr dataMap = new ModelMapper().map(objReq, QlnvQdXdungKhoHdr.class);
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));

			List<QlnvQdXdungKhoDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvQdXdungKhoDtl.class);
			dataMap.setChildren(dtls);

			QlnvQdXdungKhoHdr createCheck = qlnvQdXdungKhoHdrRepository.save(dataMap);

			resp.setData(createCheck);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xoá thông tin Quyết định xây dựng kho tàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@Valid @RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvQdXdungKhoHdr> qOptional = qlnvQdXdungKhoHdrRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvQdXdungKhoHdrRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu Quyết định xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(HttpServletRequest request, @RequestBody SimpleSearchReq simpleSearchReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(simpleSearchReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(simpleSearchReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			// Add them dk loc trong child
			Session session = entityManager.unwrap(Session.class);
			if (!getDvi(request).getCapDvi().equals(Contains.CAP_TONG_CUC)) {
				Filter filter = session.enableFilter("pFilter");
				filter.setParameter("maDvi", getDvi(request).getMaDvi());
			}

			Page<QlnvQdXdungKhoHdr2> qhKho = qlnvQdXdungKhoHdr2Repository.selectParams(simpleSearchReq.getCode(),
					pageable);

			session.disableFilter("pFilter");

			resp.setData(qhKho);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Cập nhật Quyết định xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(HttpServletRequest request, @Valid @RequestBody QlnvQdXdungKhoHdrReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvQdXdungKhoHdr> QlnvQdXdungKhoHdr = qlnvQdXdungKhoHdrRepository
					.findById(Long.valueOf(objReq.getId()));
			if (!QlnvQdXdungKhoHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			QlnvQdXdungKhoHdr dataDB = QlnvQdXdungKhoHdr.get();

			List<QlnvQdXdungKhoDtlReq> dtlReqList = objReq.getDetail();
			QlnvQdXdungKhoHdr dataMap = new ModelMapper().map(objReq, QlnvQdXdungKhoHdr.class);

			updateObjectToObject(dataDB, dataMap);
			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(request));

			List<QlnvQdXdungKhoDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvQdXdungKhoDtl.class);
			dataDB.setChildren(dtls);

			qlnvQdXdungKhoHdrRepository.save(dataDB);

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy chi tiết thông tin Quyết định xây dựng kho tàng", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID Quyết định xây dựng kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvQdXdungKhoHdr> qOptional = qlnvQdXdungKhoHdrRepository.findById(Long.parseLong(ids));
			if (!qOptional.isPresent())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			resp.setData(qOptional);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 Quyết định xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(HttpServletRequest request, @Valid @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvQdXdungKhoHdr> qHoach = qlnvQdXdungKhoHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");

			String status = stReq.getTrangThai();
			switch (status) {
			case Contains.CHO_DUYET:
				qHoach.get().setNguoiGuiDuyet(getUserName(request));
				qHoach.get().setNgayGuiDuyet(getDateTimeNow());
				break;
			case Contains.DUYET:
				qHoach.get().setNguoiPduyet(getUserName(request));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				break;
			case Contains.TU_CHOI:
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			default:
				break;
			}

			qHoach.get().setTrangThai(stReq.getTrangThai());
			qlnvQdXdungKhoHdrRepository.save(qHoach.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
}