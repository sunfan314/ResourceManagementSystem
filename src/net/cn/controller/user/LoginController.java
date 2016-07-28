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
	public ModelAndView userLogin(@RequestParam("uid") String uid, @RequestParam("password") String password,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(userService.login(uid, password)){
			session.setAttribute("uid", uid);
			session.setMaxInactiveInterval(60*60*6);
//			modelAndView.setViewName("login/success");
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
		/*测试用
		session.setAttribute("uid", uid);
		session.setMaxInactiveInterval(5);	
		if(uid.equals("admin")){
			modelAndView.setViewName("admin/admin");
		}else if(uid.equals("manager")){
			modelAndView.setViewName("manager/manager");
		}else{
			modelAndView.setViewName("user/user");
		}
		*/
		return modelAndView;
	}
	
	@RequestMapping("/test")
	public ModelAndView test(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("user/test");
		return modelAndView;
	}
	
	@RequestMapping("/getUsers")
	public @ResponseBody List<Property> getUsers(){
		List<Property> list=new ArrayList<>();
		LDAP ldap=new LDAP();
		List<String> users=ldap.getUsers();
		for (String str : users) {
			Property property=new Property(str, str);
			list.add(property);
		}
		return list;
	}

}
