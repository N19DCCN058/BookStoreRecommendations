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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.*;

@Transactional
@Controller
public class Dashboard_Cus {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value="trangchu", method = RequestMethod.GET)
	public String dashboard (ModelMap model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		if(session.getAttribute("role")!=null) {
//			if(session.getAttribute("role").equals("CUSTOMER")){
//				model.addAttribute("listBatchs", getListBatch());
//				return "index";
//			}else {
//				model.addAttribute("listBatchs", getListBatch());
//				return "index";
//			}
//		}
		model.addAttribute("listBatchs", getListBatch());
		return "index";
	}
	
	@SuppressWarnings("unchecked")
	private List<Batch> getListBatch() {
		List<Batch> listBatch = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch E WHERE E.status = " + '1');
		listBatch = query.list();
		return listBatch;
	}

}
