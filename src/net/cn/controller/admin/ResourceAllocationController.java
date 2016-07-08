package net.cn.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Application;

@Controller("adminResourceAllocationController")
@RequestMapping("/admin")
public class ResourceAllocationController {

	/**
	 * @param session
	 * @return	�����ʲ��������
	 */
	@RequestMapping("/resourceAllocation")
	public ModelAndView resourceAllocation(HttpSession session) {
		return null;
	}

	/**
	 * @return	��ȡ����׼���ʲ����������б�
	 */
	@RequestMapping("/getAllocationApprovals")
	public @ResponseBody List<Application> getAllocationApprovals() {
		return null;
	}

	/**
	 * @param application
	 * @param session
	 * @return	�����ڿ��ʲ�
	 */
	@RequestMapping("/allocateResource")
	public @ResponseBody Map<String, Object> allocateResource(Application application, HttpSession session) {
		return null;
	}
	
	

}
