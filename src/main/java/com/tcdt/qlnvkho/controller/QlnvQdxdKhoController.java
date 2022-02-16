package com.tcdt.qlnvkho.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
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
import com.tcdt.qlnvkho.repository.QlnvQdXdungKhoHdrRepository;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvQdXdungKhoDtlReq;
import com.tcdt.qlnvkho.request.object.QlnvQdXdungKhoHdrReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.request.search.QlnvQdXdungKhoHdrSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.secification.QlnvQdXdungKhoHdrSpecification;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoDtl;
import com.tcdt.qlnvkho.table.QlnvQdXdungKhoHdr;
import com.tcdt.qlnvkho.table.catalog.QlnvDmDonvi;
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
@RequestMapping(value = PathContains.QL_KHXD_TRUNG_HAN)
@Api(tags = "Quyết định xây dựng kho tàng trung hạn")
public class QlnvQdxdKhoController extends BaseController {
	@Autowired
	private QlnvQdXdungKhoHdrRepository qlnvQdXdungKhoHdrRepository;

	@ApiOperation(value = "Tạo mới Quyết định xây dựng kho tàng trung hạn", response = List.class)
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
			dataMap.setLoaiKhoach(Contains.KH_TRUNG_HAN);
			dataMap.setLoaiQd(objReq.getLoaiQd().equals(Contains.QD_MOI) ? Contains.QD_MOI : Contains.QD_DIEU_CHINH);

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

	@ApiOperation(value = "Xoá thông tin Quyết định xây dựng kho tàng trung hạn", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
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

	@ApiOperation(value = "Tra cứu Quyết định xây dựng kho tàng trung hạn", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(HttpServletRequest request,
			@Valid @RequestBody QlnvQdXdungKhoHdrSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			objReq.setLoaiKhoach(Contains.DXKH_TRUNG_HAN);

			Page<QlnvQdXdungKhoHdr> qhKho = qlnvQdXdungKhoHdrRepository
					.findAll(QlnvQdXdungKhoHdrSpecification.buildSearchQuery(objReq), pageable);

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

	@ApiOperation(value = "Cập nhật Quyết định xây dựng kho tàng trung hạn", response = List.class)
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
			dataMap.setLoaiKhoach(Contains.KH_TRUNG_HAN);
			dataMap.setLoaiQd(objReq.getLoaiQd().equals(Contains.QD_MOI) ? Contains.QD_MOI : Contains.QD_DIEU_CHINH);

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

	@ApiOperation(value = "Lấy chi tiết thông tin Quyết định xây dựng kho tàng trung hạn", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID Quyết định xây dựng kho tàng trung hạn", example = "1", required = true) @PathVariable("ids") String ids) {
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

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 Quyết định xây dựng kho tàng trung hạn", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(HttpServletRequest req, @Valid @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvQdXdungKhoHdr> qHoach = qlnvQdXdungKhoHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");

			String status = stReq.getTrangThai() + qHoach.get().getTrangThai();
			switch (status) {
			case Contains.CHO_DUYET + Contains.MOI_TAO:
				qHoach.get().setNguoiGuiDuyet(getUserName(req));
				qHoach.get().setNgayGuiDuyet(getDateTimeNow());
				break;
			case Contains.TU_CHOI + Contains.CHO_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.DUYET + Contains.CHO_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				break;
			default:
				throw new Exception("Phê duyệt không thành công");
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

	@ApiOperation(value = "Lấy chi tiết thông tin Quyết định xây dựng kho tàng trung hạn dành cho cấp cục", response = List.class)
	@PostMapping(value = PathContains.URL_CHI_TIET
			+ PathContains.URL_CAP_CUC, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailChild(@Valid @RequestBody IdSearchReq objReq, HttpServletRequest request) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			// Lay thong tin don vi quan ly
			QlnvDmDonvi objDvi = getDvi(request);
			if (ObjectUtils.isEmpty(objDvi) || StringUtils.isEmpty(objDvi.getCapDvi()))
				throw new UnsupportedOperationException("Không lấy được thông tin đơn vị");

			// Add them dk loc trong child
			if (!objDvi.getCapDvi().equals(Contains.CAP_TONG_CUC))
				objReq.setMaDvi(objDvi.getMaDvi());

			List<QlnvQdXdungKhoHdr> lKhoHdrs = qlnvQdXdungKhoHdrRepository
					.findAll(QlnvQdXdungKhoHdrSpecification.buildFindByIdQuery(objReq));

			if (lKhoHdrs.isEmpty())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			resp.setData(lKhoHdrs.get(0));
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