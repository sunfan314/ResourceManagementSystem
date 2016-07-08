package net.cn.controller.user;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Log;
import net.cn.model.Property;
import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.ResourceService;
import net.cn.service.UserService;

@Controller("userResourceViewController")
@RequestMapping("/user")
public class ResourceViewController {
	@javax.annotation.Resource
	private UserService userService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	/**
	 * @return ��ҵ�ʲ��鿴����
	 */
	@RequestMapping("/companyResources")
	public ModelAndView companyResources() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/companyResources");
		return modelAndView;
	}

	/**
	 * @param session
	 * @return �����ʲ��鿴����
	 */
	@RequestMapping("/personalResources")
	public ModelAndView personalResources() {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("user/personalResources");
		return modelAndView;
	}

	/**
	 * @return ��ҵ�ʲ�����б�
	 */
	@RequestMapping("/getResourceTypes")
	public @ResponseBody List<Property> getResourceTypes() {
		List<Type> types = resourceService.getResourceTypes();
		//�ʲ�����Ŀ
		List<Type> fatherTypes = new ArrayList<>();
		//�ʲ�����Ŀ
		List<Type> sonTypes = new ArrayList<>();
		for (Type type : types) {
			if (type.getFatherType() == 0) {
				fatherTypes.add(type);
			} else {
				sonTypes.add(type);
			}
		}
		List<Property> list = new ArrayList<>();
		//���ݸ���Ŀ�����������Ӧ����Ŀ������Ŀ��
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
	 * @return ĳ�����ҵ�ʲ��б�
	 */
	@RequestMapping("/getCompanyResources")
	public @ResponseBody List<Resource> getCompanyResources(int type) {
		return resourceService.getCompanyResources(type);
	}
	
	/**
	 * @return	��ȡ����ӵ���ʲ����û��б�
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
	 * @return �����ʲ��б�
	 */
	@RequestMapping("/getPersonalResources")
	public @ResponseBody List<Resource> getPersonalResources(@RequestParam(value="uid",required=false)String uid,HttpSession session) {
		if(uid==null){
			//�鿴���������ʲ��б�
			return resourceService.getPersonalResources((String)session.getAttribute("uid"));
		}else{
			//�鿴���������ʲ��б�
			return resourceService.getPersonalResources(uid);
		}
		
	}
	
	/**
	 * @param rid
	 * @return	��ȡĳ�ʲ�ʹ�ü�¼��
	 */
	@RequestMapping("/getResourceLogs")
	public @ResponseBody List<Log> getResourceLog(int rid){
		return resourceService.getResourceLogs(rid);
	}

}
