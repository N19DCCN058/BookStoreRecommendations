package ptithcm.bean;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailsBill {
	String nameProduct;
	String photo;
	int soluong;
	Long thanhtien;
	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public Long getThanhtien() {
		return thanhtien;
	}
	public void setThanhtien(Long thanhtien) {
		this.thanhtien = thanhtien;
	}
	public DetailsBill(String nameProduct, String photo, int soluong, Long thanhtien) {
		super();
		this.nameProduct = nameProduct;
		this.photo = photo;
		this.soluong = soluong;
		this.thanhtien = thanhtien;
	}
	
	public DetailsBill() {
		super();
	}
	
	public String thanhtienToString() {
		Locale localeEN = new Locale("en", "EN");
	    NumberFormat en = NumberFormat.getInstance(localeEN);
	    return en.format(thanhtien);
	}
}
