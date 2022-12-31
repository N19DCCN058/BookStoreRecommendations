package ptithcm.entity;

import java.text.NumberFormat;
import java.util.Collection;
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

import ptithcm.bean.Record;

@Entity
@Table(name = "Batch")
public class Batch {
	@Id
	@GeneratedValue
	@Column(name = "BatchId")
	private Integer batchId;

	@Column(name = "BatchName")
	private String batchName;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;
	
	@Column(name="Amount")
	private Integer amount;
	
	@Column(name="Sold")
	private Integer sold;
	
	@Column(name="Status")
	private Integer status;
	
	@Column(name = "Describe")
	private String describe;

	@Column(name = "Photo")
	private String photo;
	
	@Column(name="Price")
	private Long price;
	
	@Column(name="Discount")
	private int discount;
	
	@Column(name="Score")
	private int score;
	
	@OneToMany(mappedBy = "batch", fetch = FetchType.EAGER)
	private Collection<Cart> carts;

	@OneToMany(mappedBy = "batch", fetch = FetchType.EAGER)
	private Collection<Product> products;

	public Batch() {
	}

	public Batch(Integer batchId, String batchName, Category category, Integer amount, Integer sold, Integer status,
			String describe, String photo, Long price, int discount, Collection<Cart> carts,
			Collection<Product> products) {
		this.batchId = batchId;
		this.batchName = batchName;
		this.category = category;
		this.amount = amount;
		this.sold = sold;
		this.status = status;
		this.describe = describe;
		this.photo = photo;
		this.price = price;
		this.discount = discount;
		this.carts = carts;
		this.products = products;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public Collection<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Collection<Cart> carts) {
		this.carts = carts;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getSold() {
		return sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long priceDiscount() {
		return this.price - (this.price*this.discount)/100;
	}
	
	public String priceToString() {
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(this.price);
	}
	
	public String priceDiscountToString() {
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(this.price - (this.price*this.discount)/100);
	}
	
	public String batchNameToURL() {
		return this.batchName.toLowerCase().trim().replace(" - ", "-").
				replace(" ", "-").replace("/", "-");
	}

	@Override
	public String toString() {
		return "Batch [batchId=" + batchId + ", batchName=" + batchName + ", category=" + category + ", amount="
				+ amount + ", sold=" + sold + ", status=" + status + ", describe=" + describe + ", photo=" + photo
				+ ", price=" + price + ", discount=" + discount + ", carts=" + carts + ", products=" + products + "]";
	}
	
	public Record toRecord() {
		return new Record(this.batchId,this.category.getCategoryId(),this.price,this.score,this.discount);
	}
	
}
