package net.cn.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import net.cn.model.Application;
import net.cn.service.UserService;
import net.cn.util.ListUtil;

@Controller("userResourceTransferController")
@RequestMapping("/user")
public class ResourceTransferController {
	@Resource
	private UserService userService;

	/**
	 * @param session
	 * @return 资产转移申请界面
	 */
	@RequestMapping("/resourceTransferApplication")
	public ModelAndView resourceTransferApplication(HttpSession session) {
//		String uid = (String) session.getAttribute("uid");
		ModelAndView modelAndView = new ModelAndView();
//		List<Application> applications = userService.getReceivedResourceTransferApplication(uid);
//		modelAndView.addObject("transferApplications", applications);
//		modelAndView.addObject("numOfApplications", applications.size());
		modelAndView.setViewName("/user/resourceTransferApplication");
		return modelAndView;
	}
	
	/**
	 * @return	获取用户收到的资产转移请求
	 */
	@RequestMapping("/getResourceTransferApplications")
	public @ResponseBody List<Application> getResourceTransferApplications(HttpSession session){
		String uid=(String)session.getAttribute("uid");
		List<Application> applications=userService.getReceivedResourceTransferApplication(uid);
		return applications;
	}

	/**
	 * @param session
	 * @param agree 是否同意接受个人资产转移
	 * @return 处理个人资产转移请求
	 */
	@RequestMapping("/dealTransferApplication")
	public @ResponseBody Map<String, Object> dealTransferApplication(HttpSession session,
			@RequestParam(value = "aId", required = true) int applicationId,
			@RequestParam(value = "accept", required = true) boolean accept,
			@RequestParam(value = "remark", required = false) String remark) {
		Map<String,Object> map=new HashMap<String,Object>();
		String uid=(String)session.getAttribute("uid");
		//判断申请是否已被处理
		if(ListUtil.applicationContained(applicationId, userService.getReceivedResourceTransferApplication(uid))){
			map.put("applicationCompleted",false );
			//用户接受资产转移申请
			if(accept){
				map.put("success", userService.acceptResourceTransferApplication(applicationId, uid));
			}else{//用户拒绝资产转移申请
				userService.refuseResourceTransferApplication(applicationId, uid, remark);
				map.put("success", true);
			}
		}else{
			map.put("applicationCompleted",true);
		}
		return map;
	}
}
