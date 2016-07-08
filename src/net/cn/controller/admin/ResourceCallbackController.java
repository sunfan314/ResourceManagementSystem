package net.cn.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Application;

@Controller("adminResourceCallbackController")
@RequestMapping("/admin")
public class ResourceCallbackController {
	
	/**
	 * @param session
	 * @return	�����ʲ����ս���
	 */
	@RequestMapping("/resourceCallback")
	public ModelAndView resourceCallback(HttpSession session){
		return null;
	}
	
	/**
	 * @return	��ȡ�ʲ����������б�
	 */
	@RequestMapping("/getResourceCallbackApplications")
	public @ResponseBody List<Application> getResourceCallbackApplications(){
		return null;
	}
	
	/**
	 * @param rid
	 * @param session
	 * @return	�����ʲ�
	 */
	@RequestMapping("/returnResource")
	public @ResponseBody Map<String,Object> returnResource(int rid,HttpSession session){
		return null;
	}

}
