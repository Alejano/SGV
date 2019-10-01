package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Viaje;

public interface IViajeDao extends PagingAndSortingRepository<Viaje, Long> {	
	        
	@Query("select v from Viaje v inner join Vehiculo e on v.vehiculo.placa = e.placa inner join Adscripcion a on e.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1")
	public List<Viaje> ViajesArea(Long id_adscripcion);
	
	@Query("select v from Viaje v inner join Vehiculo e on v.vehiculo.placa = e.placa inner join Adscripcion a on e.adscripcion.id_adscripcion=a.id_adscripcion where a.id_adscripcion like ?1")
	public Page<Viaje> ViajesAreaPage(Long id_adscripcion,Pageable pageable);
	
	@Query("select v from Viaje v inner join Conductor c on v.conductor.no_empleado = c.no_empleado inner join Vehiculo l on v.vehiculo.placa=l.placa  where c.nombre like %?1% or c.apellido1 like %?1% or c.apellido2 like %?1% or l.placa like %?1% or v.fecha_regreso like %?1% or v.fecha_salida like %?1% or v.destino like %?1% or v.kilometraje_inicial like %?1% or v.kilometraje_final like %?1% or v.distancia_recorrida like %?1%")
	public Page<Viaje> ViajeElemPage(String nombre,Pageable pageable);
	
	@Query("select v from Viaje v inner join Vehiculo vh on v.vehiculo.placa = vh.placa inner join Adscripcion a on vh.adscripcion.id_adscripcion=a.id_adscripcion inner join Conductor c on v.conductor.no_empleado = c.no_empleado where a.id_adscripcion like ?1 and (c.nombre like %?2% or c.apellido1 like %?2% or c.apellido2 like %?2% or vh.placa like %?2% or v.fecha_regreso like %?2% or v.fecha_salida like %?2% or v.destino like %?2% or v.kilometraje_inicial like %?2% or v.kilometraje_final like %?2% or v.distancia_recorrida like %?2%)")
	public Page<Viaje> ViajesElemAreaPage(Long id_adscripcion,String nombre,Pageable pageable);
	
	@Query("select count(v) from Viaje v")
	public Long viajestotales();
		
}
