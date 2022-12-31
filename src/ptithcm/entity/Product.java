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
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue
	@Column(name = "ProductId")
	private Integer productId;

	@ManyToOne
	@JoinColumn(name = "BatchId")
	private Batch batch;

	@Column(name = "Price")
	private Long price;

	@Column(name = "Status")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name = "BillId")
	private Bill bill;

	public Product() {
	}

	public Product(Integer productId, Batch batch, Long price, Integer status, Bill bill) {
		this.productId = productId;
		this.batch = batch;
		this.price = price;
		this.status = status;
		this.bill = bill;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	public String PriceToString() {
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(this.price);
	}

}
