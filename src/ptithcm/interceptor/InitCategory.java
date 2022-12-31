package ptithcm.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ptithcm.entity.Category;

@Transactional
public class InitCategory extends HandlerInterceptorAdapter{
	@Autowired
	SessionFactory factory;
	
	@SuppressWarnings("unchecked")
	private List<Category> getListCategory(){
		List<Category> listCategory = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Category");
		listCategory = query.list();
		return listCategory;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("listCategorys", getListCategory());
		return true;
	}
}
