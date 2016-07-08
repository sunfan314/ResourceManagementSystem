package net.cn.controller.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Application;

@Controller("managerResourceAllocationController")
@RequestMapping("/manager")
public class ResourceAllocationController {

	/**
	 * @param session
	 * @return 返回在库资产分配申请界面
	 */
	@RequestMapping("/resourceAllocation")
	public ModelAndView resourceAllocation(HttpSession session) {
		return null;
	}

	/**
	 * @return 获取资产分配申请列表
	 */
	@RequestMapping("/getResourceAllocationApplications")
	public @ResponseBody List<Application> getResourceAllocationApplications() {
		return null;
	}

	/**
	 * @param session
	 * @param application
	 * @return	处理资产分配申请
	 */
	@RequestMapping("/dealResourceAllocationApplication")
	public @ResponseBody Map<String, Object> dealResourceAllocationApplication(HttpSession session,
			Application application) {
		return null;
	}

}
