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

import com.tcdt.qlnvkho.enums.EnumResponse;
import com.tcdt.qlnvkho.jwt.TokenAuthenticationService;
import com.tcdt.qlnvkho.repository.QlnvDmDiemKhoRepository;
import com.tcdt.qlnvkho.repository.QlnvKhoTangRepository;
import com.tcdt.qlnvkho.repository.QlnvKhoThuKhoRepository;
import com.tcdt.qlnvkho.repository.catalog.QlnvDmThukhoRepository;
import com.tcdt.qlnvkho.request.BaseRequest;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvKhoTangReq;
import com.tcdt.qlnvkho.request.object.QlnvKhoThuKhoReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.request.search.QlnvDmThukhoSearchReq;
import com.tcdt.qlnvkho.request.search.QlnvKhoTangSearchReq;
import com.tcdt.qlnvkho.request.search.QlnvKhoThuKhoSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.table.QlnvDmDiemKho;
import com.tcdt.qlnvkho.table.QlnvKhoTang;
import com.tcdt.qlnvkho.table.QlnvKhoThuKho;
import com.tcdt.qlnvkho.table.catalog.QlnvDmThukho;
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
@RequestMapping("/business/kho-tang")
@Api(tags = "Kho tàng")
public class QlnvKhoTangController extends BaseController {
	@Autowired
	private QlnvKhoTangRepository qlnvKhoTangRepository;
	@Autowired
	private QlnvDmThukhoRepository qlnvDmThukhoRepository;
	@Autowired
	private QlnvDmDiemKhoRepository qlnvDmDiemKhoRepository;
	@Autowired
	private QlnvKhoThuKhoRepository qlnvKhoThuKhoRepository;

	@ApiOperation(value = "Tạo mới kho tàng", response = List.class)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest request, @RequestBody QlnvKhoTangReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			QlnvKhoTang dataMap = new ModelMapper().map(objReq, QlnvKhoTang.class);
			QlnvKhoTang qOptional = qlnvKhoTangRepository.findByMaKho(objReq.getMaKho());
			if (qOptional != null)
				throw new UnsupportedOperationException("Đã tồn tại Kho với mã " + objReq.getMaKho());
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(authentication.getName());
			qlnvKhoTangRepository.save(dataMap);
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

