package ptithcm.controller.admin;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping(value = "admin")
public class Bill_Ad {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "listbill")
	public String getListBill(ModelMap model) {
		model.addAttribute("listBill",getListBill());
		return "admin/list_bill";
	}

	@RequestMapping(value = "bill/{billId}")
	public String infoBill(ModelMap model, @PathVariable("billId")Integer BillId) {
		Session session = factory.getCurrentSession();
		Bill bill = (Bill)session.get(Bill.class, BillId);
		if(bill!=null) {
			model.addAttribute("bill",bill);
			model.addAttribute("sumPrice", bill.sumMoneyToString());
			model.addAttribute("detailsBill", listProduct(bill));
			return "admin/bill";
		}else {
			return "redirect:/admin/listbill.htm";
		}
		
	}
	private Object listProduct(Bill bill) {
		// TODO Auto-generated method stub
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
	private Object getListBill() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Bill");
		List<Bill> list = query.list();
		return list;
	}

}
