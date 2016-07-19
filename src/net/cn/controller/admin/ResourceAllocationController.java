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

@Controller("adminResourceAllocationController")
@RequestMapping("/admin")
public class ResourceAllocationController {
	@Resource
	private AdminService adminService;

	/**
	 * @param session
	 * @return	返回资产分配界面
	 */
	@RequestMapping("/resourceAllocation")
	public ModelAndView resourceAllocation(HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
//		List<Application> applications=adminService.getResourceAllocationApplications();
//		modelAndView.addObject("numOfApplications",applications.size());
//		modelAndView.addObject("applications",applications);
		modelAndView.setViewName("admin/resourceAllocation");
		return modelAndView;
	}
	
	/**
	 * @return	返回通过管理员审核的在库资产分配申请列表
	 */
	@RequestMapping("/getResourceAllocationApplications")
	public @ResponseBody List<Application> getResourceAllocationApplications(){
		return adminService.getResourceAllocationApplications();
	}

	

	/**
	 * @param application
	 * @param session
	 * @return	分配在库资产
	 */
	@RequestMapping("/dealResourceAllocationApplication")
	public @ResponseBody Map<String, Object> dealResourceAllocationApplication(HttpSession session,
			@RequestParam(value = "aId", required = true) int applicationId,
			@RequestParam(value = "accept", required = true) boolean accept,
			@RequestParam(value = "remark", required = false) String remark) {
		Map<String,Object> map=new HashMap<String,Object>();
		String uid=(String)session.getAttribute("uid");
		//判断申请是否已被处理
		if(ListUtil.applicationContained(applicationId, adminService.getResourceAllocationApplications())){
			map.put("applicationCompleted",false );
			//管理员完成资产分配
			if(accept){
				map.put("success", adminService.agreeResourceAllocationApplication(applicationId, uid));
			}else{//管理员拒绝资产转移分配
				adminService.refuseResourceAllocationApplication(applicationId, uid, remark);
				map.put("success", true);
			}
		}else{
			map.put("applicationCompleted",true);
		}
		return map;
	}
	
	

}
