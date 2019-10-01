package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Seguro;

public interface ISeguroService {
    public List<Seguro> findAll(); 
    
    public Page<Seguro> findAll(Pageable pageable);
	
	public void save(Seguro seguro);
	
	public Seguro findOne(Long id);
	
	public void delete(Long id);
	
	public List<Seguro> FindSeguroArea(Long id_adscripcion);
	
	public Page<Seguro> FindSeguroAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Seguro> FindSegElemPage(String elemento, Pageable pageable);
	
	public Page<Seguro> FindSegElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Long totalSeguros();
 
}
