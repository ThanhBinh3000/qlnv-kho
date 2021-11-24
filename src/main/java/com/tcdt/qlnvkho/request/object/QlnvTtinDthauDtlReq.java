package com.tcdt.qlnvkho.request.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvTtinDthauDtlReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	Long ttinDthauId;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Số hiệu không được vượt quá 20 ký tự")
	String soHieu;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tên phần thầu không được vượt quá 50 ký tự")
	String tenPhanThau;
	@Size(max = 250, message = "Hạng mục không được vượt quá 250 ký tự")
	String hangMuc;
	@NotNull(message = "Không được để trống")
	BigDecimal soLuong;
	@NotNull(message = "Không được để trống")
	BigDecimal donGia;
	BigDecimal giaPhanThau;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Hình thức ĐT không được vượt quá 2 ký tự")
	String hthucDthau;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Phương thức ĐT không được vượt quá 2 ký tự")
	String pthucDthau;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Hình thức HĐ không được vượt quá 2 ký tự")
	String hthucHdong;
	@NotNull(message = "Không được để trống")
	Integer tgianThien;
	Date ngayLcntTu;
	Date ngayLcntDen;
	
	private List<QlnvTtinDthauDtlCtietReq> detail;
	
	
}
