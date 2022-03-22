package com.tcdt.qlnvkho.service.feign;

import com.tcdt.qlnvkho.request.BaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import feign.Headers;

@FeignClient(name = "qlnv-category")
public interface CategoryServiceProxy {

	@GetMapping("/dmuc-donvi/chi-tiet/{ids}")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDetail(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@PathVariable("ids") String ids);

	@GetMapping("/dm-hang/danh-sach/ma-cha")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> recursive(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@PathVariable("maCha") String maCha);
	@PostMapping("/dmuc-donvi/chi-tiet")
	@Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
	public ResponseEntity<String> getDetailByCode(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@RequestBody BaseRequest objReq);
}
