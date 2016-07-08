package net.cn.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Application;
import net.cn.model.Resource;
import net.cn.service.ResourceService;
import net.cn.service.UserService;

@Controller("userResourceApplyController")
@RequestMapping("/user")
public class ResourceApplyController {
	@javax.annotation.Resource
	private UserService userService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	/**
	 * @param session
	 * @return �ʲ��������
	 */
	@RequestMapping("/resourceApplyApplication")
	public ModelAndView resourceApplication(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/resourceApplyApplication");
		return modelAndView;
	}

	/**
	 * @return ��ȡĳ�������ҵ�ڿ��ʲ��б�
	 */
	@RequestMapping("/getAvailableResources")
	public @ResponseBody List<Resource> getAvailableResources(int type) {
		return resourceService.getAvailableResources(type);
	}

	/**
	 * @param session
	 * @param application
	 * @return ������ҵ�ڿ��ʲ�
	 */
	@RequestMapping("/applyAvailableResource")
	public @ResponseBody Map<String, Object> applyAvailableResources(HttpServletRequest request, HttpSession session) {
		Map<String,Object> map=new HashMap<String,Object>();
		int rid=Integer.parseInt(request.getParameter("rid"));//�ʲ���ʶ
		String remark=request.getParameter("remark");//��ע��Ϣ
		String uid=(String)session.getAttribute("uid");//������
		map.put("success", userService.createResourceApplyApplication(rid, uid, remark));
		return map;
	}

}