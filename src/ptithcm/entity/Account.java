package ptithcm.entity;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Account")
public class Account {
	
	@Column(name="Email")
	private String email;
	
	@Id
	@Column(name="AccountId")
	private String accountId;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="Address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name="RoleId")
	private Role role;
	
	@OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
	private Collection<Cart> carts;
	
	@OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
	private Collection<Bill> bills;

	public Account() {
	}

	public Account(String email, String accountId, String password, String name, String phone, String address,
			Role role, Collection<Cart> carts, Collection<Bill> bills) {
		this.email = email;
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.carts = carts;
		this.bills = bills;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Collection<Cart> carts) {
		this.carts = carts;
	}
	
	public Long sumPriceCart() {
		Long sumPrice = 0L;
		for(Cart item: this.carts) {
			sumPrice+=item.sumPrice();
		}
		return sumPrice;
	}
	
	public String sumPriceCartToString() {
		Long price = sumPriceCart();
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(price);
	}

}
