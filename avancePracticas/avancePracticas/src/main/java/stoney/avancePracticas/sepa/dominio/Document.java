package stoney.avancePracticas.sepa.dominio;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "encabezado", "paymentInfos" })
public class Document {
	@XmlElement(name = "GrpHdr", required = true)
	Encabezado encabezado;
	@XmlElement(name = "PmtInf", required = true)
	List<PaymentInfo> paymentInfos;
	
	public Document(Encabezado encabezado, List<PaymentInfo> paymentInfos) {
		super();
		this.encabezado = encabezado;
		this.paymentInfos = paymentInfos;
	}

	public Encabezado getEncabezado() {
		return encabezado;
	}

	public List<PaymentInfo> getPaymentInfos() {
		return paymentInfos;
	}
	
	public void setEncabezado(Encabezado encabezado) {
		this.encabezado = encabezado;
	}

	public void setPaymentInfos(List<PaymentInfo> paymentInfos) {
		this.paymentInfos = paymentInfos;
	}
	
	public void addPaymentInfo(PaymentInfo paymentInfo) {
		if (this.paymentInfos == null) {
			this.paymentInfos = new ArrayList<>();
		}
		this.paymentInfos.add(paymentInfo);
	}
	
}
