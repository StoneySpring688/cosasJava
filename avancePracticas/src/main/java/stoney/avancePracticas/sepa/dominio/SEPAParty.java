package stoney.avancePracticas.sepa.dominio;

public class SEPAParty {
    private String name;
    private String iban;
    private String bic;
    private SEPAAddress address;
    
    private SEPAParty(Builder builder) {
        this.name = builder.name;
        this.iban = builder.iban;
        this.bic = builder.bic;
        this.address = builder.address;
    }
    
    // Getters
    public String getName() { return name; }
    public String getIban() { return iban; }
    public String getBic() { return bic; }
    public SEPAAddress getAddress() { return address; }
    
    // Builder
    public static class Builder {
        private String name;
        private String iban;
        private String bic;
        private SEPAAddress address;
        
        public Builder(String name, String iban, String bic) {
            this.name = name;
            this.iban = iban;
            this.bic = bic;
        }
        
        public Builder withAddress(SEPAAddress address) {
            this.address = address;
            return this;
        }
        
        public Builder withAddress(String country, String addressLine) {
            this.address = new SEPAAddress(country, addressLine);
            return this;
        }
        
        public SEPAParty build() {
            validate();
            return new SEPAParty(this);
        }
        
        private void validate() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name es requerido");
            }
            if (!isValidIBAN(iban)) {
                throw new IllegalArgumentException("IBAN inválido: " + iban);
            }
            if (!isValidBIC(bic)) {
                throw new IllegalArgumentException("BIC inválido: " + bic);
            }
        }
        
        private boolean isValidIBAN(String iban) {
            return iban != null && iban.length() >= 15 && iban.length() <= 34;
        }
        
        private boolean isValidBIC(String bic) {
            return bic != null && (bic.length() == 8 || bic.length() == 11);
        }
    }
}