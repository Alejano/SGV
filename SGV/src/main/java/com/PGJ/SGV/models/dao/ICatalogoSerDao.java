package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.CatalogoServicio;

public interface ICatalogoSerDao extends PagingAndSortingRepository<CatalogoServicio,Long>{
	
	@Query("select count(c)from CatalogoServicio c")
	public Long totalCatalogoServicios();
}
