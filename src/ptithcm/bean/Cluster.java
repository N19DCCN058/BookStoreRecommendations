package ptithcm.bean;

import ptithcm.entity.Batch;

public class Cluster {

	private int categoryCentroid;
	private long priceCentroid;
	private int discountCentroid;
	private int scoreCentroid;
	private int clusterNumber;
	
	

	public Cluster(int categoryCentroid, long priceCentroid, int scoreCentroid, int discountCentroid, int clusterNumber) {
		this.categoryCentroid = categoryCentroid;
		this.priceCentroid = priceCentroid;
		this.scoreCentroid = scoreCentroid;
		this.discountCentroid = discountCentroid;
		this.clusterNumber = clusterNumber;
	}
	

	public int getCategoryCentroid() {
		return this.categoryCentroid;
	}

	public void setCategoryCentroid(int categoryCentroid) {
		this.categoryCentroid = categoryCentroid;
	}

	public long getPriceCentroid() {
		return this.priceCentroid;
	}

	public void setPriceCentroid(long priceCentroid) {
		this.priceCentroid = priceCentroid;
	}

	public int getScoreCentroid() {
		return this.scoreCentroid;
	}

	public void setScoreCentroid(int scoreCentroid) {
		this.scoreCentroid = scoreCentroid;
	}

	public int getdiscountCentroid() {
		return this.discountCentroid;
	}

	public void setdiscountCentroid(int discountCentroid) {
		this.discountCentroid = discountCentroid;
	}

	public int getClusterNumber() {
		return this.clusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

	
	// Euclidean distance calculation
	public double calculateDistance(Record record) {
		return Math.sqrt(Math.pow((getCategoryCentroid() - record.getCategory()), 2) + Math.pow((getPriceCentroid() - record.getPrice()),2) + Math.pow((getScoreCentroid() - record.getScore()), 2)+Math.pow((getdiscountCentroid() - record.getDiscount()), 2));
    }

	// Binod Suman Academy YouTube Video on K-Mean Algorithm
	public void updateCentroid(Record record) {
		setCategoryCentroid((getCategoryCentroid()+record.getCategory())/2);
		setPriceCentroid((getPriceCentroid()+record.getPrice())/2);
		setScoreCentroid((getScoreCentroid()+record.getScore())/2); 
		setdiscountCentroid((getdiscountCentroid()+record.getDiscount())/2);
	}

}