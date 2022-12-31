package ptithcm.controller.admin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.bean.Mailer;
import ptithcm.entity.*;

@Controller
@Transactional
public class Login {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	Mailer mailer;
	
	@RequestMapping(value = "dangnhap", method = RequestMethod.GET)
	public String getpage_Login(ModelMap model) {
		return "admin/login";
	}
	
	@RequestMapping(value = "dangnhap", method = RequestMethod.POST)
	public String login(ModelMap model, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session_web = request.getSession();
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, username);
		if(account!=null) {
			if(account.getPassword().equals(password)) {
				session_web.setAttribute("role", account.getRole().getRoleName());
				session_web.setAttribute("username", account.getAccountId());
				session_web.setAttribute("fullname", account.getName());
				if(account.getRole().getRoleName().equalsIgnoreCase("admin")) {
					return "redirect:/admin/index.htm";
				}
				else
					return "redirect:/trangchu.htm";
			}
		}
		session_web.setAttribute("status_login", 0);
		return "admin/login";
	}
	
	@RequestMapping(value = "dangxuat")
	public String logout(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("role", null);
		session.setAttribute("username", null);
		session.setAttribute("fullname", null);
		return "redirect:/trangchu.htm";
	}
	
	@RequestMapping(value = "dangki", method = RequestMethod.GET)
	public String getpage_dangki(ModelMap model) {
		
		return "admin/dangki";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "dangki", method = RequestMethod.POST)
	public String dangki(ModelMap model, @RequestParam("username") String accountId, 
			@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("address")String address, @RequestParam("password") String password, HttpServletRequest request) {
		name = name.trim();
		email = email.trim().toLowerCase();
		phone = phone.trim();
		address = address.trim();
		accountId = accountId.trim();
		if(check_Username(accountId)==0) {
			model.addAttribute("status_dangki_username", 0);
			return "admin/dangki";
		}
		if (check_Email(email) == 0) {
			model.addAttribute("status_dangki_email", 0);
			return "admin/dangki";
		}
		if (check_Name(name) == 0) {
			model.addAttribute("status_dangki_name", 0);
			return "admin/dangki";
		}
		if (check_SDT(phone) == 0) {
			model.addAttribute("status_dangki_phone", 0);
			return "admin/dangki";
		}
		if (check_Address(address) == 0) {
			model.addAttribute("status_dangki_address", 0);
			return "admin/dangki";
		}
		if (check_PassWord(password) == 0) {
			model.addAttribute("status_dangki_password", 0);
			return "admin/dangki";
		}
		
		Account account = new Account();
		ArrayList<Account> listAccount = (ArrayList<Account>) getListAccount();
		if(check_Trung_Username(listAccount, accountId)) {
			model.addAttribute("errorUsername",1);
			return "admin/dangki";
		}
		if(check_Trung_Email(listAccount, email)) {
			model.addAttribute("errorEmail",1);
			return "admin/dangki";
		}
		if(check_trung_sdt(listAccount, phone)) {
			model.addAttribute("errorPhone",1);
			return "admin/dangki";
		}
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		account.setAccountId(accountId);
		Role role = (Role) session.get(Role.class, 2);
		account.setRole(role);
		account.setAddress(address);
		account.setEmail(email);
		account.setName(name);
		account.setPassword(password);
		account.setPhone(phone);
		try {
			session.save(account);
			transaction.commit();
			
		}catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			return "admin/dangki";
		}finally {
			session.close();
		}
		HttpSession web_session = request.getSession();
		web_session.setAttribute("status_add_account",1);
		return "redirect:/dangnhap.htm";
	}
	
	@SuppressWarnings("unchecked")
	private Object getListAccount() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account");
		List<Account> list = query.list();
		return list;
	}
	
	private boolean check_Trung_Username(ArrayList<Account> list, String username) {
		for(Account item: list) {
			if(item.getAccountId().equals(username)) return true;
		}
		return false;
	}
	
	private boolean check_Trung_Email(ArrayList<Account> list, String email) {
		for(Account item: list) {
			if(item.getEmail().equals(email)) return true;
		}
		return false;
	}
	
	private boolean check_trung_sdt(ArrayList<Account> list, String phone) {
		for(Account item: list) {
			if(item.getPhone().equals(phone)) return true;
		}
		return false;
	}
	
	@RequestMapping(value = "quenmatkhau", method = RequestMethod.GET)
	public String getpage_quenmatkhau(ModelMap model) {
		return "admin/forgot_password";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "quenmatkhau", method = RequestMethod.POST)
	public String quenmatkhau(ModelMap model, HttpServletRequest request, @RequestParam("email")String email) {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Recovery_Password recovery_Password = (Recovery_Password) session.get(Recovery_Password.class, email);
		String hql = "FROM Account";
		Query query = session.createQuery(hql);
		List<Account> accounts = query.list();
		HttpSession se = request.getSession();
		boolean check = false;
		for (Account e : accounts) {
			if (e.getEmail().trim().equals(email)) {
				check = true;
			}
		}
		if(check==true) {
			if(recovery_Password==null) {
				try {
					recovery_Password = new Recovery_Password();
					recovery_Password.setEmail(email);
					recovery_Password.setCode(String.format("%06d", number));
					session.save(recovery_Password);
					transaction.commit();
					try {
						mailer.send("ptitbookstore@gmail.com", email, "forgotpassword", recovery_Password.getCode());
//						model.addAttribute("Email", recovery_Password.getEmail());
						se.setAttribute("Email", recovery_Password.getEmail());
					} catch (Exception e) {
						// TODO: handle exception
						model.addAttribute("status_send_email", -1);
						return "admin/forgot_password";
					}
				} catch (Exception e) {
					// TODO: handle exception
					transaction.rollback();
				} finally {
					session.close();
				}
			}else {
				try {
					recovery_Password.setCode(String.format("%06d", number));
					session.update(recovery_Password);
					transaction.commit();
					try {
						mailer.send("ptitbookstore@gmail.com", email, "forgotpassword", recovery_Password.getCode());
//						model.addAttribute("Email", recovery_Password.getEmail());
						se.setAttribute("Email", recovery_Password.getEmail());
					} catch (Exception e) {
						// TODO: handle exception
						model.addAttribute("status_send_email", -1);
						return "user/forgotpasswd";
					}
				} catch (Exception e) {
					// TODO: handle exception
					transaction.rollback();
				} finally {
					session.close();
				}
			}
		}else {
			model.addAttribute("status_send_email", 0);
			return "admin/forgot_password";
		}
		return "admin/submitCode";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "submitCode", method = RequestMethod.POST)
	public String submitCode(ModelMap model, HttpServletRequest request, @RequestParam("code")Integer code,
			@RequestParam("password")String password) {
		HttpSession se = request.getSession();
		String email = (String)se.getAttribute("Email");
		ArrayList<Account> listAccount = (ArrayList<Account>) getListAccount();
		Session session = factory.openSession();
		Transaction tr = session.beginTransaction();
		Recovery_Password recovery_Password = (Recovery_Password) session.get(Recovery_Password.class, email);
		System.out.println(recovery_Password.getCode());
		Account account = findByEmail(listAccount, email);
		if(code==null||recovery_Password.getCode().equals(String.valueOf(code))==false) {
			model.addAttribute("status_code", 0);
			return "admin/submitCode";
		}
		if(check_PassWord(password)==0) {
			model.addAttribute("status_password", 0);
			return "admin/submitCode";
		}
		if(String.valueOf(code).equals(recovery_Password.getCode())) {
			try {
				account.setPassword(password);
				session.update(account);
				tr.commit();
				se.setAttribute("Email", null);
			}catch (Exception e) {
				tr.rollback();
				// TODO: handle exception
			}finally {
				session.close();
			}
		}
		return "redirect:/dangnhap.htm";
	}
	
	private Account findByEmail(ArrayList<Account> list, String email) {
		for(Account account: list) if(account.getEmail().equals(email)) return account;
		return null;
	}
	
	private int check_Name(String name) {
		if(name.length()!=0) return 1;
		return 0;
	}
	
	private int check_Username(String username) {
		String regex = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		if(matcher.matches()==false) return 0;
		return 1;
		
	}
	
	private int check_Address(String address) {
		if(address.length()!=0) return 1;
		return 0;
	}
	
	private int check_Email(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false) return 0;
		return 1;
	}
	
	private int check_SDT( String phone) {
		if(phone.length()!=10) return 0;
		return 1;
	}
	
	private int check_PassWord(String password){
		if(password.isEmpty()){
            return 0;
        }
		String regex = "^.{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		if(matcher.matches()==false) return 0;
        else {
            return 1;
        }
    }

}
