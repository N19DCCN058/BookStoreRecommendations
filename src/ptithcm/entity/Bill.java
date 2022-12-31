package ptithcm.entity;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Bill")
public class Bill {
	@Id
	@GeneratedValue
	@Column(name = "BillId")
	private Integer billId;

	@ManyToOne
	@JoinColumn(name = "AccountId")
	private Account account;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "Date")
	private Date date;
	
	@Column(name="Note")
	private String note;
	
	@Column(name="CustomerName")
	private String customerName;
	
	@Column(name="CustomerEmail")
	private String customerEmail;
	
	@Column(name="CustomerAddress")
	private String customerAddress;
	
	@Column(name="CustomerPhone")
	private String customerPhone;
	
	@ManyToOne
	@JoinColumn(name = "PaymentId")
	private Payment_Methods payment_Methods;

	@OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
	private Collection<Product> products;
	
	public Bill() {
	}

	public Bill(Integer billId, Account account, Date date, String note, String customerName, String customerEmail,
			String customerAddress, String customerPhone, Payment_Methods payment_Methods,
			Collection<Product> products) {
		this.billId = billId;
		this.account = account;
		this.date = date;
		this.note = note;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerAddress = customerAddress;
		this.customerPhone = customerPhone;
		this.payment_Methods = payment_Methods;
		this.products = products;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Payment_Methods getPayment_Methods() {
		return payment_Methods;
	}

	public void setPayment_Methods(Payment_Methods payment_Methods) {
		this.payment_Methods = payment_Methods;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Long sumMoney() {
		Long sum = 0L;
		for(Product e: this.products) {
			sum = sum +e.getPrice().intValue();
		}
		return sum;
	}
	
	public String sumMoneyToString() {
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(sumMoney());
	}
	
}
