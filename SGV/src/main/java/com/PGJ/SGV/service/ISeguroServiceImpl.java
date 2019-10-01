package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.ISeguroDao;
import com.PGJ.SGV.models.entity.Seguro;

@Service
public class ISeguroServiceImpl implements ISeguroService {
	
	@Autowired
	private ISeguroDao seguroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Seguro> findAll() {
		// TODO Auto-generated method stub
		return (List<Seguro>)seguroDao.findAll();
	}

	@Override
	@Transactional
	public void save(Seguro seguro) {
		// TODO Auto-generated method stub
		seguroDao.save(seguro);
	}

	@Override
	@Transactional(readOnly = true)
	public Seguro findOne(Long id) {
		// TODO Auto-generated method stub
		return seguroDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		seguroDao.deleteById(id);
	}

	@Override
	public List<Seguro> FindSeguroArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Seguro>) seguroDao.FindSeguroArea(id_adscripcion);
	}

	@Override
	public Page<Seguro> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.findAll(pageable);
	}

	@Override
	public Page<Seguro> FindSeguroAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSeguroAreaPage(id_adscripcion, pageable);
	}

	@Override
	public Page<Seguro> FindSegElemPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSegElemPage(elemento, pageable);
	}

	@Override
	public Page<Seguro> FindSegElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSegElemAreaPage(id_adscripcion, elemento, pageable);
	}

	@Override
	public Long totalSeguros() {
		// TODO Auto-generated method stub
		return seguroDao.totalSeguro();
	}
	
	
}