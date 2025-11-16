package stoney.avancePracticas.sepa.dominio;

import java.time.LocalDateTime;
import java.util.*;

public class SEPADocument {
    private String messageId;
    private LocalDateTime creationDateTime;
    private SEPAInitiator initiator;
    private List<SEPAPaymentInfo> paymentInfos;
    
    private SEPADocument(Builder builder) {
        this.messageId = builder.messageId;
        this.creationDateTime = builder.creationDateTime;
        this.initiator = builder.initiator;
        this.paymentInfos = builder.paymentInfos;
    }
    
    // Getters
    public String getMessageId() { return messageId; }
    public LocalDateTime getCreationDateTime() { return creationDateTime; }
    public SEPAInitiator getInitiator() { return initiator; }
    public List<SEPAPaymentInfo> getPaymentInfos() { return paymentInfos; }
    
    // Builder
    public static class Builder {
        private String messageId;
        private LocalDateTime creationDateTime;
        private SEPAInitiator initiator;
        private List<SEPAPaymentInfo> paymentInfos = new ArrayList<>();
        
        public Builder(String messageId) {
            this.messageId = messageId;
            this.creationDateTime = LocalDateTime.now();
        }
        
        public Builder withCreationDateTime(LocalDateTime dateTime) {
            this.creationDateTime = dateTime;
            return this;
        }
        
        public Builder addPaymentInfo(SEPAPaymentInfo paymentInfo) {
            this.paymentInfos.add(paymentInfo);
            return this;
        }
        
        public Builder addInitator(SEPAInitiator initiator) {
			this.initiator = initiator;
			return this;
		}
        
        public Builder addInitiator(String name, String id) {
        	this.initiator = new SEPAInitiator(name, id);
        	return this;
        }
        
        public SEPADocument build() {
            validate();
            return new SEPADocument(this);
        }
        
        private void validate() {
            if (messageId == null || messageId.isEmpty()) {
                throw new IllegalArgumentException("messageId es requerido");
            }
            if (initiator == null) {
                throw new IllegalArgumentException("initiator es requerido");
            }
            if (paymentInfos.isEmpty()) {
                throw new IllegalArgumentException("Al menos un paymentInfo es requerido");
            }
        }
    }
}
