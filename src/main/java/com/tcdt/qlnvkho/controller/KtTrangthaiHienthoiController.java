package com.tcdt.qlnvkho.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvkho.entities.KtTrangthaiHienthoiEntity;
import com.tcdt.qlnvkho.repository.KtTrangthaiHienthoiRepository;
import com.tcdt.qlnvkho.request.object.KtTrangthaiHienthoiReq;
import com.tcdt.qlnvkho.response.Resp;
import com.tcdt.qlnvkho.util.Contains;
import com.tcdt.qlnvkho.util.PathContains;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = PathContains.KT_TINHTRANG_HIENTHOI)
@Slf4j
@Api(tags = "Tình trạng kho hiện thời")
public class KtTrangthaiHienthoiController extends BaseController {

	@Autowired
	private KtTrangthaiHienthoiRepository ktTrangthaiHienthoiRepository;

	@ApiOperation(value = "Lấy danh sách tồn kho đầu năm", response = List.class)
	@PostMapping(value = PathContains.URL_THONG_TIN, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resp> colection(@Valid @RequestBody KtTrangthaiHienthoiReq objReq) {
		Resp resp = new Resp();
		try {

			Iterable<KtTrangthaiHienthoiEntity> data = ktTrangthaiHienthoiRepository.selectParams(objReq.getMaDvi(),
					objReq.getMaVthhList());
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