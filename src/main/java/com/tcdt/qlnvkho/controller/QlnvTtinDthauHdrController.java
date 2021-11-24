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
import com.tcdt.qlnvkho.repository.QlnvTtinDthauHdrRepository;
import com.tcdt.qlnvkho.repository.catalog.QlnvDmDonviEntityRepository;
import com.tcdt.qlnvkho.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvTtinDthauDtlCtietReq;
import com.tcdt.qlnvkho.request.object.QlnvTtinDthauDtlReq;
import com.tcdt.qlnvkho.request.object.QlnvTtinDthauHdrReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.request.search.QlnvDmDonviSearchReq;
import com.tcdt.qlnvkho.request.search.QlnvTtinDthauHdrSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.table.QlnvTtinDthauDtl;
import com.tcdt.qlnvkho.table.QlnvTtinDthauDtlCtiet;
import com.tcdt.qlnvkho.table.QlnvTtinDthauHdr;
import com.tcdt.qlnvkho.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvkho.table.catalog.QlnvDmDonviEntity;
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
@RequestMapping("/business/qh-ttin-dthau")
@Api(tags = "Thông tin đấu thầu")
public class QlnvTtinDthauHdrController extends BaseController {
	@Autowired
	private QlnvTtinDthauHdrRepository qlnvTtinDthauHdrRepository;
	@Autowired
	private QlnvDmDonviEntityRepository qDmDonviEntityRepository;
	
	@Autowired
	private QlnvDmDonviRepository qlnvDmDonviRepository;
	
	
	@ApiOperation(value = "Tạo mới Thông tin đấu thầu", response = List.class)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest request, @RequestBody QlnvTtinDthauHdrReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			List<QlnvTtinDthauDtlReq> dtlReqList = objReq.getDetail();
			objReq.setDetail(null);
			QlnvTtinDthauHdr dataMap = new ModelMapper().map(objReq, QlnvTtinDthauHdr.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(authentication.getName());
				for (QlnvTtinDthauDtlReq dtlReq : dtlReqList) {
					QlnvTtinDthauDtl detail = new ModelMapper().map(dtlReq, QlnvTtinDthauDtl.class);
					List<QlnvTtinDthauDtlCtietReq> dtlCtietReqList = dtlReq.getDetail();
					dtlReq.setDetail(null);
						for (QlnvTtinDthauDtlCtietReq dtlCtReq : dtlCtietReqList) {
							QlnvTtinDthauDtlCtiet detailCtiet = new ModelMapper().map(dtlCtReq, QlnvTtinDthauDtlCtiet.class);
							detail.addDetail(detailCtiet);
						}
					dataMap.addDetail(detail);
				}
				qlnvTtinDthauHdrRepository.save(dataMap);
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


	@ApiOperation(value = "Xoá thông tin Thông tin đấu thầu", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long qhoachId = idSearchReq.getId();
			Optional<QlnvTtinDthauHdr> QlnvTtinDthauHdr = qlnvTtinDthauHdrRepository.findById(qhoachId);
			if (!QlnvTtinDthauHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			//QlnvTtinDthauDtlRepository.deleteByIdQhHdr(qhoachId);
			qlnvTtinDthauHdrRepository.deleteById(qhoachId);
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu Thông tin đấu thầu", response = List.class)
	@PostMapping(value = "/findList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody QlnvTtinDthauHdrSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<QlnvTtinDthauHdr> qhKho = qlnvTtinDthauHdrRepository.selectParams(objReq.getSoQdinh(),objReq.getMaDuAn(), pageable);

			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setData(qhKho);
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Cập nhật Thông tin đấu thầu", response = List.class)
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request,@RequestBody QlnvTtinDthauHdrReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");
			Calendar cal = Calendar.getInstance();
			Optional<QlnvTtinDthauHdr> QlnvTtinDthauHdr = qlnvTtinDthauHdrRepository.findById(Long.valueOf(objReq.getId()));
			if (!QlnvTtinDthauHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");
			QlnvTtinDthauHdr dataDTB = QlnvTtinDthauHdr.get();
			List<QlnvTtinDthauDtlReq> dtlReqList = objReq.getDetail();
			objReq.setDetail(null);
			QlnvTtinDthauHdr dataMap = new ModelMapper().map(objReq, QlnvTtinDthauHdr.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			updateObjectToObject(dataDTB, dataMap);
			dataDTB.setNgaySua(cal.getTime());
			dataDTB.setNguoiSua(authentication.getName());
			List<QlnvTtinDthauDtl> dtlList = dataDTB.getDetailList();
			for (QlnvTtinDthauDtl dtl : dtlList) {
				List<QlnvTtinDthauDtlCtiet> dtlCtietList = dtl.getDetailList();
				for (QlnvTtinDthauDtlCtiet dtlCtiet : dtlCtietList) {
					dtl.removeDetail(dtlCtiet);
				}
				
				dataDTB.removeDetail(dtl);
			}
			for (QlnvTtinDthauDtlReq dtlReq : dtlReqList) {
				QlnvTtinDthauDtl detail = new ModelMapper().map(dtlReq, QlnvTtinDthauDtl.class);
				List<QlnvTtinDthauDtlCtietReq> dtlCtietReqList = dtlReq.getDetail();
				dtlReq.setDetail(null);
					for (QlnvTtinDthauDtlCtietReq dtlCtReq : dtlCtietReqList) {
						QlnvTtinDthauDtlCtiet detailCtiet = new ModelMapper().map(dtlCtReq, QlnvTtinDthauDtlCtiet.class);
						detail.addDetail(detailCtiet);
					}
				dataDTB.addDetail(detail);
				//detailList.add(detail);
			}
			qlnvTtinDthauHdrRepository.save(dataDTB);
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
	@ApiOperation(value = "Lấy chi tiết thông tin Thông tin đấu thầu", response = List.class)
	@GetMapping(value = "/chi-tiet/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID Thông tin đấu thầu", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvTtinDthauHdr> qOptional = qlnvTtinDthauHdrRepository.findById(Long.parseLong(ids));
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
	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 Thông tin đấu thầu", response = List.class)
	@PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@Valid HttpServletRequest request, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvTtinDthauHdr> qHoach = qlnvTtinDthauHdrRepository.findById(Long.valueOf(stReq.getId()));
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
			qlnvTtinDthauHdrRepository.save(qHoach.get());
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
}