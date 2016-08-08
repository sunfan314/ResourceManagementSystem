package net.cn.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.internal.runners.TestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import junit.framework.Test;
import net.cn.model.Property;
import net.cn.service.FlowService;
import net.cn.service.UserService;
import net.cn.util.LDAP;
import net.cn.util.LDAPConfig;
import net.cn.util.UserGroupConfig;

/**
 * @author sunfan314	
 * �����û���¼
 *
 */
@Controller("userLoginController")
@RequestMapping("/user")
public class LoginController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private FlowService flowService;
	
	/** 
	 * @return �û���¼����
	 */
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/login");
		return modelAndView;
	}
	
	/**
	 * @param uid	�û���		
	 * @param password	����
	 * @param session	�Ự
	 * @return	�����û�������������û���֤
	 */
	@RequestMapping("/userLogin")
	public ModelAndView userLogin(String uid, String password,HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		//��session��cookieʧЧ֮��ˢ����ҳ��ʱ��ת����¼����
		if(uid==null||password==null){	
			modelAndView.setViewName("login/login");
			return modelAndView;
		}
		if(userService.login(uid, password)){
			session.setAttribute("uid", uid);
			session.setMaxInactiveInterval(60*60*6);
			//�û�Ȩ����֤
			int type=userService.getUserAuthority(uid);
			if(type==UserGroupConfig.MANAGER){
				modelAndView.setViewName("manager/manager");
			}else if(type==UserGroupConfig.ADMIN){
				modelAndView.setViewName("admin/admin");
			}else{
				modelAndView.setViewName("user/user");
			}
		}else{
			modelAndView.setViewName("login/fail");
		}	
		
//		session.setAttribute("uid", uid);
//		session.setMaxInactiveInterval(5);	
//		if(uid.equals("admin")){
//			modelAndView.setViewName("admin/admin");
//		}else if(uid.equals("manager")){
//			modelAndView.setViewName("manager/manager");
//		}else{
//			modelAndView.setViewName("user/user");
//		}	
		return modelAndView;
	}
	
	

}
