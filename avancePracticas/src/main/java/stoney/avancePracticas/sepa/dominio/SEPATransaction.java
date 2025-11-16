package stoney.avancePracticas.sepa.dominio;

public class SEPATransaction {
    private String id;
    private long amountInCents;
    private SEPAParty creditor;
    private String concept;
    
    private SEPATransaction(Builder builder) {
        this.id = builder.id;
        this.amountInCents = builder.amountInCents;
        this.creditor = builder.creditor;
        this.concept = builder.concept;
    }
    
    // Getters
    public String getId() { return id; }
    public long getAmountInCents() { return amountInCents; }
    public SEPAParty getCreditor() { return creditor; }
    public String getConcept() { return concept; }
    
    // Builder
    public static class Builder {
        private String id;
        private long amountInCents;
        private SEPAParty creditor;
        private String concept;
        
        public Builder(String id, long amountInCents, SEPAParty creditor) {
            this.id = id;
            this.amountInCents = amountInCents;
            this.creditor = creditor;
        }
        
        public Builder withConcept(String concept) {
            this.concept = concept;
            return this;
        }
        
        // Método alternativo para pasar importe en euros
        public Builder withAmountInEuros(double euros) {
            this.amountInCents = (long) (euros * 100);
            return this;
        }
        
        public SEPATransaction build() {
            validate();
            return new SEPATransaction(this);
        }
        
        private void validate() {
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("id es requerido");
            }
            if (amountInCents <= 0) {
                throw new IllegalArgumentException("amountInCents debe ser mayor que 0");
            }
            if (creditor == null) {
                throw new IllegalArgumentException("creditor es requerido");
            }
            if (concept == null || concept.isEmpty()) {
                throw new IllegalArgumentException("concept es requerido");
            }
        }
    }
}
