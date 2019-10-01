package com.PGJ.SGV.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IConductorDao;
import com.PGJ.SGV.models.entity.Conductor;

@Service
public class IConductorServiceImpl implements IConductorService {

	@Autowired
	private IConductorDao conductorDao;

	
	@Override
	@Transactional(readOnly = true)
	public List<Conductor> findAll() {
		// TODO Auto-generated method stub					
		
		return  (List<Conductor>) conductorDao.findAll();
	}

	@Override
	@Transactional
	public void save(Conductor conductor) {
		conductorDao.save(conductor);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Conductor findOne(String no_empleado) {
		// TODO Auto-generated method stub
		return conductorDao.findById(no_empleado).orElse(null);
	}

	@Override
	@Transactional
	public void delete(String no_empleado) {
		// TODO Auto-generated method stub
		conductorDao.deleteById(no_empleado);
	}

	@Override
	public List<Conductor> findConductorArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Conductor>) conductorDao.findConductorArea(id_adscripcion);
	}

	@Override
	public Page<Conductor> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findAll(pageable);
	}

	@Override
	public Page<Conductor> findConductorAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findConductorAreaPage(pageable, id_adscripcion);
	}

	@Override
	public Page<Conductor> findCondElemnPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findCondElemnPage(elemento, pageable);
	}

	@Override
	public Page<Conductor> findCondElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findCondElemnAreaPage(id_adscripcion, elemento, pageable);
	}

	@Override
	public Long totalConductores() {
		// TODO Auto-generated method stub
		return conductorDao.totalConductor();
	}

}
