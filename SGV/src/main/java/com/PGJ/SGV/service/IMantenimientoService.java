package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Mantenimiento;

public interface IMantenimientoService {
		public List<Mantenimiento> findAll(); 
		
		public Page<Mantenimiento> findAll(Pageable pageable); 
		
		public List<Mantenimiento> FindMantPlaca(String placa);
		
		public List<Mantenimiento> FindMantPlacaAds(String placa,Long id_adscripcion);
		
		public Page<Mantenimiento> FindMantPlacaPage(String placa,Pageable pageable);
		
		public Page<Mantenimiento> FindMantPlacaAreaPage(String placa,Long id_adscripcion,Pageable pageable);
	
	public void save(Mantenimiento mantenimiento);
	
	public Mantenimiento findOne(Long id_mantenimiento);
	
	public void delete(Long id_mantenimiento);
	
	public List<Mantenimiento> FindMantenimientoArea(Long id_adscripcion);
	
	public Page<Mantenimiento> FindMantenimientoAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Mantenimiento> FindMantElemPage(String elemento,Pageable pageable);
	
	public Page<Mantenimiento> FindMantelemntAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Long totalMantenimiento();
	

}
