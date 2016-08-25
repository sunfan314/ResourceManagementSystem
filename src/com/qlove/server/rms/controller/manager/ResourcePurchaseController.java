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

import com.qlove.server.rms.model.PurchaseApplication;
import com.qlove.server.rms.service.ManagerService;
import com.qlove.server.rms.util.ListUtil;

@Controller("managerResourcePurchaseController")
@RequestMapping("/manager")
public class ResourcePurchaseController {
	@Resource
	private ManagerService managerService;

	/**
	 * @param session
	 * @return 返回资产购买申请界面
	 */
	@RequestMapping("/resourcePurchase")
	public ModelAndView resourcePurchase(HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("manager/resourcePurchase");
		return modelAndView;
	}

	/**
	 * @return 获取资产购买申请列表
	 */
	@RequestMapping("/getResourcePurchaseApplications")
	public @ResponseBody List<PurchaseApplication> getResourcePurchaseApplications() {
		return managerService.getResourcePurchaseApplications();
	}

	/**
	 * @param session
	 * @param str
	 * @return 处理资产购买申请
	 */
	@RequestMapping("/dealResourcePurchaseApplication")
	public @ResponseBody Map<String, Object> dealResourcePurchaseApplication(HttpSession session,
			@RequestParam(value = "aId", required = true) int applicationId,
			@RequestParam(value = "accept", required = true) boolean accept,
			@RequestParam(value = "remark", required = false) String remark) {
		Map<String,Object> map=new HashMap<String,Object>();
		String uid=(String)session.getAttribute("uid");
		//判断申请是否已被处理
		if(ListUtil.purchaseApplicationContained(applicationId, managerService.getResourcePurchaseApplications())){
			map.put("applicationCompleted",false );
			//经理同意资产分配申请
			if(accept){
				managerService.agreeResourcePurchaseApplication(applicationId,uid);
				map.put("success",true);
			}else{//经理拒绝资产分配申请
				managerService.refuseResourcePurchaseApplication(applicationId,uid,remark);;
				map.put("success", true);
			}
		}else{
			map.put("applicationCompleted",true);
		}
		return map;
	}

}
