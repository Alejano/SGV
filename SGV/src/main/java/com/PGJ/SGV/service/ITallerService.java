package com.PGJ.SGV.service;

import java.util.List;

import com.PGJ.SGV.models.entity.Taller;

public interface ITallerService {

public List<Taller> findAll(); 
	
	public void save(Taller taller);
	
	public Taller findOne(Long id_taller);
	
	public void delete(Long id_taller);
	
	
}
