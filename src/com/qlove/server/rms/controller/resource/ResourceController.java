package com.qlove.server.rms.controller.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.service.ResourceService;

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
	
	/**
	 * @param type
	 * @return	获取类别父类别
	 */
	@RequestMapping("/getFatherType")
	public @ResponseBody Map<String,Object> getFatherType(int type){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("fatherType",resourceService.getFatherType(type));
		return map;
	}

}
