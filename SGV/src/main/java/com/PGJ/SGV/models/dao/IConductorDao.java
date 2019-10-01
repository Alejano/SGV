package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Conductor;


public interface IConductorDao extends PagingAndSortingRepository<Conductor, String> {
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public List<Conductor> findConductorArea(Long id_adscripcion);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public Page<Conductor> findConductorAreaPage(Pageable pageable,Long id_adscripcion);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.no_empleado like %?1% or p.nombre like %?1% or p.apellido1 like %?1% or p.apellido2 like %?1% or p.adscripcion.nombre_adscripcion like %?1%")
	public Page<Conductor> findCondElemnPage(String elemento,Pageable pageable);
	
	@Query("select p from Conductor p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1 and (p.no_empleado like %?2% or p.nombre like %?2% or p.apellido1 like %?2% or p.apellido2 like %?2% or p.adscripcion.nombre_adscripcion like %?2%)")
	public Page<Conductor> findCondElemnAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select count(c) from Conductor c")
	public Long totalConductor();
}
