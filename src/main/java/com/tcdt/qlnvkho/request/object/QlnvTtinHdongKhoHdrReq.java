package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvkho.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinHdongKhoHdrReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số HĐ không được vượt quá 50 ký tự")
	String soHdong;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
	String soQdinh;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayKy;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayHluc;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayHetHluc;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số hiệu không được vượt quá 50 ký tự")
	String soHieu;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên HĐ không được vượt quá 250 ký tự")
	String tenHdong;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Đơn vị thực hiện không được vượt quá 250 ký tự")
	String dviThien;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "MST đơn vị Thực hiện không được vượt quá 20 ký tự")
	String mstDvi;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Người đại diện không được vượt quá 50 ký tự")
	String nguoiDaiDien;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "SDT không được vượt quá 20 ký tự")
	String soDthoai;
	
	@NotNull(message = "Không được để trống")
	BigDecimal giaTriHdong;
	
	@NotNull(message = "Không được để trống")
	BigDecimal vat;
	
	@Size(max = 2, message = "Trạng thái hợp đồng không được vượt quá 2 ký tự")
	String tthaiHdong;
	
	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Trạng thái hợp đồng không được vượt quá 50 ký tự")
	String maDvi;
	
	private List<QlnvTtinHdongKhoDtl1Req> detail1;
	private List<QlnvTtinHdongKhoDtl2Req> detail2;
}
