package com.qlove.server.rms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserValidateInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String uri=request.getRequestURI();
		String ctx=request.getContextPath();
		if(!(uri.contains("login.do")||(uri.contains("userLogin.do")))){
			HttpSession session=request.getSession();
			if(null==session.getAttribute("uid")){
				System.out.println("会话过期");
				response.sendRedirect(ctx+"/user/login.do");
			}else{
				System.out.println("会话正常");
			}
		}
		return true;
	}

}
