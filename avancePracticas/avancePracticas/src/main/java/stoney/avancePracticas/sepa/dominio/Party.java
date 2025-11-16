package stoney.avancePracticas.sepa.dominio;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Party {
	@XmlElement(name = "Nm", required = true)
	private String nombre;
	@XmlElement(name = "Id", required = true)
	private String id;
	
	public Party() {}
	
	public Party(String nombre, String id) {
		this.nombre = nombre;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getId() {
		return id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
