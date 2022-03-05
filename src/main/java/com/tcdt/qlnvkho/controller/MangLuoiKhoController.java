package com.tcdt.qlnvkho.controller;

import com.tcdt.qlnvkho.enums.EnumResponse;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.khotang.*;
import com.tcdt.qlnvkho.request.search.QlnvDuAnSearchReq;
import com.tcdt.qlnvkho.request.search.khotang.*;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.service.MangLuoiKhoService;
import com.tcdt.qlnvkho.table.QlnvDuAn;
import com.tcdt.qlnvkho.table.khotang.*;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.ObjectMapperUtils;
import com.tcdt.qlnvkho.util.PaginationSet;
import com.tcdt.qlnvkho.util.PathContains;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = PathContains.KT_MANG_LUOI_KHO)
@Slf4j
@Api(tags = "Tình trạng kho hiện thời")
public class MangLuoiKhoController extends BaseController {

	@Autowired
	private MangLuoiKhoService mangLuoiKhoService;

	/**
	 * Phan Anh
	 * @return
	 */
	@ApiOperation(value = "Lấy cây mạng lưới kho", response = List.class)
	@PostMapping(value = PathContains.URL_THONG_TIN, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> getDtqgkv() {
		Resp resp = new Resp();
		try {

			Iterable<KtDtqgkv> data = mangLuoiKhoService.getCucList();
			MLK mlk = new MLK();
			mlk.setId(Long.parseLong("1"));
			mlk.setMaDtqgkv("0");
			mlk.setTenDtqgkv("Tổng Cục Dự Trữ Nhà Nước");
			mlk.setChild(StreamSupport.stream(data.spliterator(), false)
					.collect(Collectors.toList()));

			resp.setData(mlk);
			resp.setStatusCode(Contains.RESP_SUCC);
			resp.setMsg("Thành công");
		} catch (Exception e) {
			resp.setStatusCode(Contains.RESP_FAIL);
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Tạo mới đơn vị dữ trữ quốc gia khu vực (Cục)", response = List.class)
	@PostMapping(value = PathContains.KT_CUC + PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> createCuc(HttpServletRequest req,
											   @Valid @RequestBody KtDtqgkvReq objReq) {
		Resp resp = new Resp();
		try {
			// Add thong tin
			KtDtqgkv dataMap = ObjectMapperUtils.map(objReq, KtDtqgkv.class);

			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			dataMap.setNgayTao(getDateTimeNow());

			KtDtqgkv createCheck = mangLuoiKhoService.saveKtDtqgkv(dataMap);

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

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Cập nhật đơn vị dữ trữ quốc gia khu vực (Cục)", response = List.class)
	@PostMapping(value = PathContains.KT_CUC + PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateCuc(HttpServletRequest req,
											   @Valid @RequestBody KtDtqgkvReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<KtDtqgkv> qOptional = mangLuoiKhoService
					.findKtDtqgkvbyId(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			KtDtqgkv dataDB = qOptional.get();
			KtDtqgkv dataMap = ObjectMapperUtils.map(objReq, KtDtqgkv.class);

			updateObjectToObject(dataDB, dataMap);

			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(req));

			KtDtqgkv createCheck = mangLuoiKhoService.saveKtDtqgkv(dataDB);

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

	/**
	 * Phan Anh
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "Lấy chi tiết thông tin đơn vị dữ trữ quốc gia khu vực (Cục)", response = List.class)
	@GetMapping(value = PathContains.KT_CUC + PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailCuc(
			@ApiParam(value = "ID bản ghi", example = "54", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<KtDtqgkv> qOptional = mangLuoiKhoService.findKtDtqgkvbyId(Long.parseLong(ids));
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
	@ApiOperation(value = "Xoá thông tin thông tin đơn vị dữ trữ quốc gia khu vực (Cục)", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteCuc(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long id = idSearchReq.getId();
			Optional<KtDtqgkv> ktDtqgkv = mangLuoiKhoService.findKtDtqgkvbyId(id);
			if (!ktDtqgkv.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			mangLuoiKhoService.delKtDtqgkvbyId(id);
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

	/**
	 * Chi cục ======================================================================================
	 */

	@ApiOperation(value = "Tìm kiếm danh sách Chi cục", response = List.class)
	@PostMapping(value = PathContains.KT_CHI_CUC + PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAllChiCuc(@Valid @RequestBody KtTongKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<KtTongKho> qhKho = mangLuoiKhoService.selectTongKhoParams(objReq.getMaTongKho(), objReq.getTenTongKho(),objReq.getDtqgkvId(), pageable);

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


	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Tạo mới đơn vị dữ trữ quốc gia khu vực (Chi cục)", response = List.class)
	@PostMapping(value = PathContains.KT_CHI_CUC + PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> createChiCuc(HttpServletRequest req,
									   @Valid @RequestBody KtTongKhoReq objReq) {
		Resp resp = new Resp();
		try {
			// Add thong tin
			KtDtqgkv dataMap = ObjectMapperUtils.map(objReq, KtDtqgkv.class);

			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			dataMap.setNgayTao(getDateTimeNow());

			KtDtqgkv createCheck = mangLuoiKhoService.saveKtDtqgkv(dataMap);

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

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Cập nhật đơn vị dữ trữ quốc gia khu vực (Chi cục)", response = List.class)
	@PostMapping(value = PathContains.KT_CHI_CUC + PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateChiCuc(HttpServletRequest req,
									   @Valid @RequestBody KtTongKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<KtTongKho> qOptional = mangLuoiKhoService
					.findKtTongKhobyId(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			KtTongKho dataDB = qOptional.get();
			KtTongKho dataMap = ObjectMapperUtils.map(objReq, KtTongKho.class);

			updateObjectToObject(dataDB, dataMap);

			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(req));

			KtTongKho createCheck = mangLuoiKhoService.saveKtTongKho(dataDB);

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

	/**
	 * Phan Anh
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "Lấy chi tiết thông tin đơn vị dữ trữ quốc gia khu vực (Chi Cục)", response = List.class)
	@GetMapping(value = PathContains.KT_CHI_CUC + PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailChiCuc(
			@ApiParam(value = "ID bản ghi", example = "54", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<KtTongKho> qOptional = mangLuoiKhoService.findKtTongKhobyId(Long.parseLong(ids));
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
	@ApiOperation(value = "Xoá thông tin thông tin đơn vị dữ trữ quốc gia khu vực (Chi Cục)", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.KT_CHI_CUC + PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteChiCuc(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long id = idSearchReq.getId();
			Optional<KtTongKho> ktTongKho = mangLuoiKhoService.findKtTongKhobyId(id);
			if (!ktTongKho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			mangLuoiKhoService.delKtTongKhobyId(id);
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


	/**
	 * Diem kho ======================================================================================
	 */

	@ApiOperation(value = "Tìm kiếm danh sách Diem kho", response = List.class)
	@PostMapping(value = PathContains.KT_DIEM_KHO + PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAllDiemKho(@Valid @RequestBody KtDiemKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<KtDiemKho> qhKho = mangLuoiKhoService.selectDiemKhoParams(objReq.getMaDiemKho(), objReq.getTenDiemKho(),objReq.getTongKhoId(), pageable);

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


	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Tạo mới đơn vị dữ trữ quốc gia khu vực (Diem kho)", response = List.class)
	@PostMapping(value = PathContains.KT_DIEM_KHO + PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> createDiemKho(HttpServletRequest req,
											  @Valid @RequestBody KtDiemKhoReq objReq) {
		Resp resp = new Resp();
		try {
			// Add thong tin
			KtDiemKho dataMap = ObjectMapperUtils.map(objReq, KtDiemKho.class);

			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			dataMap.setNgayTao(getDateTimeNow());

			KtDiemKho createCheck = mangLuoiKhoService.saveKtDiemKho(dataMap);

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

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Cập nhật đơn vị dữ trữ quốc gia khu vực (Diem kho)", response = List.class)
	@PostMapping(value = PathContains.KT_DIEM_KHO + PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateDiemKho(HttpServletRequest req,
											  @Valid @RequestBody KtDiemKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<KtDiemKho> qOptional = mangLuoiKhoService
					.findKtDiemKhobyId(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			KtDiemKho dataDB = qOptional.get();
			KtDiemKho dataMap = ObjectMapperUtils.map(objReq, KtDiemKho.class);

			updateObjectToObject(dataDB, dataMap);

			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(req));

			KtDiemKho createCheck = mangLuoiKhoService.saveKtDiemKho(dataDB);

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

	/**
	 * Phan Anh
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "Lấy chi tiết thông tin đơn vị dữ trữ quốc gia khu vực (Diem kho)", response = List.class)
	@GetMapping(value = PathContains.KT_DIEM_KHO + PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailDiemKho(
			@ApiParam(value = "ID bản ghi", example = "54", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<KtDiemKho> qOptional = mangLuoiKhoService.findKtDiemKhobyId(Long.parseLong(ids));
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
	@ApiOperation(value = "Xoá thông tin thông tin đơn vị dữ trữ quốc gia khu vực (Diem kho)", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.KT_DIEM_KHO + PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteDiemKho(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long id = idSearchReq.getId();
			Optional<KtDiemKho> KtDiemKho = mangLuoiKhoService.findKtDiemKhobyId(id);
			if (!KtDiemKho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			mangLuoiKhoService.delKtDiemKhobyId(id);
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

	/**
	 * Nha kho ======================================================================================
	 */

	@ApiOperation(value = "Tìm kiếm danh sách Nha kho", response = List.class)
	@PostMapping(value = PathContains.KT_NHA_KHO + PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAllNhaKho(@Valid @RequestBody KtNhaKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<KtNhaKho> qhKho = mangLuoiKhoService.selectNhaKhoParams(objReq.getMaNhaKho(), objReq.getTenNhaKho(),objReq.getDiemKhoId(), pageable);

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


	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Tạo mới đơn vị dữ trữ quốc gia khu vực (Nha kho)", response = List.class)
	@PostMapping(value = PathContains.KT_NHA_KHO + PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> createNhaKho(HttpServletRequest req,
											 @Valid @RequestBody KtNhaKhoReq objReq) {
		Resp resp = new Resp();
		try {
			// Add thong tin
			KtNhaKho dataMap = ObjectMapperUtils.map(objReq, KtNhaKho.class);

			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			dataMap.setNgayTao(getDateTimeNow());

			KtNhaKho createCheck = mangLuoiKhoService.saveKtNhaKho(dataMap);

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

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Cập nhật đơn vị dữ trữ quốc gia khu vực (Nha kho)", response = List.class)
	@PostMapping(value = PathContains.KT_NHA_KHO + PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateNhaKho(HttpServletRequest req,
											 @Valid @RequestBody KtNhaKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<KtNhaKho> qOptional = mangLuoiKhoService
					.findKtNhaKhobyId(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			KtNhaKho dataDB = qOptional.get();
			KtNhaKho dataMap = ObjectMapperUtils.map(objReq, KtNhaKho.class);

			updateObjectToObject(dataDB, dataMap);

			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(req));

			KtNhaKho createCheck = mangLuoiKhoService.saveKtNhaKho(dataDB);

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

	/**
	 * Phan Anh
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "Lấy chi tiết thông tin đơn vị dữ trữ quốc gia khu vực (Nha kho)", response = List.class)
	@GetMapping(value = PathContains.KT_NHA_KHO + PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailNhaKho(
			@ApiParam(value = "ID bản ghi", example = "54", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<KtNhaKho> qOptional = mangLuoiKhoService.findKtNhaKhobyId(Long.parseLong(ids));
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
	@ApiOperation(value = "Xoá thông tin thông tin đơn vị dữ trữ quốc gia khu vực (Nha kho)", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.KT_NHA_KHO + PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteNhaKho(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long id = idSearchReq.getId();
			Optional<KtNhaKho> KtNhaKho = mangLuoiKhoService.findKtNhaKhobyId(id);
			if (!KtNhaKho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			mangLuoiKhoService.delKtNhaKhobyId(id);
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

	/**
	 * Ngan kho ======================================================================================
	 */

	@ApiOperation(value = "Tìm kiếm danh sách Ngan kho", response = List.class)
	@PostMapping(value = PathContains.KT_NGAN_KHO + PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAllNganKho(@Valid @RequestBody KtNganKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<KtNganKho> qhKho = mangLuoiKhoService.selectNganKhoParams(objReq.getMaNganKho(), objReq.getTenNganKho(),objReq.getNhaKhoId(), pageable);

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


	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Tạo mới đơn vị dữ trữ quốc gia khu vực (Ngan kho)", response = List.class)
	@PostMapping(value = PathContains.KT_NGAN_KHO + PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> createNganKho(HttpServletRequest req,
											  @Valid @RequestBody KtNganKhoReq objReq) {
		Resp resp = new Resp();
		try {
			// Add thong tin
			KtNganKho dataMap = ObjectMapperUtils.map(objReq, KtNganKho.class);

			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			dataMap.setNgayTao(getDateTimeNow());

			KtNganKho createCheck = mangLuoiKhoService.saveKtNganKho(dataMap);

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

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Cập nhật đơn vị dữ trữ quốc gia khu vực (Ngan kho)", response = List.class)
	@PostMapping(value = PathContains.KT_NGAN_KHO + PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateNganKho(HttpServletRequest req,
											  @Valid @RequestBody KtNganKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<KtNganKho> qOptional = mangLuoiKhoService
					.findKtNganKhobyId(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			KtNganKho dataDB = qOptional.get();
			KtNganKho dataMap = ObjectMapperUtils.map(objReq, KtNganKho.class);

			updateObjectToObject(dataDB, dataMap);

			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(req));

			KtNganKho createCheck = mangLuoiKhoService.saveKtNganKho(dataDB);

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

	/**
	 * Phan Anh
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "Lấy chi tiết thông tin đơn vị dữ trữ quốc gia khu vực (Ngan kho)", response = List.class)
	@GetMapping(value = PathContains.KT_NGAN_KHO + PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailNganKho(
			@ApiParam(value = "ID bản ghi", example = "54", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<KtNganKho> qOptional = mangLuoiKhoService.findKtNganKhobyId(Long.parseLong(ids));
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
	@ApiOperation(value = "Xoá thông tin thông tin đơn vị dữ trữ quốc gia khu vực (Ngan kho)", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.KT_NGAN_KHO + PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteNganKho(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long id = idSearchReq.getId();
			Optional<KtNganKho> KtNganKho = mangLuoiKhoService.findKtNganKhobyId(id);
			if (!KtNganKho.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			mangLuoiKhoService.delKtNganKhobyId(id);
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
	/**
	 * Ngan lo ======================================================================================
	 */

	@ApiOperation(value = "Tìm kiếm danh sách Ngan lo", response = List.class)
	@PostMapping(value = PathContains.KT_NGAN_LO + PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAllNganLo(@Valid @RequestBody KtNganLoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<KtNganLo> qhLo = mangLuoiKhoService.selectNganLoParams(objReq.getMaNganLo(), objReq.getTenNganLo(),objReq.getNganKhoId(), pageable);

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
			resp.setData(qhLo);
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}


	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Tạo mới đơn vị dữ trữ quốc gia khu vực (Ngan lo)", response = List.class)
	@PostMapping(value = PathContains.KT_NGAN_LO + PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> createNganLo(HttpServletRequest req,
											 @Valid @RequestBody KtNganLoReq objReq) {
		Resp resp = new Resp();
		try {
			// Add thong tin
			KtNganLo dataMap = ObjectMapperUtils.map(objReq, KtNganLo.class);

			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(req));
			dataMap.setNgayTao(getDateTimeNow());

			KtNganLo createCheck = mangLuoiKhoService.saveKtNganLo(dataMap);

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

	/**
	 * Phan Anh
	 * @param req
	 * @param objReq
	 * @return
	 */
	@ApiOperation(value = "Cập nhật đơn vị dữ trữ quốc gia khu vực (Ngan lo)", response = List.class)
	@PostMapping(value = PathContains.KT_NGAN_LO + PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateNganLo(HttpServletRequest req,
											 @Valid @RequestBody KtNganLoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<KtNganLo> qOptional = mangLuoiKhoService
					.findKtNganLobyId(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			KtNganLo dataDB = qOptional.get();
			KtNganLo dataMap = ObjectMapperUtils.map(objReq, KtNganLo.class);

			updateObjectToObject(dataDB, dataMap);

			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(req));

			KtNganLo createCheck = mangLuoiKhoService.saveKtNganLo(dataDB);

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

	/**
	 * Phan Anh
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "Lấy chi tiết thông tin đơn vị dữ trữ quốc gia khu vực (Ngan lo)", response = List.class)
	@GetMapping(value = PathContains.KT_NGAN_LO + PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailNganLo(
			@ApiParam(value = "ID bản ghi", example = "54", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<KtNganLo> qOptional = mangLuoiKhoService.findKtNganLobyId(Long.parseLong(ids));
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
	@ApiOperation(value = "Xoá thông tin thông tin đơn vị dữ trữ quốc gia khu vực (Ngan lo)", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.KT_NGAN_LO + PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteNganLo(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Long id = idSearchReq.getId();
			Optional<KtNganLo> KtNganLo = mangLuoiKhoService.findKtNganLobyId(id);
			if (!KtNganLo.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			mangLuoiKhoService.delKtNganLobyId(id);
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