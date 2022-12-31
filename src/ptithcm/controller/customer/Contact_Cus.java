package ptithcm.controller.customer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.Account;

@Controller
@Transactional
public class Contact_Cus {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "user/contact", method = RequestMethod.GET)
	public String contact(ModelMap model, HttpServletRequest request) {
		HttpSession session_web = request.getSession();
		String accountId = (String) session_web.getAttribute("username");
		Account account = getAccount(accountId);
		model.addAttribute("account", account);
		return "contact";
	}
	
	@RequestMapping(value = "user/contact", method = RequestMethod.POST)
	public String submitContact(ModelMap model, HttpServletRequest request) {
		String fullname = request.getParameter("userFullname").trim();
		String email = request.getParameter("userEmail").trim().toLowerCase();
		String address = request.getParameter("userAddress").trim();
		String phone = request.getParameter("userPhone").trim().toLowerCase();
		HttpSession session_web = request.getSession();
		String accountId = (String) session_web.getAttribute("username");
		Account account  = getAccount(accountId);
		ArrayList<Account> list = (ArrayList<Account>) getListAccount();
		//Cac ham kiem tra dieu kien
		int checkName = check_Name(fullname);
		if(checkName==0) {
			model.addAttribute("account", account);
			model.addAttribute("status_update_Name", checkName);
			return "contact";
		}
		int checkEmail = check_Email(list, email, account);
		if(checkEmail==0||checkEmail==2) {
			model.addAttribute("account", account);
			model.addAttribute("status_update_Email", checkEmail);
			return "contact";
		}
		int checkAddress = check_Address(address);
		if(checkAddress==0) {
			model.addAttribute("account", account);
			model.addAttribute("status_update_Address", checkAddress);
			return "contact";
		}
		int checkSDT = check_SDT(list, phone, account);
		if(checkSDT==0||checkSDT==2) {
			model.addAttribute("account", account);
			model.addAttribute("status_update_Phone", checkSDT);
			return "contact";
		}
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		account.setAddress(address);
		account.setName(fullname);
		account.setPhone(phone);
		account.setEmail(email);
		try {
			session.save(account);
			transaction.commit();
		}catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
		}finally {
			session.close();
		}
		model.addAttribute("account", account);
		model.addAttribute("status_update_account", 1);
		return "contact";
	}
	
	@SuppressWarnings("unchecked")
	private List<Account> getListAccount() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account");
		List<Account> list = query.list();
		return list;
	}
	
	private int check_Name(String name) {
		if(name.length()!=0) return 1;
		return 0;
	}
	
	private int check_Address(String address) {
		if(address.length()!=0) return 1;
		return 0;
	}
	
	private int check_Email(ArrayList<Account> list, String email, Account myAccount) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false) return 2;
		if(myAccount.getEmail().equals(email)==false) {
			for(Account item: list) {
				if(item.getEmail().equals(email)) return 0;
			}
		}
		return 1;
	}
	
	private int check_SDT(ArrayList<Account> list, String phone, Account myAccount) {
		if(phone.length()!=10) return 2;
		if(myAccount.getPhone().equals(phone)==false) {
			for(Account item: list) {
				if(item.getPhone().equals(phone)) return 0;
			}
		}
		return 1;
	}
	
	@RequestMapping(value = "user/changepass", method = RequestMethod.GET)
	public String changePass_Get(ModelMap model, HttpServletRequest request) {
		return "change_password";
	}
	
	@RequestMapping(value = "user/changepass", method = RequestMethod.POST)
	public String changePass(ModelMap model, HttpServletRequest request,
			@RequestParam("oldPassword")String oldPass,
			@RequestParam("newPassword")String newPass,
			@RequestParam("rePassword")String rePass) {
		HttpSession httpSession = request.getSession();
		String accountId = (String) httpSession.getAttribute("username");
		Account myAccount = getAccount(accountId);
		if(!validatePassWord(newPass)) {
			model.addAttribute("status_update_Pass", 0);
		}else {
			if((oldPass).equals(myAccount.getPassword())==false) {
				model.addAttribute("status_update_Pass",-1);
			}else {
				if(newPass.equals(rePass)==false) {
					model.addAttribute("status_update_Pass",-2);
				}else {
					myAccount.setPassword(newPass);
					Session session = factory.openSession();
					Transaction transaction = session.beginTransaction();
					try {
						session.update(myAccount);
						transaction.commit();
					}catch (Exception e) {
						transaction.rollback();
						// TODO: handle exception
					}finally {
						session.close();
					}
					model.addAttribute("status_update_Pass", 1);
				}
			}
		}
		return "change_password";
	}
	
	private Account getAccount(String accountId) {
		Session session = factory.getCurrentSession();
		Account account = (Account) session.get(Account.class, accountId);
		return account;
	}
	
	private boolean validatePassWord(String password){
		if(password.isEmpty()){
            return false;
        }
		String regex = "^.{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password );
		if(matcher.matches()==false) return false;
        else {
            return true;
        }
    }
}
