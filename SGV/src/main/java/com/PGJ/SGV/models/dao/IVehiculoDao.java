package com.PGJ.SGV.models.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Vehiculo;

public interface IVehiculoDao extends PagingAndSortingRepository<Vehiculo,String > {
	
	@Query("select p from Vehiculo p where p.tipo_vehiculo like ?1")
	public Page<Vehiculo> findTVechiulo(String vehiculo,Pageable pageable);
	
	@Query("select p from Vehiculo p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public List<Vehiculo> findVehiculosArea(Long id_adscripcion);
	
	@Query("select p from Vehiculo p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public Page<Vehiculo> findVehiculosAreaPage(Long id_adscripcion,Pageable pageable);
	
	@Query("select v from Vehiculo v  where v.clase like %?1% or v.marca like %?1% or v.modelo like %?1% or v.estado like %?1% or v.tipo_combustible like %?1% or v.tipo like %?1% or v.ano like %?1% or v.kilometraje like %?1% or v.valor_factura like %?1% or v.placa like %?1% or v.no_serie like %?1% or v.no_factura like %?1% or v.no_poliza like %?1%")
	public Page<Vehiculo> findVehElemntoPage(String elemento,Pageable pageable);
	
	@Query("select v from Vehiculo v inner join Adscripcion a on v.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1 and (v.clase like %?2% or v.marca like %?2% or v.modelo like %?2% or v.estado like %?2% or v.tipo_combustible like %?2% or v.tipo like %?2% or v.ano like %?2% or v.kilometraje like %?2% or v.valor_factura like %?2% or v.placa like %?2% or v.no_serie like %?2% or v.no_factura like %?2% or v.no_poliza like %?2%)")
	public Page<Vehiculo> findVehElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	@Query("select count(v) from Vehiculo v")
	public Long TotalVehiculos();
	
	@Query("select count(p) from Vehiculo p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where a.id_adscripcion like ?1")
	public int TotalVehiculosArea(Long id_adscripcion);
	
	@Query("select v.kilometraje from Vehiculo v where v.placa like %?1%")
	public Double kilometraje(String placa);
	
}
