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
	 * @return 查看请求的进度
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
	 * @return	获得用户发起的资产分配申请，资产转移申请和资产归还申请
	 */
	@RequestMapping("/getUserApplications")
	public @ResponseBody List<Application> getUserApplications(HttpSession session) {
		String uid = (String) session.getAttribute("uid");
		// 用户发起的资产分配请求、资产转移请求和资产分配请求列表
		List<Application> list = new ArrayList<>();
		list.addAll(userService.getResourceApplyApplications(uid));
		list.addAll(userService.getResourceTransferApplications(uid));
		list.addAll(userService.getResourceRetrunApplications(uid));
		return list;
	}

}
