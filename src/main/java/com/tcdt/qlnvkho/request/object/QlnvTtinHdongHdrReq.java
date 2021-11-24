package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinHdongHdrReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số HĐ không được vượt quá 50 ký tự")
	String soHdong;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
	String soQdinh;
	@NotNull(message = "Không được để trống")
	Date ngayKy;
	@NotNull(message = "Không được để trống")
	Date ngayHluc;
	@NotNull(message = "Không được để trống")
	Date ngayHetHluc;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số hiệu không được vượt quá 50 ký tự")
	String soHieu;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên HĐ không được vượt quá 250 ký tự")
	String tenHdong;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Chủ đầu tư không được vượt quá 250 ký tự")
	String chuDtu;
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
	
	private List<QlnvTtinHdongDtl1Req> detail1;
	private List<QlnvTtinHdongDtl2Req> detail2;
}
