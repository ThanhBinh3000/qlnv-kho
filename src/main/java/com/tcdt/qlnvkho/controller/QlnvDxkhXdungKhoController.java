package com.tcdt.qlnvkho.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkho.enums.EnumResponse;
import com.tcdt.qlnvkho.repository.QlnvDxkhXdungKhoRepository;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvDxkhXdungKhoReq;
import com.tcdt.qlnvkho.request.search.QlnvDxkhXdungKhoSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.secification.QlnvDxkhXdungKhoSpecification;
import com.tcdt.qlnvkho.table.FileDKemJoinDxkhXdungKho;
import com.tcdt.qlnvkho.table.QlnvDxkhXdungKho;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.ObjectMapperUtils;
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
@RequestMapping(value = PathContains.QL_DX_XD_KHO)
@Api(tags = "Đề xuất kế hoạch xây dựng Kho tàng trung hạn")
public class QlnvDxkhXdungKhoController extends BaseController {
	@Autowired
	private QlnvDxkhXdungKhoRepository qlnvDxkhXdungKhoRepository;

	@ApiOperation(value = "Tạo mới đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest request, @RequestBody QlnvDxkhXdungKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (objReq.getLoaiDxuat() == null || !Contains.mpLoaiDX.containsKey(objReq.getLoaiDxuat()))
				throw new Exception("Loại đề xuất không phù hợp");

			List<FileDKemJoinDxkhXdungKho> fileDinhKemList = new ArrayList<FileDKemJoinDxkhXdungKho>();
			if (objReq.getFileDinhKems() != null) {
				fileDinhKemList = ObjectMapperUtils.mapAll(objReq.getFileDinhKems(), FileDKemJoinDxkhXdungKho.class);
				fileDinhKemList.forEach(f -> {
					f.setDataType(QlnvDxkhXdungKho.TABLE_NAME);
					f.setCreateDate(new Date());
				});
			}

			QlnvDxkhXdungKho dataMap = new ModelMapper().map(objReq, QlnvDxkhXdungKho.class);
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));
			dataMap.setChildren(fileDinhKemList);

			QlnvDxkhXdungKho createCheck = qlnvDxkhXdungKhoRepository.save(dataMap);

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

	@ApiOperation(value = "Xoá thông tin đề xuất kế hoạch xây dựng kho tàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvDxkhXdungKho> qOptional = qlnvDxkhXdungKhoRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvDxkhXdungKhoRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody QlnvDxkhXdungKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			List<String> listDeXuat = new ArrayList<String>(Contains.mpLoaiDX.keySet());

			Page<QlnvDxkhXdungKho> qhKho = qlnvDxkhXdungKhoRepository
					.findAll(QlnvDxkhXdungKhoSpecification.buildSearchQuery(objReq, listDeXuat), pageable);

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

	@ApiOperation(value = "Cập nhật đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request, @RequestBody QlnvDxkhXdungKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvDxkhXdungKho> qOptional = qlnvDxkhXdungKhoRepository.findById(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			if (objReq.getLoaiDxuat() == null || !Contains.mpLoaiDX.containsKey(objReq.getLoaiDxuat()))
				throw new Exception("Loại đề xuất không phù hợp");

			List<FileDKemJoinDxkhXdungKho> fileDinhKemList = new ArrayList<FileDKemJoinDxkhXdungKho>();
			if (objReq.getFileDinhKems() != null) {
				fileDinhKemList = ObjectMapperUtils.mapAll(objReq.getFileDinhKems(), FileDKemJoinDxkhXdungKho.class);
				fileDinhKemList.forEach(f -> {
					f.setDataType(QlnvDxkhXdungKho.TABLE_NAME);
					f.setCreateDate(new Date());
				});
			}

			QlnvDxkhXdungKho dataDTB = qOptional.get();
			QlnvDxkhXdungKho dataMap = ObjectMapperUtils.map(objReq, QlnvDxkhXdungKho.class);

			updateObjectToObject(dataDTB, dataMap);

			dataDTB.setNgaySua(getDateTimeNow());
			dataDTB.setNguoiSua(getUserName(request));
			dataDTB.setChildren(fileDinhKemList);

			QlnvDxkhXdungKho createCheck = qlnvDxkhXdungKhoRepository.save(dataDTB);

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

	@ApiOperation(value = "Lấy chi tiết thông tin đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID đề xuất kế hoạch xây dựng kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			List<QlnvDxkhXdungKho> dxkhXdungKhos = qlnvDxkhXdungKhoRepository
					.findAll(QlnvDxkhXdungKhoSpecification.buildFindByIdQuery(Long.parseLong(ids)));

			if (dxkhXdungKhos.isEmpty())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			resp.setData(dxkhXdungKhos.get(0));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tạo mới tổng hợp đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI
			+ PathContains.URL_CAP_TONG_CUC, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insertCapTCuc(@Valid HttpServletRequest request,
			@RequestBody QlnvDxkhXdungKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (objReq.getLoaiDxuat() == null || !Contains.mpLoaiDxTh.containsKey(objReq.getLoaiDxuat()))
				throw new Exception("Loại đề xuất không phù hợp");

			List<FileDKemJoinDxkhXdungKho> fileDinhKemList = new ArrayList<FileDKemJoinDxkhXdungKho>();
			if (objReq.getFileDinhKems() != null) {
				fileDinhKemList = ObjectMapperUtils.mapAll(objReq.getFileDinhKems(), FileDKemJoinDxkhXdungKho.class);
				fileDinhKemList.forEach(f -> {
					f.setDataType(QlnvDxkhXdungKho.TABLE_NAME);
					f.setCreateDate(new Date());
				});
			}

			QlnvDxkhXdungKho dataMap = new ModelMapper().map(objReq, QlnvDxkhXdungKho.class);
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));
			dataMap.setChildren(fileDinhKemList);

			QlnvDxkhXdungKho createCheck = qlnvDxkhXdungKhoRepository.save(dataMap);

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

	@ApiOperation(value = "Cập nhật tổng hợp đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT
			+ PathContains.URL_CAP_TONG_CUC, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateCapTCuc(@Valid HttpServletRequest request,
			@RequestBody QlnvDxkhXdungKhoReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			if (objReq.getLoaiDxuat() == null || !Contains.mpLoaiDxTh.containsKey(objReq.getLoaiDxuat()))
				throw new Exception("Loại đề xuất không phù hợp");

			Optional<QlnvDxkhXdungKho> qOptional = qlnvDxkhXdungKhoRepository.findById(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			if (objReq.getLoaiDxuat() == null || !Contains.mpLoaiDX.containsKey(objReq.getLoaiDxuat()))
				throw new Exception("Loại đề xuất không phù hợp");

			List<FileDKemJoinDxkhXdungKho> fileDinhKemList = new ArrayList<FileDKemJoinDxkhXdungKho>();
			if (objReq.getFileDinhKems() != null) {
				fileDinhKemList = ObjectMapperUtils.mapAll(objReq.getFileDinhKems(), FileDKemJoinDxkhXdungKho.class);
				fileDinhKemList.forEach(f -> {
					f.setDataType(QlnvDxkhXdungKho.TABLE_NAME);
					f.setCreateDate(new Date());
				});
			}

			QlnvDxkhXdungKho dataDTB = qOptional.get();
			QlnvDxkhXdungKho dataMap = ObjectMapperUtils.map(objReq, QlnvDxkhXdungKho.class);

			updateObjectToObject(dataDTB, dataMap);

			dataDTB.setNgaySua(getDateTimeNow());
			dataDTB.setNguoiSua(getUserName(request));
			dataDTB.setChildren(fileDinhKemList);

			QlnvDxkhXdungKho createCheck = qlnvDxkhXdungKhoRepository.save(dataDTB);

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

	@ApiOperation(value = "Lấy chi tiết thông tin tổng hợp đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + PathContains.URL_CAP_TONG_CUC
			+ "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detailCapTCuc(
			@ApiParam(value = "ID tổng hợp đề xuất kế hoạch xây dựng kho tàng", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			List<QlnvDxkhXdungKho> dxkhXdungKhos = qlnvDxkhXdungKhoRepository
					.findAll(QlnvDxkhXdungKhoSpecification.buildFindByIdQuery(Long.parseLong(ids)));

			if (dxkhXdungKhos.isEmpty())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			resp.setData(dxkhXdungKhos.get(0));
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Xoá thông tin tổng hợp đề xuất kế hoạch xây dựng kho tàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA
			+ PathContains.URL_CAP_TONG_CUC, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> deleteCapTCuc(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvDxkhXdungKho> qOptional = qlnvDxkhXdungKhoRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvDxkhXdungKhoRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu tổng hợp đề xuất kế hoạch xây dựng kho tàng", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU
			+ PathContains.URL_CAP_TONG_CUC, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAllCapTCuc(@RequestBody QlnvDxkhXdungKhoSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			List<String> listDeXuat = new ArrayList<String>(Contains.mpLoaiDxTh.keySet());

			Page<QlnvDxkhXdungKho> qhKho = qlnvDxkhXdungKhoRepository
					.findAll(QlnvDxkhXdungKhoSpecification.buildSearchQuery(objReq, listDeXuat), pageable);

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
}