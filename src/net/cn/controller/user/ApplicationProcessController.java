package net.cn.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		String uid = (String) session.getAttribute("uid");
		modelAndView.addObject("uid", uid);
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
		list.addAll(userService.getResourceReturnApplications(uid));
		return list;
	}
	
	/**
	 * @param session
	 * @return	获得用户发起的资产分配申请
	 */
	@RequestMapping("/getUserResourceApplyApplications")
	public @ResponseBody List<Application> getUserResourceApplyApplications(HttpSession session){
		String uid=(String)session.getAttribute("uid");
		List<Application> applications=userService.getResourceApplyApplications(uid);
		return applications;
	}
	
	/**
	 * @param session
	 * @return	获得用户发起的资产转移申请
	 */
	@RequestMapping("/getUserResourceTransferApplications")
	public @ResponseBody List<Application> getUserResourceTransferApplications(HttpSession session){
		String uid=(String)session.getAttribute("uid");
		List<Application> applications=userService.getResourceTransferApplications(uid);
		return applications;
	}
	
	/**
	 * @param session
	 * @return	获得用户发起的资产归还申请
	 */
	@RequestMapping("/getUserResourceReturnApplications")
	public @ResponseBody List<Application> getUserResourceReturnApplications(HttpSession session){
		String uid=(String)session.getAttribute("uid");
		List<Application> applications=userService.getResourceReturnApplications(uid);
		return applications;
	}
	
	/**
	 * @param applicationId
	 * @return	获得申请的详细信息
	 */
	@RequestMapping("/getApplicationDetailInfo")
	public @ResponseBody Map<String,Object> getApplicationDetailInfo(@RequestParam(value="aId",required=true)int applicationId){
		Map<String,Object> map=new HashMap<String,Object>();
		Application application=userService.getApplicationInfo(applicationId);
		map.put("application", application);
		return map;
	}

}
