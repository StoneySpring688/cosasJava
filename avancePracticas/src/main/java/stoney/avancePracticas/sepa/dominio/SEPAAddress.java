package stoney.avancePracticas.sepa.dominio;

public class SEPAAddress {
    private String country;
    private String addressLine;
    
    public SEPAAddress(String country, String addressLine) {
        this.country = country;
        this.addressLine = addressLine;
    }
    
    // Getters y Setters
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }
}
