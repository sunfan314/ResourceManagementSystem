package net.cn.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Application;
import net.cn.model.Property;
import net.cn.service.ResourceService;
import net.cn.service.UserService;
import net.cn.util.LDAP;

@Controller("userResourceManageController")
@RequestMapping("/user")
public class ResourceManageController {
	@Resource
	private UserService userService;

	/**
	 * @param session
	 * @return �����ʲ��������
	 */
	@RequestMapping("/userResourceManagement")
	public ModelAndView userResourceManagement(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("uid", session.getAttribute("uid"));
		modelAndView.setViewName("user/userResourceManagement");
		return modelAndView;
	}

	/**
	 * @param session
	 * @param rid
	 * @return ����黹�����ʲ�
	 */
	@RequestMapping("/returnResourceApplication")
	public @ResponseBody Map<String, Object> returnResourceApplication(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int rid = Integer.parseInt(request.getParameter("rid"));// �ʲ���ʶ
		String uid = (String) session.getAttribute("uid");// �û���ʶ
		String remark = request.getParameter("remark");//��ע��Ϣ
		map.put("success",userService.createResourceReturnApplication(rid, uid, remark));
		map.put("receiverExists", true);
		return map;
	}

	/**
	 * @param session
	 * @param application
	 * @return ��������ʲ�ת�Ƶ�����
	 */
	@RequestMapping("/transferPersonalResourceApplication")
	public @ResponseBody Map<String, Object> transferPersonalResourceApplication(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int rid = Integer.parseInt(request.getParameter("rid"));// �ʲ���ʶ
		String uid = (String) session.getAttribute("uid");// �û���ʶ
		String receiver=request.getParameter("resourceReceiver");//�ʲ�������
		String remark = request.getParameter("remark");//��ע��Ϣ
		LDAP ldap=new LDAP();
		List<String> userList=ldap.getUsers();
		if(!userList.contains(receiver)){//�����û�������
//		if(!true){
			map.put("receiverExists", false);
			map.put("success",false);
		}else{
			map.put("receiverExists", true);
			map.put("success",userService.createResourceTransferApplication(rid, uid, receiver, remark));
		}
		
		return map;
	}

	/**
	 * @param uid
	 * @return ��ȡ�û��б���ȥ����������û�����
	 */
	@RequestMapping("/getOtherUsers")
	public @ResponseBody List<Property> getOtherUsers(String uid) {
		return userService.getUsers(uid);
	}

}
