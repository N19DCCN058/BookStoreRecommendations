package ptithcm.controller.customer;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.Account;
import ptithcm.entity.Batch;
import ptithcm.entity.Bill;
import ptithcm.entity.Cart;
import ptithcm.entity.Payment_Methods;
import ptithcm.entity.Product;

@Controller
@Transactional
@RequestMapping("user/mycart")
public class Cart_Cus {
	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String get_Cart(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String accountId = (String) session.getAttribute("username");
		model.addAttribute("role",(String) session.getAttribute("role"));
		if(accountId==null) return "redirect:/trangchu.htm";
		model.addAttribute("listCarts",getAccount(accountId).getCarts());
		model.addAttribute("sumPrice",getAccount(accountId).sumPriceCartToString());
		return "cart";
	}
	
	private Account getAccount(String accountId) {
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, accountId);
		return account;
	}
	
	@RequestMapping(value = "removeAll")
	public String removeAllCart(ModelMap model, HttpServletRequest request) {
		HttpSession session_web = request.getSession();
		String accountId = (String) session_web.getAttribute("username");
		List<Cart> list = (List<Cart>) getAccount(accountId).getCarts();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			for(Cart item: list) {
				session.delete(item);
			}
			transaction.commit();
		}catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
		}finally {
			session.close();
		}
		return "redirect:/user/mycart.htm";
	}
	
	@RequestMapping(value = "addCart/{batchName}")
	public String addCartProduct(ModelMap model, @PathVariable("batchName") String batchName, HttpServletRequest request) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		HttpSession session_web = request.getSession();
		String accountId = (String) session_web.getAttribute("username");
		Account account = getAccount(accountId);
		Batch batch = getBatch(batchName);
		if(batch!=null) {
			if(batch.getStatus()==1) {
				Cart cart = new Cart();
				cart.setBatch(batch);
				cart.setAmount(1);
				cart.setAccount(account);
				List<Cart> list = (List<Cart>) getAccount(accountId).getCarts();
				Cart findCart = checkIsExist(list, batch);
				if(findCart!=null) {
					if((batch.getAmount()-batch.getSold())<=findCart.getAmount()) {
						
					}else {
						findCart.setAmount(findCart.getAmount()+1);
						try {
							session.update(cart);
							transaction.commit();
						}catch (Exception e) {
							// TODO: handle exception
							transaction.rollback();
						}finally {
							session.close();
						}
					}
					
				}else {
					try {
						session.save(cart);
						transaction.commit();
					}catch (Exception e) {
						// TODO: handle exception
						transaction.rollback();
					}finally {
						session.close();
					}
				}
			}
		}
		return "redirect:/user/mycart.htm";
	}
	@RequestMapping(value = "addCartRC/{batchName}")
	public String addCartProductRecommendation(ModelMap model, @PathVariable("batchName") String batchName, HttpServletRequest request) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		HttpSession session_web = request.getSession();
		String accountId = (String) session_web.getAttribute("username");
		Account account = getAccount(accountId);
		Batch batch = getBatch(batchName);
		if(batch!=null) {
			
			if(batch.getStatus()==1) {
				Cart cart = new Cart();
				cart.setBatch(batch);
				cart.setAmount(1);
				cart.setAccount(account);
				List<Cart> list = (List<Cart>) getAccount(accountId).getCarts();
				Cart findCart = checkIsExist(list, batch);
				if(findCart!=null) {
					if((batch.getAmount()-batch.getSold())<=findCart.getAmount()) {
						
					}else {
						findCart.setAmount(findCart.getAmount()+1);
						try {
							session.update(cart);
							transaction.commit();
						}catch (Exception e) {
							// TODO: handle exception
							transaction.rollback();
						}finally {
							session.close();
						}
					}
					
				}else {
					try {
						session.save(cart);
						transaction.commit();
					}catch (Exception e) {
						// TODO: handle exception
						transaction.rollback();
					}finally {
						session.close();
					}
				}
			}
			HttpSession web_session = request.getSession();
			session = factory.openSession();
			transaction = session.beginTransaction();
			batch = (Batch) session.get(Batch.class, batch.getBatchId());
			batch.setScore(batch.getScore() - 5);
			try {
				session.update(batch);
				transaction.commit();
				model.addAttribute("status_update_batch", 1);
			}catch (Exception e) {
				transaction.rollback();
				// TODO: handle exception
				model.addAttribute("status_update_batch", 0);
			}finally {
				session.close();
			}
		}
		return "redirect:/user/mycart.htm";
	}
	
	@RequestMapping(value = "{cartId}/{type}")
	public String changeAmount(ModelMap model, @PathVariable("cartId") Integer cartId, @PathVariable("type") String type,
			HttpServletRequest request) {
		HttpSession session_web = request.getSession();
		String accountId = (String) session_web.getAttribute("username");
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Cart cart = (Cart) session.get(Cart.class, cartId);
		if(cart==null) {
			return "redirect:/user/mycart.htm";
		}
		else {
			if(!cart.getAccount().getAccountId().equals(accountId)) {
				return "redirect:/user/mycart.htm";
			}
			try {
				if(type.equals("remove")) {
					session.delete(cart);
				}else if(type.equals("add")) {
					Batch batch = cart.getBatch();
					if(cart.getAmount()>=(batch.getAmount()-batch.getSold())) {
						
					}else {
						cart.setAmount(cart.getAmount()+1);
						session.update(cart);
					}
				}else if(type.equals("sub")){
					cart.setAmount(cart.getAmount()-1);
					if(cart.getAmount()==0)
						session.delete(cart);
					else session.update(cart);
				}
				transaction.commit();
			}catch (Exception e) {
				transaction.rollback();
				// TODO: handle exception
			}finally {
				session.close();
			}
		}
		return "redirect:/user/mycart.htm";
	}
	
	@RequestMapping(value="buy", method = RequestMethod.GET)
	public String get_Buy(ModelMap model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String accountId = (String) session.getAttribute("username");
		if(accountId==null) return "redirect:/trangchu.htm";
		if(getAccount(accountId).getCarts().size()==0) {
			return "redirect:/trangchu.htm";
		}
		model.addAttribute("paymentMethods", getListPayment());
		model.addAttribute("listCarts",getAccount(accountId).getCarts());
		model.addAttribute("sumPrice",getAccount(accountId).sumPriceCartToString());
		model.addAttribute("account", getAccount(accountId));
		return "check_contact_buy";
	}
	
	@SuppressWarnings("unchecked")
	private Object getListPayment() {
		List<Payment_Methods> list = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Payment_Methods");
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "buy", method = RequestMethod.POST)
	public String buy(ModelMap model, HttpServletRequest request,
			@RequestParam("payment") Integer paymentId, @RequestParam("note") String note,
			@RequestParam("userFullname") String Fullname, @RequestParam("userEmail") String Email,
			@RequestParam("userAddress") String Address, @RequestParam("userPhone")String Phone) {
		HttpSession web_Session = request.getSession();
		String accountId = (String) web_Session.getAttribute("username");
		Account myAccount = getAccount(accountId);
		List<Cart> myCart = (List<Cart>) myAccount.getCarts();
		if (myCart.size() == 0) {
			return "redirect:/user/mycart.htm";
		}
		// Cac ham kiem tra dieu kien
		int checkName = check_Name(Fullname);
		if (checkName == 0) {
			model.addAttribute("paymentMethods", getListPayment());
			model.addAttribute("listCarts", getAccount(accountId).getCarts());
			model.addAttribute("sumPrice", getAccount(accountId).sumPriceCartToString());
			model.addAttribute("account", getAccount(accountId));
			model.addAttribute("status_buy_Name", checkName);
			return "check_contact_buy";
		}
		int checkEmail = check_Email(Email);
		if (checkEmail == 0) {
			model.addAttribute("paymentMethods", getListPayment());
			model.addAttribute("listCarts", getAccount(accountId).getCarts());
			model.addAttribute("sumPrice", getAccount(accountId).sumPriceCartToString());
			model.addAttribute("account", getAccount(accountId));
			model.addAttribute("status_buy_Email", checkEmail);
			return "check_contact_buy";
		}
		int checkAddress = check_Address(Address);
		if (checkAddress == 0) {
			model.addAttribute("paymentMethods", getListPayment());
			model.addAttribute("listCarts", getAccount(accountId).getCarts());
			model.addAttribute("sumPrice", getAccount(accountId).sumPriceCartToString());
			model.addAttribute("account", getAccount(accountId));
			model.addAttribute("status_buy_Address", checkAddress);
			return "check_contact_buy";
		}
		int checkSDT = check_SDT(Phone);
		if (checkSDT == 0) {
			model.addAttribute("paymentMethods", getListPayment());
			model.addAttribute("listCarts", getAccount(accountId).getCarts());
			model.addAttribute("sumPrice", getAccount(accountId).sumPriceCartToString());
			model.addAttribute("account", getAccount(accountId));
			model.addAttribute("status_buy_Phone", checkSDT);
			return "check_contact_buy";
		}
		
		Bill myBill = new Bill();
		myBill.setAccount(myAccount);
		myBill.setDate(new Date());
		List<Payment_Methods> payment_Methods = (List<Payment_Methods>) getListPayment();
		myBill.setPayment_Methods(findPaymentById(payment_Methods, paymentId));
		myBill.setNote(note);
		myBill.setCustomerName(Fullname);
		myBill.setCustomerAddress(Address);
		myBill.setCustomerEmail(Email);
		myBill.setCustomerPhone(Phone);
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(myBill);
			for(Cart a: myCart) {
				List<Product> listProduct = (List<Product>) a.getBatch().getProducts();
				for(int i=1;i<=a.getAmount();i++) {
					for(Product temp: listProduct) {
						if(temp.getStatus()==1) {
							temp.setBill(myBill);
							temp.setStatus(2);
							session.update(temp);
							break;
						}
					}
				}
				session.delete(a);
			}
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			// TODO: handle exception
		}finally {
			session.close();
		}
		
		session = factory.openSession();
		transaction = session.beginTransaction();
		try {
			for(Cart a: myCart) {
				Batch batch = a.getBatch();
				batch.setSold(batch.getSold()+a.getAmount());
				if(batch.getAmount()==batch.getSold()) batch.setStatus(2);
				session.update(batch);
			}
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			// TODO: handle exception
		}finally {
			session.close();
		}
		return "redirect:/user/mycart.htm";
	}
	
	private int check_Name(String name) {
		name = name.trim();
		if(name.length()!=0) return 1;
		return 0;
	}
	
	private int check_Address(String address) {
		address = address.trim();
		if(address.length()!=0) return 1;
		return 0;
	}
	
	private int check_Email(String email) {
		email = email.trim().toLowerCase();
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false) return 0;
		return 1;
	}
	
	private int check_SDT(String phone) {
		if(phone.length()!=10) return 0;
		return 1;
	}
	
	private Payment_Methods findPaymentById(List<Payment_Methods> a, Integer id) {
		for(Payment_Methods item: a) {
			if(item.getPaymentId()==id) return item;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Batch getBatch(String batchName) {
		List<Batch> listBatch = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch");
		listBatch = query.list();
		return findByBatchId(listBatch, batchName);
	}
	
	private Batch findByBatchId(List<Batch> list, String batchName) {
		for(Batch item: list) {
			if(item.batchNameToURL().equals(batchName)) return item;
		}
		return null;
	}
	
	private Cart checkIsExist(List<Cart> list, Batch batch) {
		for(Cart cart: list) 
			if(cart.getBatch().equals(batch)) return cart;
		return null;
	}
}
