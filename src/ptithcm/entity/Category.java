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
@Table(name = "Category")
public class Category {
	@Id
	@GeneratedValue
	@Column(name = "CategoryId")
	private Integer categoryId;

	@Column(name = "CategoryName")
	private String categoryName;

	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private Collection<Batch> batchs;

	
	public Category() {
	}

	public Category(Integer categoryId, String categoryName, Collection<Batch> batchs) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.batchs = batchs;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Collection<Batch> getBatchs() {
		return batchs;
	}

	public void setBatchs(Collection<Batch> batchs) {
		this.batchs = batchs;
	}
	
	public String categoryNameToURL() {
		return this.categoryName.trim().toLowerCase();
	}
	
	
}
