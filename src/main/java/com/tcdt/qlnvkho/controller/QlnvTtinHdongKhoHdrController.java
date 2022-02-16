package com.tcdt.qlnvkho.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.tcdt.qlnvkho.repository.QlnvTtinHdongKhoHdrRepository;
import com.tcdt.qlnvkho.request.IdSearchReq;
import com.tcdt.qlnvkho.request.object.QlnvTtinHdongKhoHdrReq;
import com.tcdt.qlnvkho.request.object.StatusReq;
import com.tcdt.qlnvkho.request.search.QlnvTtinHdongKhoHdrSearchReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.secification.QlnvTtinHdongKhoHdrSpecification;
import com.tcdt.qlnvkho.table.QlnvTtinHdongKhoDtl1;
import com.tcdt.qlnvkho.table.QlnvTtinHdongKhoDtl2;
import com.tcdt.qlnvkho.table.QlnvTtinHdongKhoHdr;
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
@RequestMapping(value = PathContains.QL_TT_HDONG_KHO)
@Api(tags = "Thông tin hợp đồng kho")
public class QlnvTtinHdongKhoHdrController extends BaseController {

	@Autowired
	private QlnvTtinHdongKhoHdrRepository qlnvTtinHdongKhoHdrRepository;
	
	@ApiOperation(value = "Tạo mới Thông tin hợp đồng kho", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resp> insert(@Valid HttpServletRequest request, @RequestBody QlnvTtinHdongKhoHdrReq objReq) {
		Resp resp = new Resp();
		try {
			
			QlnvTtinHdongKhoHdr dataMap = ObjectMapperUtils.map(objReq, QlnvTtinHdongKhoHdr.class);
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));

			List<QlnvTtinHdongKhoDtl1> dtls1 = ObjectMapperUtils.mapAll(objReq.getDetail1(), QlnvTtinHdongKhoDtl1.class);
			dataMap.setDetailList(dtls1);
			List<QlnvTtinHdongKhoDtl2> dtls2 = ObjectMapperUtils.mapAll(objReq.getDetail2(), QlnvTtinHdongKhoDtl2.class);
			dataMap.setDetailListPl(dtls2);
			
			qlnvTtinHdongKhoHdrRepository.save(dataMap);
			resp.setData(dataMap);
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
	
	@ApiOperation(value = "Xoá Thông tin hợp đồng kho", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> delete(@RequestBody IdSearchReq idSearchReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");
			Optional<QlnvTtinHdongKhoHdr> optional = qlnvTtinHdongKhoHdrRepository.findById(idSearchReq.getId());
			if (!optional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");
			
			qlnvTtinHdongKhoHdrRepository.delete(optional.get());
			
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
	
	@ApiOperation(value = "Tra cứu Thông tin hợp đồng kho", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> selectAll(@RequestBody QlnvTtinHdongKhoHdrSearchReq objReq) {
		Resp resp = new Resp();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

			Page<QlnvTtinHdongKhoHdr> qhKho = qlnvTtinHdongKhoHdrRepository.findAll(QlnvTtinHdongKhoHdrSpecification.buildSearchQuery(objReq), pageable);

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

	@ApiOperation(value = "Cập nhật Thông tin hợp đồng kho", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> update(@Valid HttpServletRequest request,@RequestBody QlnvTtinHdongKhoHdrReq objReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvTtinHdongKhoHdr> optional = qlnvTtinHdongKhoHdrRepository.findById(Long.valueOf(objReq.getId()));
			if (!optional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			QlnvTtinHdongKhoHdr dataDB = optional.get();
			QlnvTtinHdongKhoHdr dataMap = ObjectMapperUtils.map(objReq, QlnvTtinHdongKhoHdr.class);

			updateObjectToObject(dataDB, dataMap);
			dataDB.setNgaySua(getDateTimeNow());
			dataDB.setNguoiSua(getUserName(request));

			List<QlnvTtinHdongKhoDtl1> dtls1 = ObjectMapperUtils.mapAll(objReq.getDetail1(), QlnvTtinHdongKhoDtl1.class);
			dataDB.setDetailList(dtls1);
			List<QlnvTtinHdongKhoDtl2> dtls2 = ObjectMapperUtils.mapAll(objReq.getDetail2(), QlnvTtinHdongKhoDtl2.class);
			dataDB.setDetailListPl(dtls2);

			qlnvTtinHdongKhoHdrRepository.save(dataDB);
			
			resp.setData(dataDB);
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
	
	@ApiOperation(value = "Lấy chi tiết thông tin Thông tin hợp đồng kho", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> detail(
			@ApiParam(value = "ID Thông tin đấu thầu", example = "1", required = true) @PathVariable("ids") String ids) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			Optional<QlnvTtinHdongKhoHdr> qOptional = qlnvTtinHdongKhoHdrRepository.findById(Long.parseLong(ids));
			if (!qOptional.isPresent())
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			resp.setData(qOptional.get());
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}
	
	
	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03/Xoá-04 Thông tin hợp đồng kho", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resp> updateStatus(@Valid HttpServletRequest request, @RequestBody StatusReq stReq) {
		Resp resp = new Resp();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvTtinHdongKhoHdr> optional = qlnvTtinHdongKhoHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!optional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");
			
			optional.get().setTrangThai(stReq.getTrangThai());
			String status = stReq.getTrangThai();
			switch (status) {
			case Contains.CHO_DUYET:
				optional.get().setNguoiGuiDuyet(getUserName(request));
				optional.get().setNgayGuiDuyet(getDateTimeNow());
				break;
			case Contains.DUYET:
				optional.get().setNguoiPduyet(getUserName(request));
				optional.get().setNgayPduyet(getDateTimeNow());
				break;
			case Contains.TU_CHOI:
				optional.get().setLdoTuchoi(stReq.getLyDo());
				break;
			default:
				break;
			}
			qlnvTtinHdongKhoHdrRepository.save(optional.get());
			
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
