package actas.modelo;

import javax.xml.bind.annotation.XmlElement;

public class Calificacion {
	
	private String nif;
	private String nombre;
	private Double nota;
	public String getNif() {
		return nif;
	}
	@XmlElement(name="nif")
	public void setNif(String nif) {
		this.nif = nif;
	}
	@XmlElement(name="nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlElement(name="nota")
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}
}
