package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Usuario;


public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, String> {
	
	@Query("select p,a from Usuario p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.no_empleado = ?1")
	public Usuario findByid_adscripcion(String no_empleado);
	
	@Query("select p,a from Usuario p inner join Adscripcion a on p.adscripcion.id_adscripcion = a.id_adscripcion where p.no_empleado like %?1%")
	public Page<Usuario> findByAreaPage(String no_empleado,Pageable pageable);
	
	@Query("select u from Usuario u inner join Adscripcion a on u.adscripcion.id_adscripcion = a.id_adscripcion where u.no_empleado like %?1% or u.nombre like %?1% or u.apellido1 like %?1% or u.apellido2 like %?1% or a.nombre_adscripcion like %?1%")
	public Page<Usuario> finUsuElemntPage(String elemento,Pageable pageable);
	
	@Query("select count(u) from Usuario u")
	public Long totalUsuarios();
		
}
