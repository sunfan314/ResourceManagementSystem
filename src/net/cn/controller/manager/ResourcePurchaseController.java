package net.cn.controller.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.PurchaseApplication;

@Controller("managerResourcePurchaseController")
@RequestMapping("/manager")
public class ResourcePurchaseController {

	/**
	 * @param session
	 * @return 返回资产购买申请界面
	 */
	@RequestMapping("/resourcePurchase")
	public ModelAndView resourcePurchase(HttpSession session) {
		return null;
	}

	/**
	 * @return 获取资产购买申请列表
	 */
	@RequestMapping("/getResourcePurchaseApplications")
	public @ResponseBody List<PurchaseApplication> getResourcePurchaseApplications() {
		return null;
	}

	/**
	 * @param session
	 * @param str
	 * @return 处理资产购买申请
	 */
	@RequestMapping("/dealResourcePurchaseApplication")
	public @ResponseBody Map<String, Object> dealResourcePurchaseApplication(HttpSession session,
			PurchaseApplication application) {
		return null;
	}

}
