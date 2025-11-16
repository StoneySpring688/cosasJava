package stoney.avancePracticas.sepa.dominio;

import java.util.ArrayList;
import java.util.List;

public class SEPAPaymentInfo {
    private String id;
    private SEPAParty debtor;
    private List<SEPATransaction> transactions;
    
    private SEPAPaymentInfo(Builder builder) {
        this.id = builder.id;
        this.debtor = builder.debtor;
        this.transactions = builder.transactions;
    }
    
    // Getters
    public String getId() { return id; }
    public SEPAParty getDebtor() { return debtor; }
    public List<SEPATransaction> getTransactions() { return transactions; }
    
    // Builder
    public static class Builder {
        private String id;
        private SEPAParty debtor;
        private List<SEPATransaction> transactions = new ArrayList<>();
        
        public Builder(String id, SEPAParty debtor) {
            this.id = id;
            this.debtor = debtor;
        }
        
        public Builder addTransaction(SEPATransaction transaction) {
            this.transactions.add(transaction);
            return this;
        }
        
        public Builder addTransaction(String id, long amountInCents, 
                                     SEPAParty creditor, String concept) {
            SEPATransaction transaction = new SEPATransaction.Builder(id, amountInCents, creditor)
                .withConcept(concept)
                .build();
            this.transactions.add(transaction);
            return this;
        }
        
        public SEPAPaymentInfo build() {
            validate();
            return new SEPAPaymentInfo(this);
        }
        
        private void validate() {
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("id es requerido");
            }
            if (debtor == null) {
                throw new IllegalArgumentException("debtor es requerido");
            }
            if (transactions.isEmpty()) {
                throw new IllegalArgumentException("Al menos una transacción es requerida");
            }
        }
    }
}
