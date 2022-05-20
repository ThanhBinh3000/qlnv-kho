package com.tcdt.qlnvkho.service;
import com.tcdt.qlnvkho.repository.khotang.*;
import com.tcdt.qlnvkho.table.khotang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

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

	public KtDtqgkv saveKtDtqgkv(KtDtqgkv ktDtqgkv){
		return ktDtqgkvRepository.save(ktDtqgkv);
	}

	public Optional<KtDtqgkv> findKtDtqgkvbyId(Long id){
		return ktDtqgkvRepository.findById(id);
	}

	public void delKtDtqgkvbyId(Long id){
		ktDtqgkvRepository.deleteById(id);
	}

	/**
	 * Chi Cuc
	 * @param ma
	 * @param ten
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<KtTongKho> selectTongKhoParams(String ma, String ten, Long id, Pageable pageable){
		return ktTongKhoRepository.selectParams(ma,ten,id,pageable);
	}

	public Optional<KtTongKho> findKtTongKhobyId(String id){
		if (StringUtils.isEmpty(id)){
			throw new UnsupportedOperationException("Không tồn tại bản ghi");
		}
		Optional<KtTongKho> qOptional = ktTongKhoRepository.findById(Long.parseLong(id));
		if (!qOptional.isPresent()){
			Optional<KtTongKho> qOptionalMa = ktTongKhoRepository.findByMaTongKho(id);
			if (!qOptionalMa.isPresent()){
				throw new UnsupportedOperationException("Không tồn tại bản ghi");
			}
			return qOptionalMa;
		}
		return qOptional;
	}

	public KtTongKho saveKtTongKho(KtTongKho ktTongKho){
		return ktTongKhoRepository.save(ktTongKho);
	}

	public void delKtTongKhobyId(Long id){
		ktTongKhoRepository.deleteById(id);
	}
	/**
	 * Điểm kho
	 * @param ma
	 * @param ten
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<KtDiemKho> selectDiemKhoParams(String ma, String ten, Long id, Pageable pageable){
		return ktDiemKhoRepository.selectParams(ma,ten,id,pageable);
	}

	public Optional<KtDiemKho> findKtDiemKhobyId(Long id){
		return ktDiemKhoRepository.findById(id);
	}

	public KtDiemKho saveKtDiemKho(KtDiemKho ktTongKho){
		return ktDiemKhoRepository.save(ktTongKho);
	}

	public void delKtDiemKhobyId(Long id){
		ktDiemKhoRepository.deleteById(id);
	}
	/**
	 * Nhà kho
	 * @param ma
	 * @param ten
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<KtNhaKho> selectNhaKhoParams(String ma, String ten, Long id, Pageable pageable){
		return ktNhaKhoRepository.selectParams(ma,ten,id,pageable);
	}

	public Optional<KtNhaKho> findKtNhaKhobyId(Long id){
		return ktNhaKhoRepository.findById(id);
	}

	public KtNhaKho saveKtNhaKho(KtNhaKho ktTongKho){
		return ktNhaKhoRepository.save(ktTongKho);
	}

	public void delKtNhaKhobyId(Long id){
		ktNhaKhoRepository.deleteById(id);
	}

	/**
	 * Ngăn kho
	 * @param ma
	 * @param ten
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<KtNganKho> selectNganKhoParams(String ma, String ten, Long id, Pageable pageable){
		return ktNganKhoRepository.selectParams(ma,ten,id,pageable);
	}

	public Optional<KtNganKho> findKtNganKhobyId(Long id){
		return ktNganKhoRepository.findById(id);
	}

	public KtNganKho saveKtNganKho(KtNganKho ktTongKho){
		return ktNganKhoRepository.save(ktTongKho);
	}

	public void delKtNganKhobyId(Long id){
		ktNganKhoRepository.deleteById(id);
	}

	/**
	 * Ngăn lô
	 * @param ma
	 * @param ten
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<KtNganLo> selectNganLoParams(String ma, String ten, Long id, Pageable pageable){
		return ktNganLoRepository.selectParams(ma,ten,id,pageable);
	}

	public Optional<KtNganLo> findKtNganLobyId(Long id){
		return ktNganLoRepository.findById(id);
	}

	public KtNganLo saveKtNganLo(KtNganLo ktTongKho){
		return ktNganLoRepository.save(ktTongKho);
	}

	public void delKtNganLobyId(Long id){
		ktNganLoRepository.deleteById(id);
	}
}