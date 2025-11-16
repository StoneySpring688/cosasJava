package stoney.avancePracticas.sepa.dominio;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Encabezado {
	@XmlElement(name = "MsgId", required = true)
	private String MsgId;
	@XmlElement(name = "CreDtTm", required = true)
	private LocalDateTime CreationDateTime;
	@XmlElement(name = "NbOfTxs", required = true)
	private int numberOfTransactions;
	@XmlElement(name = "CtrlSum", required = true)
	private double controlSum;
	@XmlElement(name = "InitgPty", required = true)
	private Party parteIniciadora;
	
	public Encabezado() {}
	
	public Encabezado(String msgId, LocalDateTime creationDateTime, int numberOfTransactions, double controlSum,
			Party parteIniciadora) {
		MsgId = msgId;
		CreationDateTime = creationDateTime;
		this.numberOfTransactions = numberOfTransactions;
		this.controlSum = controlSum;
		this.parteIniciadora = parteIniciadora;
	}

	public String getMsgId() {
		return MsgId;
	}

	public LocalDateTime getCreationDateTime() {
		return CreationDateTime;
	}

	public int getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public double getControlSum() {
		return controlSum;
	}

	public Party getParteIniciadora() {
		return parteIniciadora;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		CreationDateTime = creationDateTime;
	}

	public void setNumberOfTransactions(int numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}

	public void setControlSum(double controlSum) {
		this.controlSum = controlSum;
	}

	public void setParteIniciadora(Party parteIniciadora) {
		this.parteIniciadora = parteIniciadora;
	}
	
}
