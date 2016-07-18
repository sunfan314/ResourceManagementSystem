package net.cn.controller.user;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.PurchaseApplication;

@Controller("userResourcePurchaseController")
@RequestMapping("/user")
public class ResourcePurchaseController {
	/**
	 * @param session
	 * @return	�ʲ������������
	 */
	@RequestMapping("/resourcePurchaseApplication")
	public ModelAndView resourcePurchaseApplication(HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("uid",session.getAttribute("uid"));
		modelAndView.setViewName("user/resourcePurchaseApplication");
		return modelAndView;
	}
	
	/**
	 * @param session
	 * @param info	�ʲ�����������Ϣ
	 * @return	�ύ�ʲ���������
	 */
	@RequestMapping("/commitPurchaseApplication")
	public @ResponseBody Map<String, Object> commitPurchaseApplication(HttpSession session,PurchaseApplication application){
		return null;
	}
}
