package com.PGJ.SGV.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IViajeDao;
import com.PGJ.SGV.models.entity.Viaje;

@Service
public class IViajeServiceImpl implements IViajeService {

	@Autowired
	private IViajeDao viajeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Viaje> findAll() {
		// TODO Auto-generated method stub
		 return (List<Viaje>) viajeDao.findAll();
	}

	@Override
	@Transactional
	public void save(Viaje viaje) {
		// TODO Auto-generated method stub
		viajeDao.save(viaje);
	}

	@Override
	@Transactional(readOnly = true)
	public Viaje findOne(Long id_viaje) {
		// TODO Auto-generated method stub
		return viajeDao.findById(id_viaje).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_viaje) {
		// TODO Auto-generated method stub
		viajeDao.deleteById(id_viaje);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Viaje> ViajesArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return viajeDao.ViajesArea(id_adscripcion);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Viaje> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Viaje> ViajesAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.ViajesAreaPage(id_adscripcion,pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Viaje> ViajeElemPage(String nombre, Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.ViajeElemPage(nombre, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Viaje> ViajesElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.ViajesElemAreaPage(id_adscripcion, elemento, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long viajestotales() {
		// TODO Auto-generated method stub
		return viajeDao.viajestotales();
	}
	
	@Override
	public int TotalViajesArea(Long id_adscipcion) {
		// TODO Auto-generated method stub
		return viajeDao.TotalViajesArea(id_adscipcion);
	}

}
