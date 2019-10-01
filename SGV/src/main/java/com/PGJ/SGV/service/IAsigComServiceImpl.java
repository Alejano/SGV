package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IAsigComDao;
import com.PGJ.SGV.models.entity.AsigCombustible;;

@Service
public class IAsigComServiceImpl implements IAsigComService {

	@Autowired
	private IAsigComDao asignacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<AsigCombustible> findAll() {
		// TODO Auto-generated method stub
		return (List<AsigCombustible>) asignacionDao.findAll();
	}

	@Override
	@Transactional
	public void save(AsigCombustible combustible) {
		// TODO Auto-generated method stub
		asignacionDao.save(combustible);
	}

	@Override
	@Transactional(readOnly = true)
	public AsigCombustible findOne(Long id_asignacion) {
		// TODO Auto-generated method stub
		return asignacionDao.findById(id_asignacion).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_asignacion) {
		// TODO Auto-generated method stub
		asignacionDao.deleteById(id_asignacion);
	}

	@Override
	public List<AsigCombustible> findPlaca(String placa) {
		// TODO Auto-generated method stub
		return asignacionDao.findPlaca(placa);
	}

	
	

}
