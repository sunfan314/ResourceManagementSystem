package com.qlove.server.rms.controller.admin;

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

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.model.Type;
import com.qlove.server.rms.service.AdminService;
import com.qlove.server.rms.service.ResourceService;
import com.qlove.server.rms.service.UserService;
import com.qlove.server.rms.util.ExcelUtil;
import com.qlove.server.rms.util.FileUtil;
import com.qlove.server.rms.util.LDAP;
import com.qlove.server.rms.util.TimeUtil;

@Controller("excelHelper")
@RequestMapping("/admin")
public class ExcelHelper {
	@javax.annotation.Resource
	private ResourceService resourceService;

	@javax.annotation.Resource
	private AdminService adminService;

	/**
	 * @return 返回资产导入导出界面
	 */
	@RequestMapping("excelHelper")
	public ModelAndView excelHelper() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/excelHelper");
		return modelAndView;
	}

	/**
	 * @param file
	 * @return 上传文件到服务器并导入数据
	 */
	@RequestMapping("/uploadFile")
	public @ResponseBody Map<String, Object> uploadFile(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "type", required = true) int typeId, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 创建数据存储文件
		String filePath = session.getServletContext().getRealPath("/resources/excel");
		String desFileName = getFileName();
		File desFile = new File(filePath, desFileName);
		try {
			// 将用户上传的excel数据拷贝至创建的数据存储文件中
			file.transferTo(desFile);
			ExcelUtil excelUtil = new ExcelUtil();
			Type type = resourceService.getType(typeId);
			// 从拷贝的excel文件中读取资产数据
			List<Resource> list = excelUtil.readFromFile(filePath + FileUtil.SEPARATOR + desFileName, type);
			LDAP ldap = new LDAP();
			// 获取用户列表
			List<String> users = ldap.getUsers();
			// 创建资产插入异常日志
			List<DataLog> dataLogs = new ArrayList<>();
			// 插入数据计数
			int count = 0;
			for (Resource resource : list) {
				//不填写资产拥有人默认新入库资产拥有人为仓库
				if(resource.getOwner().equals("")){
					resource.setOwner("warehouse");
					adminService.addNewResource(resource);
					count++;
				}else{
					//判断用户是否在LDAP服务器中
					if (users.contains(resource.getOwner())) {
						adminService.addNewResource(resource);
						count++;
					} else {
						DataLog dataLog = new DataLog(list.indexOf(resource) + 1, resource.getOwner());
						dataLogs.add(dataLog);
					}
				}
			}
			map.put("success", true);
			map.put("successCount", count);
			map.put("failCount", dataLogs.size());
			map.put("logs", dataLogs);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("success", false);
			return map;
		}

	}

	/**
	 * @param typeId
	 * @param session
	 * @return	导出资产数据到excel文件
	 */
	@RequestMapping("/downloadFile")
	public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "type", required = false) int typeId,
			HttpSession session){
		ExcelUtil excelUtil = new ExcelUtil();
		String filePath = session.getServletContext().getRealPath("/resources/exportExcel");
		Type type = resourceService.getType(typeId);
		List<Resource> resources = resourceService.getCompanyResources(typeId);
		try {
			File file = excelUtil.exportResource(filePath, type, resources);
			HttpHeaders headers = new HttpHeaders();
			String fileName = new String((type.getName()+".xlsx").getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * @param session
	 * @return 数据导入模板下载
	 */
	@RequestMapping("/downloadExcelTemplate")
	public ResponseEntity<byte[]> downloadExcelTemplate(HttpSession session){
		try {
			String filePath = session.getServletContext().getRealPath("/resources/excel-template");
			File file = new File(filePath+FileUtil.SEPARATOR+FileUtil.EXCEL_TEMPLATE_NAME);
			HttpHeaders headers = new HttpHeaders();
			String fileName = new String((FileUtil.EXCEL_TEMPLATE_NAME).getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return 根据当前时间创建excel文件名
	 */
	private String getFileName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = df.format(new Date());
		return "data" + "_" + time + ".xls";
	}

	// 异常日志，记录插入数据在excel表中的位置以及错误的用户名
	class DataLog {
		private int index;

		private String user;

		public DataLog() {
			super();
		}

		public DataLog(int index, String user) {
			super();
			this.index = index;
			this.user = user;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

	}
	

}
