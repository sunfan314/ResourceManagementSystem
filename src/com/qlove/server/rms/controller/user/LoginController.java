package com.qlove.server.rms.controller.user;

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

import com.qlove.server.rms.model.Property;
import com.qlove.server.rms.service.FlowService;
import com.qlove.server.rms.service.UserService;
import com.qlove.server.rms.util.LDAP;
import com.qlove.server.rms.util.LDAPConfig;
import com.qlove.server.rms.util.UserGroupConfig;

import junit.framework.Test;

/**
 * @author sunfan314	
 * 处理用户登录
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
	 * @return 用户登录界面
	 */
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/login");
		return modelAndView;
	}
	
	/**
	 * @param uid	用户名		
	 * @param password	密码
	 * @param session	会话
	 * @return	根据用户名和密码进行用户验证
	 */
	@RequestMapping("/userLogin")
	public ModelAndView userLogin(String uid, String password,HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		//在session和cookie失效之后刷新主页面时跳转到登录界面
		if(uid==null||password==null){	
			modelAndView.setViewName("login/login");
			return modelAndView;
		}
		if(userService.login(uid, password)){
			session.setAttribute("uid", uid);
			session.setMaxInactiveInterval(60*60*6);
			//用户权限验证
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
	
	@RequestMapping("/test")
	public ModelAndView test(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("user/test");
		return modelAndView;
	}
	
	@RequestMapping("mailTest")
	public @ResponseBody Map<String,Object> mailTest(){
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("success", true);
		return map;
	}
	

}
