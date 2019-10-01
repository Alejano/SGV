package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.AsigCombustible;

public interface IAsigComService {

	public List<AsigCombustible> findAll(); 
	
	public List<AsigCombustible> findPlaca(String placa);
	
	public void save(AsigCombustible combustible);
	
	public AsigCombustible findOne(Long id_asignacion);
	
	public void delete(Long id_asignacion);
	
}
