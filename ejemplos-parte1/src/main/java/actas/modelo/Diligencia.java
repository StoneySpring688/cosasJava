package actas.modelo;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Diligencia {
    @XmlAttribute
    private String fecha;  // JAXB maneja como String
    
    private boolean extraordinaria;
    private String nif;
    private Double nota;
    
    // Método para obtener LocalDate no para JAXB
    public LocalDate getFechaLocal() {
        return LocalDate.parse(fecha);
    }
    public void setFechaLocal(LocalDate fechaLocal) {
        this.fecha = fechaLocal.toString();
    }
    
    @XmlAttribute
    public boolean isExtraordinaria() {
        return extraordinaria;
    }
    public void setExtraordinaria(boolean extraordinaria) {
        this.extraordinaria = extraordinaria;
    }
    
    @XmlElement(name = "nif")
    public String getNif() {
        return nif;
    }
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    @XmlElement(name = "nota")
    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
        this.nota = nota;
    }
}
