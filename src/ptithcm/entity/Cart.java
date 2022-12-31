package ptithcm.entity;

import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {
	@Id
	@GeneratedValue
	@Column(name = "CartId")
	private Integer cartId;

	@ManyToOne
	@JoinColumn(name = "AccountId")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "BatchId")
	private Batch batch;

	@Column(name = "Amount")
	private Integer amount;


	public Cart() {
	}

	public Cart(Integer cartId, Account account, Batch batch, Integer amount) {
		this.cartId = cartId;
		this.account = account;
		this.batch = batch;
		this.amount = amount;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Long sumPrice() {
		Long price = this.batch.getPrice();
		int discount = this.batch.getDiscount();
		return (price - price*discount/100)*this.amount;
	}
	
	public String sumPriceToString() {
		Long price = sumPrice();
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(price);
	}
}
