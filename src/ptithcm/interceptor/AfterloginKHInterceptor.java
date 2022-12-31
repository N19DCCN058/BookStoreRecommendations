package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AfterloginKHInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") != null) {
			if(session.getAttribute("role").equals("CUSTOMER")) {
				response.sendRedirect(request.getContextPath() + "/trangchu.htm");
				return false;
			}
		}
		return true;
	}
}
