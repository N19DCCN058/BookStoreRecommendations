package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Transactional
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath() + "/dangnhap.htm");
			return false;
		}
		return true;
	}
}
