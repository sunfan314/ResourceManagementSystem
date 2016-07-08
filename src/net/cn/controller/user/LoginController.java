package net.cn.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.cn.service.UserService;

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
	public ModelAndView userLogin(@RequestParam("uid") String uid, @RequestParam("password") String password,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
//		if(userService.login(uid, password)){
//			session.setAttribute("uid", uid);
//			session.setMaxInactiveInterval(5);
//			modelAndView.setViewName("login/success");
//			
//		}else{
//			modelAndView.setViewName("login/fail");
//		}
		session.setAttribute("uid", uid);
//		session.setMaxInactiveInterval(5);
		modelAndView.setViewName("user/user");
//		modelAndView.setViewName("admin/admin");
		return modelAndView;
	}

}