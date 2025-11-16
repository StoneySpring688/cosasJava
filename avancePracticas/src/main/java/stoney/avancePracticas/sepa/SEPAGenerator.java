package stoney.avancePracticas.sepa;

import java.io.*;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import stoney.avancePracticas.sepa.dominio.*;

public class SEPAGenerator{
    
    private static final String NAMESPACE = "urn:iso:std:iso:20022:tech:xsd:pain.001.002.03";
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    private SEPADocument document;
    
    public SEPAGenerator(SEPADocument document) {
        this.document = document;
    }
    
    public String generateXML() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        
        Element root = doc.createElement("Document");
        root.setAttribute("xmlns", NAMESPACE);
        doc.appendChild(root);
        
        Element cstmrCdtTrfInitn = doc.createElement("CstmrCdtTrfInitn");
        root.appendChild(cstmrCdtTrfInitn);
        
        cstmrCdtTrfInitn.appendChild(createGroupHeader(doc));
        
        for (SEPAPaymentInfo paymentInfo : document.getPaymentInfos()) {
            cstmrCdtTrfInitn.appendChild(createPaymentInfo(doc, paymentInfo));
        }
        
        return convertDOMToString(doc);
    }
    
    private Element createGroupHeader(Document doc) {
        Element grpHdr = doc.createElement("GrpHdr");
        
        addElement(doc, grpHdr, "MsgId", document.getMessageId());
        addElement(doc, grpHdr, "CreDtTm", 
                  document.getCreationDateTime().format(DATE_FORMATTER));
        
        int totalTransactions = document.getPaymentInfos().stream()
            .mapToInt(p -> p.getTransactions().size())
            .sum();
        addElement(doc, grpHdr, "NbOfTxns", String.valueOf(totalTransactions));
        
        // checkSum
        long totalAmount = document.getPaymentInfos().stream()
            .flatMap(p -> p.getTransactions().stream())
            .mapToLong(SEPATransaction::getAmountInCents)
            .sum();
        addElement(doc, grpHdr, "CtrlSum", formatAmount(totalAmount));
        
        Element initgPty = doc.createElement("InitgPty");
        addElement(doc, initgPty, "Nm", document.getInitiator().getName());
        
        Element id = doc.createElement("Id");
        Element othrId = doc.createElement("OthrId");
        Element othr = doc.createElement("Othr");
        addElement(doc, othr, "Id", document.getInitiator().getId());
        initgPty.appendChild(id);
        id.appendChild(othrId);
        othrId.appendChild(othr);
        
        grpHdr.appendChild(initgPty);
        
        return grpHdr;
    }
    
    private Element createPaymentInfo(Document doc, SEPAPaymentInfo paymentInfo) {
        Element pmtInf = doc.createElement("PmtInf");
        
        addElement(doc, pmtInf, "PmtInfId", paymentInfo.getId());
        addElement(doc, pmtInf, "PmtMtd", "TRF");
        
        int nbTransactions = paymentInfo.getTransactions().size();
        addElement(doc, pmtInf, "NbOfTxns", String.valueOf(nbTransactions));
        
        long totalAmount = paymentInfo.getTransactions().stream()
            .mapToLong(SEPATransaction::getAmountInCents)
            .sum();
        addElement(doc, pmtInf, "CtrlSum", formatAmount(totalAmount));
        
        Element pmtTpInf = doc.createElement("PmtTpInf");
        addElement(doc, pmtTpInf, "InstrPrty", "NORM");
        Element svcLvl = doc.createElement("SvcLvl");
        addElement(doc, svcLvl, "Cd", "SEPA");
        pmtTpInf.appendChild(svcLvl);
        Element lclInstrm = doc.createElement("LclInstrm");
        addElement(doc, lclInstrm, "Cd", "CORE");
        pmtTpInf.appendChild(lclInstrm);
        pmtInf.appendChild(pmtTpInf);
        
        SEPAParty debtor = paymentInfo.getDebtor();
        Element dbtr = doc.createElement("Dbtr");
        addElement(doc, dbtr, "Nm", debtor.getName());
        addPostalAddress(doc, dbtr, debtor.getAddress());
        pmtInf.appendChild(dbtr);
        
        Element dbtrAcct = doc.createElement("DbtrAcct");
        Element idAcct = doc.createElement("Id");
        addElement(doc, idAcct, "IBAN", debtor.getIban());
        dbtrAcct.appendChild(idAcct);
        pmtInf.appendChild(dbtrAcct);
        
        Element dbtrAgt = doc.createElement("DbtrAgt");
        Element finInstnId = doc.createElement("FinInstnId");
        addElement(doc, finInstnId, "BIC", debtor.getBic());
        dbtrAgt.appendChild(finInstnId);
        pmtInf.appendChild(dbtrAgt);
        
        for (SEPATransaction transaction : paymentInfo.getTransactions()) {
            pmtInf.appendChild(createTransaction(doc, transaction));
        }
        
        return pmtInf;
    }
    
    private Element createTransaction(Document doc, SEPATransaction transaction) {
        Element cdtTrfTxnInf = doc.createElement("CdtTrfTxnInf");
        
        Element pmtId = doc.createElement("PmtId");
        addElement(doc, pmtId, "InstrId", transaction.getId());
        addElement(doc, pmtId, "EndToEndId", transaction.getId());
        cdtTrfTxnInf.appendChild(pmtId);
        
        Element amt = doc.createElement("Amt");
        Element instdAmt = doc.createElement("InstdAmt");
        instdAmt.setAttribute("Ccy", "EUR");
        instdAmt.setTextContent(formatAmount(transaction.getAmountInCents()));
        amt.appendChild(instdAmt);
        cdtTrfTxnInf.appendChild(amt);
        
        SEPAParty creditor = transaction.getCreditor();
        Element cdtrAgt = doc.createElement("CdtrAgt");
        Element finInstnId = doc.createElement("FinInstnId");
        addElement(doc, finInstnId, "BIC", creditor.getBic());
        cdtrAgt.appendChild(finInstnId);
        cdtTrfTxnInf.appendChild(cdtrAgt);
        
        Element cdtr = doc.createElement("Cdtr");
        addElement(doc, cdtr, "Nm", creditor.getName());
        addPostalAddress(doc, cdtr, creditor.getAddress());
        cdtTrfTxnInf.appendChild(cdtr);
        
        Element cdtrAcct = doc.createElement("CdtrAcct");
        Element idAcctId = doc.createElement("Id");
        addElement(doc, idAcctId, "IBAN", creditor.getIban());
        cdtrAcct.appendChild(idAcctId);
        cdtTrfTxnInf.appendChild(cdtrAcct);
        
        Element rmtInf = doc.createElement("RmtInf");
        addElement(doc, rmtInf, "Ustrd", transaction.getConcept());
        cdtTrfTxnInf.appendChild(rmtInf);
        
        return cdtTrfTxnInf;
    }
    
    private void addPostalAddress(Document doc, Element parent, SEPAAddress address) {
        if (address == null) return;
        
        Element pstlAdr = doc.createElement("PstlAdr");
        if (address.getCountry() != null) {
            addElement(doc, pstlAdr, "Ctry", address.getCountry());
        }
        if (address.getAddressLine() != null) {
            addElement(doc, pstlAdr, "AdrLine", address.getAddressLine());
        }
        parent.appendChild(pstlAdr);
    }
    
    private void addElement(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.setTextContent(value);
        parent.appendChild(element);
    }
    
    private String formatAmount(long cents) {
        return String.format("%.2f", cents / 100.0);
    }
    
    private String convertDOMToString(Document doc) throws Exception {
        javax.xml.transform.TransformerFactory tf = 
            javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(
            javax.xml.transform.OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(
            javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
        
        java.io.StringWriter sw = new java.io.StringWriter();
        transformer.transform(
            new javax.xml.transform.dom.DOMSource(doc),
            new javax.xml.transform.stream.StreamResult(sw));
        
        return sw.toString();
    }
    
    public void saveToFile(String filename) throws Exception {
        String xml = generateXML();
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(xml);
        }
    }
}