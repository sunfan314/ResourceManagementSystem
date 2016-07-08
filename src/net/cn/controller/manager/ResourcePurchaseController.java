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
	 * @return �����ʲ������������
	 */
	@RequestMapping("/resourcePurchase")
	public ModelAndView resourcePurchase(HttpSession session) {
		return null;
	}

	/**
	 * @return ��ȡ�ʲ����������б�
	 */
	@RequestMapping("/getResourcePurchaseApplications")
	public @ResponseBody List<PurchaseApplication> getResourcePurchaseApplications() {
		return null;
	}

	/**
	 * @param session
	 * @param str
	 * @return �����ʲ���������
	 */
	@RequestMapping("/dealResourcePurchaseApplication")
	public @ResponseBody Map<String, Object> dealResourcePurchaseApplication(HttpSession session,
			PurchaseApplication application) {
		return null;
	}

}
