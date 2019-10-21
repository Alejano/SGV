package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Vehiculo;

public interface IVehiculoService {
	public List<Vehiculo> findAll(); 
	
	public Page<Vehiculo> findAllPage(Pageable pageable); 
	
	public void save(Vehiculo vehiculo);
	
	public Vehiculo findOne(String placa);
	
	public void delete(String placa);
		
	public List<Vehiculo> findVehiculosArea(Long id_adscripcion);
	
	public Page<Vehiculo> findVehiculosAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Vehiculo> findVehElemntoPage(String elemento,Pageable pageable);
	
	public Page<Vehiculo> findVehElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Long totalVehiculo();
	
	public int totalVehiculoArea(Long id_adscripcion);
	
	public Double kilometraje(String placa);
}