	@ApiOperation(value = "Điều chuyển kho tàng", response = List.class)
	@PostMapping(value = "/dieu-chuyen", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> dieuchuyen(@Valid HttpServletRequest request, @RequestBody QlnvKhoTangReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			QlnvKhoTang dataMap = new ModelMapper().map(objReq, QlnvKhoTang.class);
			QlnvKhoTang checkExist = qlnvKhoTangRepository.findByMaKho(objReq.getMaKho());
			if (checkExist != null)
				throw new UnsupportedOperationException("Đã tồn tại Kho với mã " + objReq.getMaKho());
			QlnvKhoTang qOptional = qlnvKhoTangRepository.findByMaKho(objReq.getMaKhoCu());
			if (qOptional == null)
				throw new UnsupportedOperationException("Không tồn tại Kho với mã " + objReq.getMaKhoCu());
			if (qOptional.getTinhTrang() == Contains.HOAT_DONG)
				throw new UnsupportedOperationException(
						"Không thể điều chuyển Kho với mã " + objReq.getMaKhoCu() + " đang ở tình trạng Hoạt động");
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(authentication.getName());
			qlnvKhoTangRepository.save(dataMap);
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

	@ApiOperation(value = "Xoá thông tin kho tàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long qhoachId = idSearchReq.getId();
			Optional<QlnvKhoTang> QlnvKhoTang = qlnvKhoTangRepository.findById(qhoachId);
			if (!QlnvKhoTang.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			qlnvKhoTangRepository.deleteById(qhoachId);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu kho tàng", response = List.class)
	@PostMapping(value = "/findList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody QlnvKhoTangSearchReq simpleSearchReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(simpleSearchReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(simpleSearchReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvKhoTang> qhKho = qlnvKhoTangRepository.selectParams(simpleSearchReq.getMaKho(),
					simpleSearchReq.getTrangThai(), pageable);

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

	@ApiOperation(value = "Cập nhật kho tàng", response = List.class)
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request, @RequestBody QlnvKhoTangReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");
			Calendar cal = Calendar.getInstance();
			Optional<QlnvKhoTang> QlnvKhoTang = qlnvKhoTangRepository.findById(Long.valueOf(objReq.getId()));
			if (!QlnvKhoTang.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");
			// check bản ghi ko ở trạng thái duyệt thì ko dc thay đổi tình trạng hoạt động
			if (QlnvKhoTang.get().getTrangThai() != Contains.DUYET
					&& QlnvKhoTang.get().getTinhTrang() == Contains.HOAT_DONG) {
				throw new Exception(
						"Không cập nhật tình trạng hoạt động của Kho tàng khi trạng thái không phải là phê duyệt.");
			}
			QlnvKhoTang dataDTB = QlnvKhoTang.get();
			QlnvKhoTang dataMap = new ModelMapper().map(objReq, QlnvKhoTang.class);
			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			updateObjectToObject(dataDTB, dataMap);
			dataDTB.setNgaySua(cal.getTime());
			dataDTB.setNguoiSua(authentication.getName());
			qlnvKhoTangRepository.save(dataDTB);
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

	@ApiOperation(value = "Lấy chi tiết thông tin kho tàng", response = List.class)
	@GetMapping(value = "/chi-tiet/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvKhoTang> qOptional = qlnvKhoTangRepository.findById(Long.parseLong(ids));
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

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 kho tàng", response = List.class)
	@PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@Valid HttpServletRequest request, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvKhoTang> qHoach = qlnvKhoTangRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");
			qHoach.get().setTrangThai(stReq.getTrangThai());
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
			qlnvKhoTangRepository.save(qHoach.get());
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

	@ApiOperation(value = "Kích hoạt/ Tạm dừng kho tàng", response = List.class)
	@PostMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@RequestBody IdSearchReq id) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(id.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvKhoTang> kho = qlnvKhoTangRepository.findById(Long.valueOf(id.getId()));
			if (!kho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");
			QlnvKhoTang khoDb = kho.get();
			khoDb.setTinhTrang(
					khoDb.getTinhTrang().equals(Contains.HOAT_DONG) ? Contains.NGUNG_HOAT_DONG : Contains.HOAT_DONG);
			qlnvKhoTangRepository.save(khoDb);
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

	@ApiOperation(value = "Kiểm tra điểm kho theo mã", response = List.class)
	@PostMapping(value = "/find-ma-diem-kho", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailbycode(@Valid @RequestBody BaseRequest objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getStr()))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			QlnvDmDiemKho qOptional = qlnvDmDiemKhoRepository.findByMaDiemKho(objReq.getStr());
			if (qOptional == null)
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

	@ApiOperation(value = "Lấy danh sách thủ kho", response = List.class)
	@PostMapping(value = "/danh-sach-thu-kho", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody QlnvDmThukhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<QlnvDmThukho> data = qlnvDmThukhoRepository.selectParams(objReq.getMaThukho(), objReq.getTenThukho(),
					objReq.getTrangThai(), pageable);
			resp.setData(data);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy danh sách lịch sử giao kho", response = List.class)
	@PostMapping(value = "/danh-sach-lich-su", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> getListThuKhoByKho(@Valid @RequestBody QlnvKhoThuKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<QlnvKhoThuKho> data = qlnvKhoThuKhoRepository.selectParams(objReq.getMaKho(), objReq.getTrangThai(),
					pageable);
			resp.setData(data);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Lấy thông tin thủ kho theo mã", response = List.class)
	@PostMapping(value = "/find-ma-thu-kho", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> getThuKhoByMa(@Valid @RequestBody BaseRequest objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getStr()))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			QlnvDmThukho qOptional = qlnvDmThukhoRepository.findByMaThukho(objReq.getStr());
			if (qOptional == null)
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

	@ApiOperation(value = "Giao kho cho thủ kho", response = List.class)
	@PostMapping(value = "/giao-kho", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> giaokho(@Valid HttpServletRequest request, @RequestBody QlnvKhoThuKhoReq objReq) {
		Resp resp = new Resp();
		Calendar cal = Calendar.getInstance();
		try {
			QlnvKhoThuKho dataMap = new ModelMapper().map(objReq, QlnvKhoThuKho.class);
			QlnvKhoTang qOptional = qlnvKhoTangRepository.findByMaKho(objReq.getMaKho());
			if (qOptional == null)
				throw new UnsupportedOperationException("Không tồn tại Kho với mã " + objReq.getMaKho());

			Authentication authentication = TokenAuthenticationService.getAuthentication(request);
			dataMap.setNgayTao(cal.getTime());
			dataMap.setTrangThai(Contains.HOAT_DONG);
			dataMap.setNguoiTao(authentication.getName());
			qlnvKhoThuKhoRepository.save(dataMap);
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