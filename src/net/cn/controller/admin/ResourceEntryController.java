package net.cn.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Property;
import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.AdminService;
import net.cn.service.ResourceService;
import net.cn.service.UserService;
import net.cn.util.LDAP;

@Controller("adminResourceEntryController")
@RequestMapping("/admin")
public class ResourceEntryController {
	@javax.annotation.Resource
	private UserService userService;
	
	@javax.annotation.Resource
	private AdminService adminService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;
	
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
	 * @return	获得用户列表（包含仓库）
	 */
	@RequestMapping("/getUsers")
	public @ResponseBody List<Property> getUsers(){
		List<Property> list=new ArrayList<>();
		list.add(new Property("warehouse","仓库"));
		list.addAll(userService.getUsers());
		return list;
	}
	
	/**
	 * @return	获取一级类别列表
	 */
	@RequestMapping("/getFatherTypes")
	public @ResponseBody List<Type> getFatherTypes(){
		List<Type> list=new ArrayList<>();
		List<Type> types=resourceService.getResourceTypes();
		for (Type type : types) {
			if(type.getFatherType()==0){
				list.add(type);
			}
		}
		return list;
	}
	
	/**
	 * @param typeName
	 * @param fatherType
	 * @return	添加新类别
	 */
	@RequestMapping("/addType")
	public @ResponseBody Map<String,Object> addType(String typeName,int fatherType){
		Map<String,Object> map=new HashMap<String,Object>();
		List<Type> types=resourceService.getResourceTypes();
		for (Type type : types) {
			if(type.getName().equals(typeName)&&type.getFatherType()==fatherType){
				map.put("success", false);
				return map;
			}
		}
		resourceService.addType(typeName, fatherType);
		map.put("success", true);
		return map;
	}
	

	/**
	 * @param session
	 * @param resource
	 * @return	入库新购资产
	 */
	@RequestMapping("/enterNewResource")
	public @ResponseBody Map<String, Object> enterNewResource(Resource resource) {
		Map<String,Object> map=new HashMap<String,Object>();
		LDAP ldap=new LDAP();
		List<String> userList=ldap.getUsers();
		userList.add("warehouse");
		//检查资产拥有人是否存在，防止资产被分配给不存在的用户而导致无法资产无法获取
		if(userList.contains(resource.getOwner())){
			adminService.addNewResource(resource);
			map.put("success", true);
		}else{
			map.put("success",false);
		}
		return map;
	}
	
	/**
	 * @param session
	 * @param resource
	 * @return	修改资产信息
	 */
	@RequestMapping("/editResource")
	public @ResponseBody Map<String,Object> editResource(HttpSession session,Resource resource){
		Map<String,Object> map=new HashMap<String,Object>();
		LDAP ldap=new LDAP();
		List<String> userList=ldap.getUsers();
		userList.add("warehouse");
		//检查资产拥有人是否存在，防止资产被分配给不存在的用户而导致无法资产无法获取
		if(userList.contains(resource.getOwner())){
			adminService.editResource(resource);
			map.put("success", true);
		}else{
			map.put("success",false);
		}
		return map;
	}
	

}
