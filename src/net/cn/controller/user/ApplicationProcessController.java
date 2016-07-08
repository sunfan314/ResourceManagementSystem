package net.cn.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Application;
import net.cn.service.UserService;

@Controller("userApplicationProcessController")
@RequestMapping("/user")
public class ApplicationProcessController {
	@Resource
	private UserService userService;

	/**
	 * @param session
	 * @return �鿴����Ľ���
	 */
	@RequestMapping("/userApplicationProcess")
	public ModelAndView userApplicationProcess(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("uid", session.getAttribute("uid"));
		modelAndView.setViewName("user/userApplicationProcess");
		return modelAndView;
	}

	/**
	 * @param session
	 * @return	����û�������ʲ��������룬�ʲ�ת��������ʲ��黹����
	 */
	@RequestMapping("/getUserApplications")
	public @ResponseBody List<Application> getUserApplications(HttpSession session) {
		String uid = (String) session.getAttribute("uid");
		// �û�������ʲ����������ʲ�ת��������ʲ����������б�
		List<Application> list = new ArrayList<>();
		list.addAll(userService.getResourceApplyApplications(uid));
		list.addAll(userService.getResourceTransferApplications(uid));
		list.addAll(userService.getResourceRetrunApplications(uid));
		return list;
	}

}
