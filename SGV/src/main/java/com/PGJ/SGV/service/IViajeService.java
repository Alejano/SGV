package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Viaje;

public interface IViajeService {
	public List<Viaje> findAll(); 
	
	public Page<Viaje> findAll(Pageable pageable); 	
	
	public void save(Viaje conductor);
	
	public Viaje findOne(Long id_viaje);
	
	public void delete(Long id_viaje);
	
	public List<Viaje> ViajesArea(Long id_adscripcion);
	
	public Page<Viaje> ViajesAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Viaje> ViajeElemPage(String nombre,Pageable pageable); 
	
	public Page<Viaje> ViajesElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Long viajestotales(); 
	
	public int TotalViajesArea(Long id_adscipcion);
	
	public Viaje viajependiente(String placa);
}
