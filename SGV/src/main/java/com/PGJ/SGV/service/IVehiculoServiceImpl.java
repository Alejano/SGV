package com.PGJ.SGV.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IVehiculoDao;

import com.PGJ.SGV.models.entity.Vehiculo;

@Service
public class IVehiculoServiceImpl implements IVehiculoService {

   @Autowired
	private IVehiculoDao vehiculoDao; 
	
   
    @Override
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() {
		// TODO Auto-generated method stub
		return (List<Vehiculo>) vehiculoDao.findAll();
	}

    @Override
	@Transactional
	public void save(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		vehiculoDao.save(vehiculo);
	}

    @Override
	@Transactional(readOnly = true)
	public Vehiculo findOne(String placa) {
		// TODO Auto-generated method stub
		return vehiculoDao.findById(placa).orElse(null);
	}

    @Override
	@Transactional
	public void delete(String placa) {
		vehiculoDao.deleteById(placa);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Vehiculo> findVehiculosArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Vehiculo>) vehiculoDao.findVehiculosArea(id_adscripcion);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findVehiculosAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findVehiculosAreaPage(id_adscripcion, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findAllPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findVehElemntoPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findVehElemntoPage(elemento, pageable);
	}

	@Override
	public Page<Vehiculo> findVehElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findVehElemAreaPage(id_adscripcion, elemento, pageable);
	}

	@Override
	public Long totalVehiculo() {
		// TODO Auto-generated method stub
		return vehiculoDao.TotalVehiculos();
	}
	
	@Override
	public Double kilometraje(String placa) {
		// TODO Auto-generated method stub
		return vehiculoDao.kilometraje(placa);
	}
	
}
