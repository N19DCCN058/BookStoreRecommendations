package ptithcm.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.*;

@Controller
@Transactional
@RequestMapping("admin")
public class Dashboard_Ad {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "index")
	public String index(ModelMap model, HttpServletRequest request) {
		model.addAttribute("listAdmin", Admins());
		model.addAttribute("listCustomer", Customers());
		model.addAttribute("listBill", Bills());
		model.addAttribute("listProduct",Products());
		model.addAttribute("getListBill", getListBill());
		model.addAttribute("getListUser", getListUser());
		model.addAttribute("getListProduct", getListProduct());
		return "admin/index";
	}
	
	
	@SuppressWarnings("unchecked")
	private Object Customers() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account U WHERE U.role.roleId = 2");
		List<Account> list = query.list();
		return list;
	}


	private List<Batch> getListProduct() {
		List<Batch> list = Products();
		if(list.size()<=5) return list;
		else {
			List<Batch> temp = new ArrayList<Batch>();
			for(int i=0;i<5;i++)
				temp.add(list.get(i));
			return temp;
		}
	}


	private List<Account> getListUser() {
		List<Account> list = Users();
		if(list.size()<=5) return list;
		else {
			List<Account> temp = new ArrayList<Account>();
			for(int i=0;i<5;i++)
				temp.add(list.get(i));
			return temp;
		}
	}


	private List<Bill> getListBill() {
		List<Bill> list = Bills();
		if(list.size()<=5) return list;
		else {
			List<Bill> temp = new ArrayList<Bill>();
			for(int i=0;i<5;i++)
				temp.add(list.get(i));
			return temp;
		}
	}

	@SuppressWarnings("unchecked")
	private List<Account> Users() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account");
		List<Account> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<Account> Admins(){
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account U WHERE U.role.roleId = 1");
		List<Account> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<Bill> Bills() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Bill");
		List<Bill> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<Batch> Products() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch");
		List<Batch> list = query.list();
		return list;
	}
	

}
