package ptithcm.controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.bean.DetailsBill;
import ptithcm.entity.Bill;
import ptithcm.entity.Product;

@Controller
@Transactional
public class Bill_Cus {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping( value = "user/listBill")
	public String get_ListBill(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("listBill", getListBill(username));
		return "list_bill";
	}
	
	@RequestMapping(value = "user/mybill/{billId}")
	public String getInfoBill(ModelMap model, HttpServletRequest request, @PathVariable("billId") int billId) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		List<Bill> listBill = getListBill(username);
		Bill bill = findBillById(listBill, billId);
		if(bill!=null) {
			model.addAttribute("bill", bill);
			model.addAttribute("sumPrice", bill.sumMoneyToString());
			model.addAttribute("detailsBill", listProduct(bill));
			return "bill";
		}
		return "redirect:/user/listBill.htm";
	}
	
	private Bill findBillById(List<Bill> list, int billId) {
		for(Bill bill: list) {
			if(bill.getBillId()==billId) return bill;
		}
		return null;
	}
	
	private List<DetailsBill> listProduct(Bill bill){
		List<Product> list = (List<Product>) bill.getProducts();
		List<DetailsBill> a = new ArrayList<DetailsBill>();
		for(int i=0;i<list.size();i++) {
			int vitri = findDetailsBillByNameProduct(a, list.get(i));
			if(vitri==-1) {
				DetailsBill temp = new DetailsBill();
				temp.setNameProduct(list.get(i).getBatch().getBatchName());
				temp.setPhoto(list.get(i).getBatch().getPhoto());
				temp.setSoluong(1);
				temp.setThanhtien(list.get(i).getPrice()*1);
				a.add(temp);
			}else {
				a.get(vitri).setSoluong(a.get(vitri).getSoluong()+1);
				a.get(vitri).setThanhtien(list.get(i).getPrice()*a.get(vitri).getSoluong());
			}
		}
		return a;
	}
	
	private int findDetailsBillByNameProduct(List<DetailsBill> list, Product a) {
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getNameProduct().equals(a.getBatch().getBatchName())) return i;
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	private List<Bill> getListBill(String username){
		List<Bill> listBill = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Bill E WHERE E.account.accountId = " + "'"+ username+"'");
		listBill = query.list();
		return listBill;
	}

}
