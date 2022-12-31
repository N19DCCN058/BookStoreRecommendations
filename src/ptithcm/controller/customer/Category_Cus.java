package ptithcm.controller.customer;

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
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.Batch;
import ptithcm.entity.Category;

@Transactional
@Controller
public class Category_Cus {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "{categoryName}", method = RequestMethod.GET)
	public String get_Category(ModelMap model, @PathVariable("categoryName") String categoryName, HttpServletRequest request) {
		List<Batch> batchs = getListBatch(categoryName);
		if(batchs!=null) {
			HttpSession session = request.getSession();
			model.addAttribute("role",(String) session.getAttribute("role"));
			model.addAttribute("listBatchs", batchs);
			return "index";
		}
		return "redirect:/trangchu.htm";
	}
	
	@SuppressWarnings("unchecked")
	private List<Category> getListCategory(){
		List<Category> listCategory = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Category");
		listCategory = query.list();
		return listCategory;
	}
	
	private List<Batch> getListBatch(String categoryName){
		List<Category> list = getListCategory();
		for(Category item: list)
			if(item.categoryNameToURL().equals(categoryName)) return (List<Batch>) item.getBatchs();
		return null;
	}

}
