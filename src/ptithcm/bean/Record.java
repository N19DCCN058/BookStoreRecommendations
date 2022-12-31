package ptithcm.bean;


public class Record {
	
	private int id;
	private int category;
	private long price;
	private int score;
	private int discount;
	private int clusterNumber;
	
	
	
	
	public Record(int id,int category, long price, int score, int discount) {
		super();
		this.id = id;
		this.category = category;
		this.score = score;
		this.price = price;
		this.discount = discount;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public long getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getDiscount() {
		return this.discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getClusterNumber() {
		return this.clusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

}
