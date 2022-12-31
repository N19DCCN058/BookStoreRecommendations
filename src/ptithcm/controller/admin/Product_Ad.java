package ptithcm.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ptithcm.entity.Batch;
import ptithcm.entity.Category;
import ptithcm.entity.Product;

@RequestMapping(value = "admin")
@Transactional
@Controller
public class Product_Ad {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "listproduct")
	public String getListProduct(ModelMap model) {
		model.addAttribute("listProduct", getListProduct());
		return "admin/list_batch";
	}
	
	@RequestMapping(value= "newproduct", method = RequestMethod.GET)
	public String loadpage_addProduct(ModelMap model) {
		model.addAttribute("categorys", getCategory());
		return "admin/add_batch";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value= "newproduct", method = RequestMethod.POST)
	public String addProduct(ModelMap model,@RequestParam("batchName") String batchName, 
			@RequestParam("category") Integer categoryId,
			@RequestParam("price") Long price, @RequestParam("amount") Integer amount, 
			@RequestParam("describe") String describe, @RequestParam("photo") CommonsMultipartFile photo, 
			HttpServletRequest request) throws Exception {
		model.addAttribute("categorys", getCategory());
		if(batchName.trim().length()==0||price==null||amount==null) {
			model.addAttribute("status_add_batch",4);
			return "admin/add_batch";
		}
		boolean check_name = check_nameBatch((ArrayList<Batch>)getListProduct(), batchName);
		if(check_name==false) {
			model.addAttribute("status_add_batch",3);
			return "admin/add_batch";
		}
		
		
		HttpSession web_session = request.getSession();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Category category = (Category) session.get(Category.class , categoryId);
		Batch batch = new Batch();
		batch.setBatchName(batchName);
		batch.setCategory(category);
		batch.setAmount(amount);
		batch.setSold(0);
		batch.setStatus(1);
		batch.setPrice(price);
		batch.setDiscount(0);
		batch.setDescribe(describe);
		ServletContext context = web_session.getServletContext();  
		
	    String path = context.getRealPath("/resources/upload/product"); 
	    String fileName = photo.getOriginalFilename();
		if(photo.getContentType().equalsIgnoreCase("image/jpeg")||photo.getContentType().equalsIgnoreCase("image/png")) {
			photo.transferTo(new File(path+File.separator+fileName));
			batch.setPhoto(fileName);
			try {
				session.save(batch);
				for (int i = 1; i <= batch.getAmount(); i++) {
					Product product = new Product();
					product.setBatch(batch);
					product.setPrice(batch.getPrice());
					product.setStatus(1);
					session.save(product);
				}
				transaction.commit();
				web_session.setAttribute("status_add_batch", 1);
			}catch (Exception e) {
				System.out.println(e.toString());
				transaction.rollback();
				// TODO: handle exception
				model.addAttribute("status_add_batch", 0);
				return "admin/add_batch";
			}finally {
				session.close();
			}
		}else {
			model.addAttribute("status_add_batch", 2);
			return "admin/add_batch";
		}
		return "redirect:/admin/listproduct.htm";
	}
	
	@RequestMapping(value = "batch/{batchId}")
	public String infoBatch(ModelMap model, @PathVariable("batchId") Integer batchId) {
		Session session = factory.getCurrentSession();
		Batch batch = (Batch)session.get(Batch.class, batchId);
		if(batch==null) {
			return "redirect:/admin/listproduct.htm";
		}
		model.addAttribute("batch", batch);
		return "admin/info_batch";
	}
	
	@RequestMapping(value = "editbatch/{batchId}", method = RequestMethod.GET)
	public String loadpage_editBatch(ModelMap model, @PathVariable("batchId") Integer batchId) {
		Session session = factory.getCurrentSession();
		Batch batch = (Batch)session.get(Batch.class, batchId);
		if(batch==null) {
			return "redirect:/admin/listproduct.htm";
		}
		model.addAttribute("categorys", getCategory());
		model.addAttribute("batch", batch);
		return "admin/edit_batch";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "editbatch/{batchId}", method = RequestMethod.POST)
	public String editBatch(ModelMap model, @PathVariable("batchId") Integer batchId,
			@RequestParam("batchName")String batchName, @RequestParam("category") Integer categoryId,
			@RequestParam("photo") CommonsMultipartFile photo, @RequestParam("describe") String describe,
			@RequestParam("discount") Integer discount, HttpServletRequest request) throws Exception {
		
		HttpSession web_session = request.getSession();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Batch batch = (Batch) session.get(Batch.class, batchId);
		boolean check_batchName = check_nameBatch((ArrayList<Batch>)getListProduct(), batch, batchName);
		if(batchName.trim().length()==0||discount==null) {
			model.addAttribute("status_update_batch",3);
			model.addAttribute("categorys", getCategory());
			model.addAttribute("batch", (Batch) session.get(Batch.class, batchId));
			return "admin/edit_batch";
		}
		if(check_batchName==false) {
			model.addAttribute("status_update_batch",2);
			model.addAttribute("categorys", getCategory());
			model.addAttribute("batch", (Batch) session.get(Batch.class, batchId));
			return "admin/edit_batch";
		}
		Category category = (Category) session.get(Category.class , categoryId);
		batch.setBatchName(batchName);
		batch.setCategory(category);
		batch.setDescribe(describe);
		batch.setDiscount(discount);
		String fileName = photo.getOriginalFilename();
		ServletContext context = web_session.getServletContext();
		String path = context.getRealPath("/resources/upload/product"); 
		if(fileName.equals("")) {
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
		}else {
			if(photo.getContentType().equalsIgnoreCase("image/jpeg")||photo.getContentType().equalsIgnoreCase("image/png")) {
				photo.transferTo(new File(path+File.separator+fileName));
				batch.setPhoto(fileName);
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
			}else {
				model.addAttribute("status_update_batch_photo", 0);
			}
		}	
		model.addAttribute("categorys", getCategory());
		model.addAttribute("batch", batch);
		return "admin/edit_batch";
	}
	
	private boolean check_nameBatch(ArrayList<Batch> list, String name) {
		String urlNameBatch = batchNameToURL(name);
		for(Batch batch: list)
			if(batch.batchNameToURL().equals(urlNameBatch))
				return false;
		return true;
	}
	
	private boolean check_nameBatch(ArrayList<Batch> list,Batch batchNow ,String name) {
		String urlNameBatch = batchNameToURL(name);
		for(Batch batch: list)
			if(!batch.getBatchId().equals(batchNow.getBatchId())) {
				if(batch.batchNameToURL().equals(urlNameBatch))
					return false;
			}
		return true;
	}
	
	private String batchNameToURL(String batchName) {
		return batchName.toLowerCase().trim().replace(" - ", "-").
				replace(" ", "-").replace("/", "-");
	}
	
	@RequestMapping(value = "disablebatch/{batchId}")
	public String disableBatch(ModelMap model, @PathVariable("batchId") Integer batchId) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Batch batch = (Batch) session.get(Batch.class, batchId);
		batch.setStatus(2);
		try {
			session.update(batch);
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			// TODO: handle exception
		}finally {
			session.close();
		}
		return "redirect:/admin/editbatch/"+batch.getBatchId()+".htm";
	}
	
	@SuppressWarnings("unchecked")
	private List<Category> getCategory() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Category");
		List<Category> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	private Object getListProduct() {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch");
		List<Batch> list = query.list();
		return list;
	}
}
