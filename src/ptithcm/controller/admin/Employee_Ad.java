package ptithcm.controller.admin;

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
import ptithcm.entity.Role;

@Controller
@Transactional
@RequestMapping(value = "admin")
public class Employee_Ad {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "listuser")
	public String getListUser(ModelMap model) {
		model.addAttribute("listUser", getListUser());
		return "admin/list_account";
	}
	
	@RequestMapping(value = "newaccount", method = RequestMethod.GET)
	public String loadpage_NewAccount(ModelMap model) {
		return "admin/add_account";
	}
	
	@RequestMapping(value = "newaccount", method = RequestMethod.POST)
	public String addNewAccount(ModelMap model, @RequestParam("username")String accountId,
			@RequestParam("email")String email, @RequestParam("name")String fullname,
			@RequestParam("phone")String phone, @RequestParam("address")String address,
			@RequestParam("password")String password, HttpServletRequest request) {
		ArrayList<Account> listAccount = (ArrayList<Account>) getListUser();
		int checkusername = check_username(listAccount, accountId);
		if(checkusername==0||checkusername==2) {
			model.addAttribute("status_add_account_username", checkusername);
			return "admin/add_account";
		}
		int checkEmail = check_email(listAccount, email);
		if(checkEmail==0||checkEmail==2) {
			model.addAttribute("status_add_account_email", checkEmail);
			return "admin/add_account";
		}
		int checkName = check_name(fullname);
		if(checkName==0) {
			model.addAttribute("status_add_account_name", checkName);
			return "admin/add_account";
		}
		int checkPhone = check_phone(listAccount, phone);
		if(checkPhone==0||checkPhone==2) {
			model.addAttribute("status_add_account_phone", checkPhone);
			return "admin/add_account";
		}
		int checkAddress = check_address(address);
		if(checkAddress==0) {
			model.addAttribute("status_add_account_address", checkAddress);
			return "admin/add_account";
		}

		int checkPassword = check_password(password);
		if(checkPassword==0) {
			model.addAttribute("status_add_account_password", checkPassword);
			return "admin/add_account";
		}
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		HttpSession sessionWeb = request.getSession();
		Account account = new Account();
		account.setAccountId(accountId);
		account.setEmail(email);
		account.setRole((Role) session.get(Role.class, 1));
		account.setName(fullname);
		account.setAddress(address);
		account.setPhone(phone);
		account.setPassword(password);
		try {
			session.save(account);
			transaction.commit();
			sessionWeb.setAttribute("status_add_account", 1);
		}catch (Exception e) {
			transaction.rollback();
			sessionWeb.setAttribute("status_add_account", 0);
			// TODO: handle exception
		}finally {
			session.close();
		}
		return "redirect:/admin/listuser.htm";
	}
	
	@RequestMapping(value = "profile")
	public String showProfile(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Session se = factory.getCurrentSession();
		String accountId = (String) session.getAttribute("username");
		model.addAttribute("myaccount", (Account) se.get(Account.class, accountId));
		return "admin/profile";
	}
	
	@RequestMapping(value = "editprofile", method = RequestMethod.GET)
	public String loadpage_editProfile(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Session se = factory.getCurrentSession();
		String accountId = (String) session.getAttribute("username");
		model.addAttribute("myaccount", (Account)se.get(Account.class, accountId));
		return "admin/edit_profile";
	}
	
	@RequestMapping(value = "editprofile", method = RequestMethod.POST)
	public String editProfile(ModelMap model, HttpServletRequest request,
			@RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("phone") String phone, @RequestParam("address") String address) {
		ArrayList<Account> listAccount = (ArrayList<Account>)getListUser();
		HttpSession sessionWeb = request.getSession();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		String username = (String) sessionWeb.getAttribute("username");
		Account myAccount = (Account) session.get(Account.class, username);
		int check_email = check_email(listAccount, email, myAccount);
		if(check_email==0||check_email==2) {
			model.addAttribute("myaccount", myAccount);
			model.addAttribute("status_update_account_email", check_email);
			return "admin/edit_profile";
		}
		int check_phone = check_phone(listAccount, phone, myAccount);
		if(check_phone==0||check_phone==2) {
			model.addAttribute("myaccount", myAccount);
			model.addAttribute("status_update_account_phone", check_phone);
			return "admin/edit_profile";
		}
		int check_name = check_name(name);
		if(check_name==0) {
			model.addAttribute("myaccount", myAccount);
			model.addAttribute("status_update_account_name", check_name);
			return "admin/edit_profile";
		}
		int check_address = check_address(address);
		if(check_address==0) {
			model.addAttribute("myaccount", myAccount);
			model.addAttribute("status_update_account_address", check_address);
			return "admin/edit_profile";
		}
		myAccount.setAddress(address);
		myAccount.setEmail(email);
		myAccount.setAddress(address);
		myAccount.setName(name);
		try {
			session.update(myAccount);
			transaction.commit();
			model.addAttribute("myaccount", myAccount);
			model.addAttribute("status_update_account",1);
		}catch (Exception e) {
			System.out.print(e.getMessage());
			transaction.rollback();
			model.addAttribute("myaccount", myAccount);
			model.addAttribute("status_update_account",0);
			// TODO: handle exception
		}finally {
			session.close();
		}
		return "admin/edit_profile";
	}
	
	@RequestMapping(value = "changepass", method = RequestMethod.GET)
	public String loadpage_changePass(ModelMap model) {
		return "admin/changepassword";
	}
	
	
	@RequestMapping(value = "changepass",method = RequestMethod.POST)
	public String changePass(ModelMap model, HttpServletRequest request,
			@RequestParam("oldPassword") String oldPass,
			@RequestParam("newPassword")String newPass,
			@RequestParam("rePassword")String rePass) {
		HttpSession httpSession = request.getSession();
		String accountId = (String) httpSession.getAttribute("username");
		Session se = factory.getCurrentSession();
		Account myAccount = (Account) se.get(Account.class, accountId);
		if(check_password(newPass)==0) {
			model.addAttribute("status_update_Pass", 0);
		}else {
			if((oldPass).equals(myAccount.getPassword())==false) {
				model.addAttribute("status_update_Pass",-1);
			}else {
				if(newPass.equals(rePass)==false) {
					model.addAttribute("status_update_Pass",-2);
				}else {
					myAccount.setPassword((newPass));
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
		return "admin/changepassword";
	}
	
	private int check_username(ArrayList<Account> listAccount, String username) {
		String regex = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		if(matcher.matches()==false) return 0;
		for(Account account: listAccount) if(account.getAccountId().equals(username)) return 2;
		return 1;
	}
	
	private int check_email(ArrayList<Account> listAccount, String email) {
		email = email.trim().toLowerCase();
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false) return 0;
		for(Account account: listAccount) if(account.getEmail().equals(email)) return 2;
		return 1;
	}
	
	private int check_name(String name) {
		name = name.trim();
		if(name.length()!=0) return 1;
		return 0;
	}
	
	private int check_phone(ArrayList<Account> listAccount, String phone) {
		if(phone.length()!=10) return 0;
		for(Account account: listAccount) if(account.getPhone().equals(phone)) return 2;
		return 1;
	}
	
	private int check_address(String address) {
		address = address.trim();
		if(address.length()!=0) return 1;
		return 0;
	}
	
	private int check_email(ArrayList<Account> list, String email, Account myAccount) {
		email = email.trim().toLowerCase();
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==false) return 0;
		if(myAccount.getEmail().equals(email)==false) {
			for(Account item: list) {
				if(item.getEmail().equals(email)) return 2;
			}
		}
		return 1;
	}
	
	private int check_phone(ArrayList<Account> list, String phone, Account myAccount) {
		phone = phone.trim();
		if(phone.length()!=10) return 0;
		if(myAccount.getPhone().equals(phone)==false) {
			for(Account item: list) {
				if(item.getPhone().equals(phone)) return 2;
			}
		}
		return 1;
	}
	
	private int check_password(String password) {
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
	
	@SuppressWarnings("unchecked")
	private List<Account> getListUser() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Account");
		List<Account> list = query.list();
		return list;
	}
}
