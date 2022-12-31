package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Payment_Methods")
public class Payment_Methods {
	@Id
	@GeneratedValue
	@Column(name = "PaymentId")
	private Integer paymentId;
	
	@Column(name="PaymentName")
	private String paymentName;
	
	@OneToMany(mappedBy = "payment_Methods", fetch = FetchType.EAGER)
	private Collection<Bill> bills;

	public Payment_Methods() {
	}

	public Payment_Methods(Integer paymentId, String paymentName, Collection<Bill> bills) {
		this.paymentId = paymentId;
		this.paymentName = paymentName;
		this.bills = bills;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}
	
	
}
