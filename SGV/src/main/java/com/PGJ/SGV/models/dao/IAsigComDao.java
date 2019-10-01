package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PGJ.SGV.models.entity.AsigCombustible;

public interface IAsigComDao extends CrudRepository<AsigCombustible, Long> {
	
	@Query("select a from AsigCombustible a inner join Vehiculo v on a.vehiculo.placa = v.placa where v.placa like %?1%")
	public List<AsigCombustible> findPlaca(String placa);
}

