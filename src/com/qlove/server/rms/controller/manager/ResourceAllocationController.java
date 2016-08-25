package com.qlove.server.rms.controller.manager;

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

import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.service.ManagerService;
import com.qlove.server.rms.util.ListUtil;

@Controller("managerResourceAllocationController")
@RequestMapping("/manager")
public class ResourceAllocationController {
	@Resource
	private ManagerService managerService;

	/**
	 * @param session
	 * @return 返回在库资产分配申请界面
	 */
	@RequestMapping("/resourceAllocation")
	public ModelAndView resourceAllocation(HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
//		List<Application> applications=managerService.getResourceAllocationApplications();
//		modelAndView.addObject("numOfApplications", applications.size());
//		modelAndView.addObject("applications",applications);
		modelAndView.setViewName("manager/resourceAllocation");
		return modelAndView;
	}
	
	/**
	 * @return	返回在库资产分配申请列表
	 */
	@RequestMapping("/getResourceAllocationApplications")
	public @ResponseBody List<Application> getResourceAllocationApplications(){
		return managerService.getResourceAllocationApplications();
	}

	/**
	 * @param session
	 * @param application
	 * @return	处理资产分配申请
	 */
	@RequestMapping("/dealResourceAllocationApplication")
	public @ResponseBody Map<String, Object> dealResourceAllocationApplication(HttpSession session,
			@RequestParam(value = "aId", required = true) int applicationId,
			@RequestParam(value = "accept", required = true) boolean accept,
			@RequestParam(value = "remark", required = false) String remark) {
		Map<String,Object> map=new HashMap<String,Object>();
		String uid=(String)session.getAttribute("uid");
		//判断申请是否已被处理
		if(ListUtil.applicationContained(applicationId, managerService.getResourceAllocationApplications())){
			map.put("applicationCompleted",false );
			//经理同意资产分配申请
			if(accept){
				managerService.agreeResourceAllocationApplication(applicationId,uid);
				map.put("success",true);
			}else{//经理拒绝资产分配申请
				managerService.refuseResourceAllocationApplication(applicationId,uid,remark);;
				map.put("success", true);
			}
		}else{
			map.put("applicationCompleted",true);
		}
		return map;
	}

}
