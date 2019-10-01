package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "viajes")
public class Viaje implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_viaje;
	private String fecha_salida;
	private String fecha_regreso;
	private Double kilometraje_inicial;
	private Double kilometraje_final;
	private Double distancia_recorrida;
	private String destino;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Conductor conductor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Vehiculo vehiculo;
		

	public Long getId_viaje() {
		return id_viaje;
	}

	public void setId_viaje(Long id_viaje) {
		this.id_viaje = id_viaje;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public String getFecha_regreso() {
		return fecha_regreso;
	}

	public void setFecha_regreso(String fecha_regreso) {
		this.fecha_regreso = fecha_regreso;
	}

	public Double getKilometraje_inicial() {
		return kilometraje_inicial;
	}

	public void setKilometraje_inicial(Double kilometraje_inicial) {
		this.kilometraje_inicial = kilometraje_inicial;
	}

	public Double getKilometraje_final() {
		return kilometraje_final;
	}

	public void setKilometraje_final(Double kilometraje_final) {
		this.kilometraje_final = kilometraje_final;
	}

	public Double getDistancia_recorrida() {
		return distancia_recorrida;
	}

	public void setDistancia_recorrida(Double distancia_recorrida) {
		this.distancia_recorrida = distancia_recorrida;
	}


	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	
	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	private static final long serialVersionUID = 1L;

}
