package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvQhoachKhoDtlReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long idQhHdr;
	@NotNull(message = "Không được để trống")
	@Size(max = 25, message = "Mã đơn vị không được vượt quá 25 ký tự")
	String maDvi;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Địa điểm không được vượt quá 20 ký tự")
	String diaDiem;
	@NotNull(message = "Không được để trống")
	String hinhThuc;
	@NotNull(message = "Không được để trống")
	@Size(max = 25, message = "Loại hình kho không được vượt quá 25 ký tự")
	String maLhKho;
	@NotNull(message = "Không được để trống")
	BigDecimal tongDtich;
	@NotNull(message = "Không được để trống")
	BigDecimal tongDutoan;
	String noiDung;
}
