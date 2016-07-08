package net.cn.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.PurchaseApplication;

@Controller("adminResourcePurchaseController")
@RequestMapping("/admin")
public class ResourcePurchaseController {
	
	/**
	 * @param session
	 * @return	返回资产购买界面
	 */
	@RequestMapping("/resourcePurchase")
	public ModelAndView resourcePurchase(HttpSession session){
		return null;
	}
	
	/**
	 * @return	获取批准的资产购买申请
	 */
	@RequestMapping("/getPurchaseApprovals")
	public @ResponseBody List<PurchaseApplication> getPurchaseApprovals(){
		return null;
	}

}
