package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.PGJ.SGV.models.dao.IMantenimientoDao;
import com.PGJ.SGV.models.entity.Mantenimiento;

@Service
public class IMantenimientoServiceImpl implements IMantenimientoService {

	@Autowired
	private IMantenimientoDao imantDao;
	
	@Override
	public List<Mantenimiento> findAll() {
		// TODO Auto-generated method stub
	 return (List<Mantenimiento>) imantDao.findAll();
	}

	@Override
	public void save(Mantenimiento mantenimiento) {
		// TODO Auto-generated method stub
		imantDao.save(mantenimiento);
	}

	@Override
	public Mantenimiento findOne(Long id_mantenimiento) {
		// TODO Auto-generated method stub
		return imantDao.findById(id_mantenimiento).orElse(null);
	}

	@Override
	public void delete(Long id_mantenimiento) {
		// TODO Auto-generated method stub
		imantDao.deleteById(id_mantenimiento);
		
	}

	@Override
	public List<Mantenimiento> FindMantenimientoArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Mantenimiento>) imantDao.FindMantenimientoArea(id_adscripcion);
	}

	@Override
	public List<Mantenimiento> FindMantPlaca(String placa) {
		// TODO Auto-generated method stub
		return (List<Mantenimiento>) imantDao.FindMantPlaca(placa);
	}

	@Override
	public List<Mantenimiento> FindMantPlacaAds(String placa, Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Mantenimiento>) imantDao.FindMantPlacaAds(placa, id_adscripcion);
	}

	@Override
	public Page<Mantenimiento> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return imantDao.findAll(pageable);
	}

	@Override
	public Page<Mantenimiento> FindMantenimientoAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return imantDao.FindMantenimientoAreaPage(id_adscripcion, pageable);
	}

	@Override
	public Page<Mantenimiento> FindMantPlacaPage(String placa, Pageable pageable) {
		// TODO Auto-generated method stub
		return imantDao.FindMantPlacaPage(placa, pageable);
	}

	@Override
	public Page<Mantenimiento> FindMantPlacaAreaPage(String placa, Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return imantDao.FindMantPlacaAreaPage(placa, id_adscripcion, pageable);
	}

	@Override
	public Page<Mantenimiento> FindMantelemntAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return imantDao.FindMantelemntAreaPage(id_adscripcion, elemento, pageable);
	}

	@Override
	public Page<Mantenimiento> FindMantElemPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return imantDao.FindMantElemPage(elemento, pageable);
	}

	@Override
	public Long totalMantenimiento() {
		// TODO Auto-generated method stub
		return imantDao.totalMantenimiento();
	}

}
