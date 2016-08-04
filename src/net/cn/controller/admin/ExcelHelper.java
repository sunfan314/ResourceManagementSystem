package net.cn.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.AdminService;
import net.cn.service.ResourceService;
import net.cn.util.ExcelUtil;
import net.cn.util.TimeUtil;

@Controller("excelHelper")
@RequestMapping("/admin")
public class ExcelHelper {
	@javax.annotation.Resource
	private ResourceService resourceService;

	@javax.annotation.Resource
	private AdminService adminService;

	/**
	 * @return �����ʲ����뵼������
	 */
	@RequestMapping("excelHelper")
	public ModelAndView excelHelper() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/excelHelper");
		return modelAndView;
	}

	/**
	 * @param file
	 * @return �ϴ��ļ�������������������
	 */
	@RequestMapping("/uploadFile")
	public @ResponseBody Map<String, Object> uploadFile(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "type", required = true) int typeId, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// �������ݴ洢�ļ�
		String filePath = session.getServletContext().getRealPath("/resources/excel");
		String desFileName = getFileName();
		File desFile = new File(filePath, desFileName);
		try {
			// ���û��ϴ���excel���ݿ��������������ݴ洢�ļ���
			file.transferTo(desFile);
			ExcelUtil excelUtil = new ExcelUtil();
			Type type = resourceService.getType(typeId);
			List<Resource> list = excelUtil.readFromFile(filePath + "\\" + desFileName, type);
			for (Resource resource : list) {
				adminService.addNewResource(resource);
			}
			map.put("success", true);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("success", false);
			return map;
		}

	}

	/**
	 * @return ���ݵ�ǰʱ�䴴��excel�ļ���
	 */
	private String getFileName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = df.format(new Date());
		return "data" + "_" + time + ".xls";
	}

}
