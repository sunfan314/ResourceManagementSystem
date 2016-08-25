package com.qlove.server.rms.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qlove.server.rms.model.Log;
import com.qlove.server.rms.model.Property;
import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.model.Type;
import com.qlove.server.rms.service.ResourceService;
import com.qlove.server.rms.service.UserService;

@Controller("userResourceViewController")
@RequestMapping("/user")
public class ResourceViewController {
	@javax.annotation.Resource
	private UserService userService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	/**
	 * @return 企业资产查看界面
	 */
	@RequestMapping("/companyResources")
	public ModelAndView companyResources() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/companyResources");
		return modelAndView;
	}

	/**
	 * @param session
	 * @return 个人资产查看界面
	 */
	@RequestMapping("/personalResources")
	public ModelAndView personalResources() {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("user/personalResources");
		return modelAndView;
	}

	/**
	 * @return 企业资产类别列表
	 */
	@RequestMapping("/getResourceTypes")
	public @ResponseBody List<Property> getResourceTypes() {
		List<Type> types = resourceService.getResourceTypes();
		//资产父类目
		List<Type> fatherTypes = new ArrayList<>();
		//资产子类目
		List<Type> sonTypes = new ArrayList<>();
		for (Type type : types) {
			if (type.getFatherType() == 0) {
				fatherTypes.add(type);
			} else {
				sonTypes.add(type);
			}
		}
		List<Property> list = new ArrayList<>();
		//根据父类目，依次添加相应子类目至父类目下
		for (Type type : fatherTypes) {
			for (Type t : sonTypes) {
				if (t.getFatherType() == type.getId()) {
					Property property = new Property(String.valueOf(t.getId()), t.getName(), type.getName());
					list.add(property);
				}
			}
		}
		return list;
	}

	
	/**
	 * @param type
	 * @return 某类别企业资产列表
	 */
	@RequestMapping("/getCompanyResources")
	public ModelAndView getCompanyResources(int type){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("type", resourceService.getType(type));
		modelAndView.addObject("resources", resourceService.getCompanyResources(type));
		modelAndView.setViewName("resource/resourceList");
		return modelAndView;
	}
	
	/**
	 * @return	获取名下拥有资产的用户列表
	 */
	@RequestMapping("/getUserWithResources")
	public @ResponseBody List<Property> getUserWithResources(){
		List<String> userList=userService.getUserWithResources();
		List<Property> list=new ArrayList<>();
		for (String str : userList) {
			Property property=new Property(str,str);
			list.add(property);
		}
		return list;
	}

	/**
	 * @param session
	 * @return 个人资产列表
	 */
	@RequestMapping("/getPersonalResources")
	public ModelAndView getPersonalResources(@RequestParam(value="uid",required=false)String uid,HttpSession session) {
		if(uid==null){
			//查看自身名下资产列表
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("resources", resourceService.getPersonalResources((String)session.getAttribute("uid")));
			modelAndView.setViewName("resource/personalResourcesList");
			return modelAndView;
		}else{
			//查看他人名下资产列表
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("resources", resourceService.getPersonalResources(uid));
			modelAndView.setViewName("resource/personalResourceList");
			return modelAndView;
		}
		
	}

	/**
	 * @param rid
	 * @return	获取资产使用记录表
	 */
	@RequestMapping("/getResourceLogs")
	public ModelAndView getResourceLogs(int rid){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("logs",resourceService.getResourceLogs(rid));
		modelAndView.setViewName("resource/resourceLogs");
		return modelAndView;
	}

}
