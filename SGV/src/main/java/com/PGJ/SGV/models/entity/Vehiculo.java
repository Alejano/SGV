package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculos")
public class Vehiculo implements Serializable {
	
	
	    @Id
		private String placa;
	    
		private String marca;
		private String modelo;
		private String ano;
		private String clase;
		private String tipo;
		private String no_serie;
		private String no_factura;
		private double valor_factura;
		private String no_poliza;
		private String estado;
		private String tipo_combustible;
		private double kilometraje;		
		
		//SQL
		@ManyToOne(fetch = FetchType.LAZY)
		private Adscripcion adscripcion;
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Viaje> viajes;
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Mantenimiento> mantenimientos;
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<AsigCombustible> asignaciones;
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Seguro> seguros;
		
		//SQL
		private String tipo_vehiculo;
		private int cilindros;
		
		
		private static final long serialVersionUID = 1L;

		public Vehiculo() {
			viajes = new ArrayList<Viaje>();
			mantenimientos = new ArrayList<Mantenimiento>();
			asignaciones = new ArrayList<AsigCombustible>();
			seguros = new ArrayList<Seguro>();
		}

		public String getPlaca() {
			return placa;
		}

		public void setPlaca(String placa) {
			this.placa = placa;
		}

		public String getMarca() {
			return marca;
		}

		public void setMarca(String marca) {
			this.marca = marca;
		}

		public String getModelo() {
			return modelo;
		}

		public void setModelo(String modelo) {
			this.modelo = modelo;
		}

		public String getAno() {
			return ano;
		}

		public void setAno(String ano) {
			this.ano = ano;
		}

		public String getClase() {
			return clase;
		}

		public void setClase(String clase) {
			this.clase = clase;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getNo_serie() {
			return no_serie;
		}

		public void setNo_serie(String no_serie) {
			this.no_serie = no_serie;
		}

		public String getNo_factura() {
			return no_factura;
		}

		public void setNo_factura(String no_factura) {
			this.no_factura = no_factura;
		}

		public double getValor_factura() {
			return valor_factura;
		}

		public void setValor_factura(double valor_factura) {
			this.valor_factura = valor_factura;
		}

		public String getNo_poliza() {
			return no_poliza;
		}

		public void setNo_poliza(String no_poliza) {
			this.no_poliza = no_poliza;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getTipo_combustible() {
			return tipo_combustible;
		}

		public void setTipo_combustible(String tipo_combustible) {
			this.tipo_combustible = tipo_combustible;
		}

		public double getKilometraje() {
			return kilometraje;
		}

		public void setKilometraje(double kilometraje) {
			this.kilometraje = kilometraje;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		public Adscripcion getAdscripcion() {
			return adscripcion;
		}

		public void setAdscripcion(Adscripcion adscripcion) {
			this.adscripcion = adscripcion;
		}

		public List<Viaje> getViajes() {
			return viajes;
		}

		public void setViajes(List<Viaje> viajes) {
			this.viajes = viajes;
		}
		
		public void adViaje(Viaje viaje) {
			viajes.add(viaje);
		}

		public List<Mantenimiento> getMantenimientos() {
			return mantenimientos;
		}

		public void setMantenimientos(List<Mantenimiento> mantenimientos) {
			this.mantenimientos = mantenimientos;
		}
		
		public void adMantenimiento (Mantenimiento mantenimiento) {
			mantenimientos.add(mantenimiento);
		}

		public List<AsigCombustible> getAsignaciones() {
			return asignaciones;
		}

		public void setAsignaciones(List<AsigCombustible> asignaciones) {
			this.asignaciones = asignaciones;
		}
		public void adAsignaciones (AsigCombustible asignacion) {
			asignaciones.add(asignacion);
		}

		public List<Seguro> getSeguros() {
			return seguros;
		}

		public void setSeguros(List<Seguro> seguros) {
			this.seguros = seguros;
		}
		
		public void adSeguro (Seguro seguro) {
			seguros.add(seguro);
		}

		public String getTipo_vehiculo() {
			return tipo_vehiculo;
		}

		public void setTipo_vehiculo(String tipo_vehiculo) {
			this.tipo_vehiculo = tipo_vehiculo;
		}

		public int getCilindros() {
			return cilindros;
		}

		public void setCilindros(int cilindros) {
			this.cilindros = cilindros;
		}
		
		
		
}
