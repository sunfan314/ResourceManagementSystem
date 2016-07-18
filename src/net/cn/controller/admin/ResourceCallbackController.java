package net.cn.controller.admin;

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
import net.cn.service.AdminService;
import net.cn.util.ListUtil;

@Controller("adminResourceCallbackController")
@RequestMapping("/admin")
public class ResourceCallbackController {
	@Resource
	private AdminService adminService;
	
	/**
	 * @param session
	 * @return	返回资产回收界面
	 */
	@RequestMapping("/resourceCallback")
	public ModelAndView resourceCallback(HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		List<Application> applications=adminService.getResourceCallbackApplications();
		modelAndView.addObject("numOfApplications", applications.size());
		modelAndView.addObject("applications", applications);
		modelAndView.setViewName("admin/resourceCallback");
		return modelAndView;
	}
	
	
	/**
	 * @param rid
	 * @param session
	 * @return	回收资产
	 */
	@RequestMapping("/dealResourceCallbackApplication")
	public @ResponseBody Map<String,Object> dealResourceCallbackApplication(HttpSession session,
			@RequestParam(value = "aId", required = true) int applicationId,
			@RequestParam(value = "accept", required = true) boolean accept,
			@RequestParam(value = "remark", required = false) String remark){
		Map<String,Object> map=new HashMap<String,Object>();
		String uid=(String)session.getAttribute("uid");
		//判断申请是否已被处理
		if(ListUtil.applicationContained(applicationId, adminService.getResourceCallbackApplications())){
			map.put("applicationCompleted",false );
			//管理员完成资产回收
			if(accept){
				map.put("success", adminService.agreeResourceCallbackApplication(applicationId, uid));
			}else{//管理员拒绝资产回收
				adminService.refuseResourceCallbackApplication(applicationId, uid, remark);
				map.put("success", true);
			}
		}else{
			map.put("applicationCompleted",true);
		}
		return map;
	}

}
