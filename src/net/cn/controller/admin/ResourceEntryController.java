package net.cn.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Resource;

@Controller("adminResourceEntryController")
@RequestMapping("/admin")
public class ResourceEntryController {

	/**
	 * @param session
	 * @return 新购资产入库界面
	 */
	@RequestMapping("/resourceEntry")
	public ModelAndView resourceEntry(HttpSession session) {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("admin/resourceEntry");
		return modelAndView;
	}

	/**
	 * @param session
	 * @param resource
	 * @return	入库新购资产
	 */
	@RequestMapping("/enterNewResource")
	public @ResponseBody Map<String, Object> enterNewResource(HttpSession session, Resource resource) {
		return null;
	}

}
