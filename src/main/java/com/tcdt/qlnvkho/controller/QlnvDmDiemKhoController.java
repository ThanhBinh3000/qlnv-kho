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

import com.tcdt.qlnvkho.enums.EnumResponse;
import com.tcdt.qlnvkho.jwt.TokenAuthenticationService;
import com.tcdt.qlnvkho.repository.QlnvDmDiemKhoRepository;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.SimpleSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvDmDiemKhoReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.table.QlnvDmDiemKho;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.PaginationSet;
import com.tcdt.qlnvkho.util.PathContains;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PathContains.QL_DIEM_KHO)
@Api(tags = "Điểm kho")
public class QlnvDmDiemKhoController extends BaseController {
	@Autowired
	private QlnvDmDiemKhoRepository qlnvDmDiemKhoRepository;

	@ApiOperation(value = "Tạo mới điểm kho", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest req, @RequestBody QlnvDmDiemKhoReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			QlnvDmDiemKho dataMap = new ModelMapper().map(objReq, QlnvDmDiemKho.class);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			qlnvDmDiemKhoRepository.save(dataMap);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xoá thông tin điểm kho", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long qhoachId = idSearchReq.getId();
			Optional<QlnvDmDiemKho> QlnvDmDiemKho = qlnvDmDiemKhoRepository.findById(qhoachId);
			if (!QlnvDmDiemKho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			qlnvDmDiemKhoRepository.deleteById(qhoachId);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu điểm kho", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody SimpleSearchReq simpleSearchReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(simpleSearchReq.getPage());
			int limit = PaginationSet.getLimit(simpleSearchReq.getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvDmDiemKho> qhKho = qlnvDmDiemKhoRepository.selectParams(simpleSearchReq.getCode(), pageable);

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
			resp.setData(qhKho);
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Cập nhật điểm kho", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request, @RequestBody QlnvDmDiemKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");
			Calendar cal = Calendar.getInstance();
			Optional<QlnvDmDiemKho> QlnvDmDiemKho = qlnvDmDiemKhoRepository.findById(Long.valueOf(objReq.getId()));
			if (!QlnvDmDiemKho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");
			QlnvDmDiemKho dataDTB = QlnvDmDiemKho.get();
			QlnvDmDiemKho dataMap = new ModelMapper().map(objReq, QlnvDmDiemKho.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			updateObjectToObject(dataDTB, dataMap);
			dataDTB.setNgaySua(cal.getTime());
			dataDTB.setNguoiSua(authentication.getName());
			qlnvDmDiemKhoRepository.save(dataDTB);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy chi tiết thông tin điểm kho", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID điểm kho", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvDmDiemKho> qOptional = qlnvDmDiemKhoRepository.findById(Long.parseLong(ids));
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

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 điểm kho", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@Valid HttpServletRequest req, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvDmDiemKho> qHoach = qlnvDmDiemKhoRepository.findById(Long.valueOf(stReq.getId()));
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
			qlnvDmDiemKhoRepository.save(qHoach.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
}