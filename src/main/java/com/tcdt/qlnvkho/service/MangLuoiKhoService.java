package com.tcdt.qlnvkho.service;
import com.tcdt.qlnvkho.repository.khotang.*;
import com.tcdt.qlnvkho.table.khotang.KtDtqgkv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MangLuoiKhoService{
	@Autowired
	KtDiemKhoRepository ktDiemKhoRepository;
	@Autowired
	KtDtqgkvRepository ktDtqgkvRepository;
	@Autowired
	KtNganLoRepository ktNganLoRepository;
	@Autowired
	KtNhaKhoRepository ktNhaKhoRepository;
	@Autowired
	KtTongKhoRepository ktTongKhoRepository;
	@Autowired
	KtNganKhoRepository ktNganKhoRepository;

	public Iterable<KtDtqgkv> getCucList(){
		return ktDtqgkvRepository.findAll();
	}


}