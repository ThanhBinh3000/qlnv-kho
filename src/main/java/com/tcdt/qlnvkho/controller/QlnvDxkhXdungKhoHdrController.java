package com.tcdt.qlnvkho.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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

import com.tcdt.qlnvkho.jwt.TokenAuthenticationService;
import com.tcdt.qlnvkho.repository.QlnvDxkhXdungKhoHdrRepository;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvDxkhXdungKhoDtlReq;
import com.tcdt.qlnvkho.request.object.QlnvDxkhXdungKhoHdrReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.request.search.QlnvDxkhXdungKhoHdrSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.table.QlnvDxkhXdungKhoDtl;
import com.tcdt.qlnvkho.table.QlnvDxkhXdungKhoHdr;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.PaginationSet;
import com.tcdt.qlnvkho.util.ReportPrint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/business/dx-xd-kho")
@Api(tags = "Đề xuất kế hoạch xây dựng Kho tàng trung hạn")
public class QlnvDxkhXdungKhoHdrController extends BaseController {
	@Autowired
	private QlnvDxkhXdungKhoHdrRepository qlnvDxkhXdungKhoHdrRepository;

	@ApiOperation(value = "Tạo mới đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest request, @RequestBody QlnvDxkhXdungKhoHdrReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			List<QlnvDxkhXdungKhoDtlReq> dtlReqList = objReq.getDetail();
			objReq.setDetail(null);
			QlnvDxkhXdungKhoHdr dataMap = new ModelMapper().map(objReq, QlnvDxkhXdungKhoHdr.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTthaiCuc(Contains.TAO_MOI);
			dataMap.setNguoiTao(authentication.getName());
			for (QlnvDxkhXdungKhoDtlReq dtlReq : dtlReqList) {
				QlnvDxkhXdungKhoDtl detail = new ModelMapper().map(dtlReq, QlnvDxkhXdungKhoDtl.class);
				dataMap.addDetail(detail);
			}
			qlnvDxkhXdungKhoHdrRepository.save(dataMap);
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

	@ApiOperation(value = "Xoá thông tin đề xuất kế hoạch xây dựng kho tàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long qhoachId = idSearchReq.getId();
			Optional<QlnvDxkhXdungKhoHdr> QlnvDxkhXdungKhoHdr = qlnvDxkhXdungKhoHdrRepository.findById(qhoachId);
			if (!QlnvDxkhXdungKhoHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			// QlnvDxkhXdungKhoDtlRepository.deleteByIdQhHdr(qhoachId);
			qlnvDxkhXdungKhoHdrRepository.deleteById(qhoachId);
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = "/findList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody QlnvDxkhXdungKhoHdrSearchReq simpleSearchReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(simpleSearchReq.getPage());
			int limit = PaginationSet.getLimit(simpleSearchReq.getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvDxkhXdungKhoHdr> qhKho = qlnvDxkhXdungKhoHdrRepository.selectParams(simpleSearchReq.getMaDvi(),
					simpleSearchReq.getSoDeNghi(), simpleSearchReq.getKhTuNam(), simpleSearchReq.getKhDenNam(),
					pageable);

			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setData(qhKho);
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Cập nhật đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request, @RequestBody QlnvDxkhXdungKhoHdrReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");
			Calendar cal = Calendar.getInstance();
			Optional<QlnvDxkhXdungKhoHdr> QlnvDxkhXdungKhoHdr = qlnvDxkhXdungKhoHdrRepository
					.findById(Long.valueOf(objReq.getId()));
			if (!QlnvDxkhXdungKhoHdr.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");
			QlnvDxkhXdungKhoHdr dataDTB = QlnvDxkhXdungKhoHdr.get();
			List<QlnvDxkhXdungKhoDtlReq> dtlReqList = objReq.getDetail();
			objReq.setDetail(null);
			QlnvDxkhXdungKhoHdr dataMap = new ModelMapper().map(objReq, QlnvDxkhXdungKhoHdr.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			updateObjectToObject(dataDTB, dataMap);
			dataDTB.setNgaySua(cal.getTime());
			dataDTB.setNguoiSua(authentication.getName());
			List<QlnvDxkhXdungKhoDtl> dtlList = dataDTB.getDetailList();
			for (QlnvDxkhXdungKhoDtl dtl : dtlList) {
				dataDTB.removeDetail(dtl);
			}
			for (QlnvDxkhXdungKhoDtlReq dtlReq : dtlReqList) {
				QlnvDxkhXdungKhoDtl detail = new ModelMapper().map(dtlReq, QlnvDxkhXdungKhoDtl.class);
				dataDTB.addDetail(detail);
				// detailList.add(detail);
			}
			qlnvDxkhXdungKhoHdrRepository.save(dataDTB);
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

	@ApiOperation(value = "Lấy chi tiết thông tin đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@GetMapping(value = "/chi-tiet/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID đề xuất kế hoạch xây dựng kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvDxkhXdungKhoHdr> qOptional = qlnvDxkhXdungKhoHdrRepository.findById(Long.parseLong(ids));
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

	@ApiOperation(value = "CẤP CỤC: Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@Valid HttpServletRequest request, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvDxkhXdungKhoHdr> qHoach = qlnvDxkhXdungKhoHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");
			qHoach.get().setTthaiCuc(stReq.getTrangThai());
			String status = stReq.getTrangThai();
			Calendar cal = Calendar.getInstance();
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			switch (status) {
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
			qlnvDxkhXdungKhoHdrRepository.save(qHoach.get());
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "CẤP TỔNG CỤC: Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = "/tc-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatusTongCuc(@Valid HttpServletRequest request, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvDxkhXdungKhoHdr> qHoach = qlnvDxkhXdungKhoHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");
			qHoach.get().setTthaiTong(stReq.getTrangThai());
			String status = stReq.getTrangThai();
			Calendar cal = Calendar.getInstance();
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			switch (status) {
			case Contains.CHO_DUYET:
				qHoach.get().setTcNguoiGuiDuyet(authentication.getName());
				qHoach.get().setTcNgayGuiDuyet(cal.getTime());
				break;
			case Contains.DUYET:
				qHoach.get().setTcNguoiPduyet(authentication.getName());
				qHoach.get().setTcNgayPduyet(cal.getTime());
				break;
			case Contains.TU_CHOI:
				qHoach.get().setTcLdoTuchoi(stReq.getLyDo());
				break;
			default:
				break;
			}
			qlnvDxkhXdungKhoHdrRepository.save(qHoach.get());
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "In thông tin đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@GetMapping(value = "/in-dx/{ids}", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> getReport(
			@ApiParam(value = "ID đề xuất kế hoạch xây dựng kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		try {
			// JasperPrint
			List<Object[]> results = new ArrayList<Object[]>();
			Map<String, Object> parameters = new HashMap<>();
			return new ResponseEntity<byte[]>(ReportPrint.jasperReportPdf(parameters, "DxuatXdungKho", results),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "In dự thảo đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@GetMapping(value = "/in-dthao/{ids}", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> getReportDuThao(
			@ApiParam(value = "ID đề xuất kế hoạch xây dựng kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		try {
			// JasperPrint
			List<Object[]> results = new ArrayList<Object[]>();
			Map<String, Object> parameters = new HashMap<>();
			return new ResponseEntity<byte[]>(ReportPrint.jasperReportPdf(parameters, "DxuatXdungKho", results),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}