package com.tcdt.qlnvkho.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkho.jwt.TokenAuthenticationService;
import com.tcdt.qlnvkho.repository.QlnvQhoachKhoHdrRepository;
import com.tcdt.qlnvkho.repository.catalog.QlnvDmDonviEntityRepository;
import com.tcdt.qlnvkho.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkho.repository.catalog.QlnvDmLoaiKhoRepository;
import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.SimpleSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvQhoachKhoDtlReq;
import com.tcdt.qlnvkho.request.object.QlnvQhoachKhoHdrReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.request.search.QlnvDmDonviSearchReq;
import com.tcdt.qlnvkho.request.search.QlnvDmLoaiKhoSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.table.QlnvQhoachKhoDtl;
import com.tcdt.qlnvkho.table.QlnvQhoachKhoHdr;
import com.tcdt.qlnvkho.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkho.table.catalog.QlnvDmDonviEntity;
import com.tcdt.qlnvkho.table.catalog.QlnvDmLoaiKho;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.PaginationSet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/business/qh-kho-tang")
@Api(tags = "Quy hoạch kho tàng")
public class QlnvQhoachKhoHdrController extends BaseController {
	@Autowired
	private QlnvQhoachKhoHdrRepository qlnvQhoachKhoHdrRepository;
	@Autowired
	private QlnvDmDonviEntityRepository qDmDonviEntityRepository;
	
	@Autowired
	private QlnvDmDonviRepository qlnvDmDonviRepository;
	
	@Autowired
	private QlnvDmLoaiKhoRepository qlnvDmLoaiKhoRepository;
	
	@ApiOperation(value = "Tạo mới quy hoạch kho tàng", response = List.class)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest request, @RequestBody QlnvQhoachKhoHdrReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			List<QlnvQhoachKhoDtlReq> dtlReqList = objReq.getDetail();
			objReq.setDetail(null);
			QlnvQhoachKhoHdr dataMap = new ModelMapper().map(objReq, QlnvQhoachKhoHdr.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(authentication.getName());
				for (QlnvQhoachKhoDtlReq dtlReq : dtlReqList) {
					QlnvQhoachKhoDtl detail = new ModelMapper().map(dtlReq, QlnvQhoachKhoDtl.class);
					dataMap.addDetail(detail);
				}
				qlnvQhoachKhoHdrRepository.save(dataMap);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}


	@ApiOperation(value = "Xoá thông tin quy hoạch kho tàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long qhoachId = idSearchReq.getId();
			Optional<QlnvQhoachKhoHdr> QlnvQhoachKhoHdr = qlnvQhoachKhoHdrRepository.findById(qhoachId);
			if (!QlnvQhoachKhoHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			//qlnvQhoachKhoDtlRepository.deleteByIdQhHdr(qhoachId);
			qlnvQhoachKhoHdrRepository.deleteById(qhoachId);
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu quy hoạch kho tàng", response = List.class)
	@PostMapping(value = "/findList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody SimpleSearchReq simpleSearchReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(simpleSearchReq.getPage());
			int limit = PaginationSet.getLimit(simpleSearchReq.getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvQhoachKhoHdr> qhKho = qlnvQhoachKhoHdrRepository.selectParams(simpleSearchReq.getCode(), pageable);

			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setData(qhKho);
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Cập nhật quy hoạch kho tàng", response = List.class)
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request,@RequestBody QlnvQhoachKhoHdrReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");
			Calendar cal = Calendar.getInstance();
			Optional<QlnvQhoachKhoHdr> QlnvQhoachKhoHdr = qlnvQhoachKhoHdrRepository.findById(Long.valueOf(objReq.getId()));
			if (!QlnvQhoachKhoHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");
			QlnvQhoachKhoHdr dataDTB = QlnvQhoachKhoHdr.get();
			List<QlnvQhoachKhoDtlReq> dtlReqList = objReq.getDetail();
			objReq.setDetail(null);
			QlnvQhoachKhoHdr dataMap = new ModelMapper().map(objReq, QlnvQhoachKhoHdr.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			updateObjectToObject(dataDTB, dataMap);
			dataDTB.setNgaySua(cal.getTime());
			dataDTB.setNguoiSua(authentication.getName());
			List<QlnvQhoachKhoDtl> dtlList = dataDTB.getDetailList();
			for (QlnvQhoachKhoDtl dtl : dtlList) {
				dataDTB.removeDetail(dtl);
			}
			for (QlnvQhoachKhoDtlReq dtlReq : dtlReqList) {
				QlnvQhoachKhoDtl detail = new ModelMapper().map(dtlReq, QlnvQhoachKhoDtl.class);
				dataDTB.addDetail(detail);
				//detailList.add(detail);
			}
			qlnvQhoachKhoHdrRepository.save(dataDTB);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	@ApiOperation(value = "Lấy chi tiết thông tin quy hoạch kho tàng", response = List.class)
	@GetMapping(value = "/chi-tiet/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID quy hoạch kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvQhoachKhoHdr> qOptional = qlnvQhoachKhoHdrRepository.findById(Long.parseLong(ids));
			if (!qOptional.isPresent())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			resp.setData(qOptional);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 quy hoạch kho tàng", response = List.class)
	@PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@Valid HttpServletRequest request, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvQhoachKhoHdr> qHoach = qlnvQhoachKhoHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");
			qHoach.get().setTrangThai(stReq.getTrangThai());
			String status = stReq.getTrangThai();
			Calendar cal = Calendar.getInstance();
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			switch(status) {
				case Contains.CHO_DUYET:
					qHoach.get().setNguoiGuiDuyet(authentication.getName());
					qHoach.get().setNgayGuiDuyet(cal.getTime());
					break;
				case Contains.DUYET:
					qHoach.get().setNguoiPduyet(authentication.getName());
					qHoach.get().setNgayPduyet(cal.getTime());
					break;
				case Contains.TU_CHOI:
					qHoach.get().setLdoTuchoi(stReq.getLyDo());
					break;
				default:
					break;
			}
			qlnvQhoachKhoHdrRepository.save(qHoach.get());
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	@ApiOperation(value = "Lấy danh sách đơn vị", response = List.class)
	@PostMapping(value = "/ds-donvi", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvDmDonviSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
			Page<QlnvDmDonviEntity> data = qDmDonviEntityRepository.selectParams(objReq.getMaDvi(), objReq.getTenDvi(),
					objReq.getTrangThai(), objReq.getMaTinh(), objReq.getMaQuan(), objReq.getMaPhuong(),
					objReq.getCapDvi(), objReq.getKieuDvi(), objReq.getLoaiDvi(), pageable);
			resp.setData(data);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	@ApiOperation(value = "Kiểm tra đơn vị theo mã", response = List.class)
	@PostMapping(value = "/find-ma-dv", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailbycode(@Valid @RequestBody BaseRequest objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getStr()))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			QlnvDmDonvi qOptional = qlnvDmDonviRepository.findByMaDvi(objReq.getStr());
			if (qOptional == null)
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			resp.setData(qOptional);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	@ApiOperation(value = "Lấy danh sách loại hình kho", response = List.class)
	@PostMapping(value = "/lh-kho", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvDmLoaiKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {

			Iterable<QlnvDmLoaiKho> data = qlnvDmLoaiKhoRepository.selectAll(objReq.getMaLhKho(), objReq.getTenLhKho(),
					objReq.getTrangThai());
			resp.setData(data);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
}