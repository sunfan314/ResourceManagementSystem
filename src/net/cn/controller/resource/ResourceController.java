package net.cn.controller.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Resource;
import net.cn.service.ResourceService;

@Controller("resourceController")
@RequestMapping("/resource")
public class ResourceController {
	@javax.annotation.Resource 
	private ResourceService resourceService;
	
	/**
	 * @param rid
	 * @return	返回资产详情页面信息
	 */
	@RequestMapping("/resourceInfo")
	public ModelAndView getResourceInfo(int rid){
		Resource resource=resourceService.getResource(rid);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("resource",resource);
		modelAndView.setViewName("resource/resourceInfo");
		return modelAndView;
		
	}

}
