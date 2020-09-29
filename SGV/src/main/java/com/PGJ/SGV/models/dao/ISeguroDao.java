package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Seguro;

public interface ISeguroDao extends PagingAndSortingRepository<Seguro, Long> {
	/*
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.placa = v.placa inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1")
	public List<Seguro> FindSeguroArea(Long id_adscripcion);
	
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.placa = v.placa inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1")
	public Page<Seguro> FindSeguroAreaPage(Long id_adscripcion,Pageable pageable);
	
	@Query("select s from Seguro s where s.poliza like %?1% or s.aseguradora like %?1% or s.cobertura like %?1%")
	public Page<Seguro> FindSegElemPage(String elemento,Pageable pageable);
	
	@Query("select s from Seguro s inner join Vehiculo v on s.vehiculo.placa = v.placa inner join Adscripcion a on v.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1 and (s.poliza like %?2% or s.aseguradora like %?2% or s.cobertura like %?2%)")
	public Page<Seguro> FindSegElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select count(s) from Seguro s")
	public Long totalSeguro();
	*/
}
