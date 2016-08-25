package com.qlove.server.rms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.model.Type;
import com.qlove.server.rms.service.AdminService;
import com.qlove.server.rms.service.ResourceService;
import com.qlove.server.rms.service.UserService;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	
	/**
	 * @param filePath
	 * @param type
	 * @return	从excel文件中读取资产信息
	 * @throws Exception
	 */
	public List<Resource> readFromFile(String filePath, Type type) throws Exception {
		File file = new File(filePath);
		InputStream in;
		Workbook workbook;
		List<Resource> list = new ArrayList<>();
		in = new FileInputStream(file);
		workbook = Workbook.getWorkbook(in);
		// 获取第一张Sheet表
		Sheet sheet = workbook.getSheet(0);
		// 获取资产数目
		int size = sheet.getColumn(0).length - 1;
		for (int i = 0; i < size; i++) {
			Resource resource = new Resource();
			// 设置资产状态为正常状态
			resource.setStatus(0);
			// 设置资产类别
			resource.setType(type);
			Cell[] cells = sheet.getRow(i + 1);
			int temp=cells.length;
			resource.setName((0>=temp)?"":cells[0].getContents());
			resource.setModel((1>=temp)?"":cells[1].getContents());
			resource.setTrackingNo((2>=temp)?"":cells[2].getContents());
			resource.setTrackingNo2((3>=temp)?"":cells[3].getContents());
			resource.setImei((4>=temp)?"":cells[4].getContents());
			resource.setSerialNo((5>=temp)?"":cells[5].getContents());
			resource.setEntryDate((6>=temp)?"":cells[6].getContents());
			resource.setOwner((7>=temp)?"":cells[7].getContents());
			resource.setRemark((8>=temp)?"":cells[8].getContents());
			list.add(resource);
//			导入充值卡信息
//			Cell[] cells=sheet.getRow(i+1);
//			int num=Integer.parseInt(cells[1].getContents());
//			for(int j=0;j<num;j++){
//				Resource resource=new Resource();
//				resource.setStatus(0);
//				resource.setPurchaser("xiahui");
//				resource.setType(type);
//				int temp=cells.length;
//				resource.setName((0>=temp)?"":cells[0].getContents());
//				resource.setPhoneNumber((2>=temp)?"":cells[2].getContents());
//				resource.setOwner((3>=temp)?"":cells[3].getContents());
//				resource.setEntryDate((4>=temp)?"":cells[4].getContents());
//				resource.setRemark((5>=temp)?"":cells[5].getContents());
//				list.add(resource);
//			}
		}

		in.close();
		workbook.close();
		return list;

	}

	/**
	 * @param file
	 * @return	返回文件
	 * @throws Exception
	 */
	public File exportResource(String filePath,Type type,List<Resource> list) throws Exception{

			String fileName=getFileName(type.getName());
			// 创建excel文件
			WritableWorkbook book = Workbook.createWorkbook(new File(filePath+FileUtil.SEPARATOR+fileName));
			// 生成名为typeName的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet(type.getName(), 0);
			//SIM卡类别
			if(type.getId()==ResourceTypeConfig.SIM_CARD){
				//添加表头
				List<Label> labels=new ArrayList<>();
				labels.add(new Label(0, 0, "资产名称"));
				labels.add(new Label(1, 0, "手机号码"));
				labels.add(new Label(2, 0, "IMSI"));
				labels.add(new Label(3, 0, "套餐信息"));
				labels.add(new Label(4, 0, "密码"));
				labels.add(new Label(5, 0, "拥有人"));
				labels.add(new Label(6, 0, "入库时间"));
				labels.add(new Label(7, 0, "资产状态"));
				labels.add(new Label(8, 0, "备注信息"));
				for (Label label : labels) {
					sheet.addCell(label);
				}
				//行计数器
				int rowCount=1;
				for (Resource resource : list) {
					//逐行添加数据
					List<Label> resourceInfos=new ArrayList<>();
					resourceInfos.add(new Label(0,rowCount,resource.getName()));
					resourceInfos.add(new Label(1,rowCount,resource.getPhoneNumber()));
					resourceInfos.add(new Label(2,rowCount,resource.getImsi()));
					resourceInfos.add(new Label(3,rowCount,resource.getPack()));
					resourceInfos.add(new Label(4,rowCount,resource.getPassword()));
					resourceInfos.add(new Label(5,rowCount,resource.getOwner()));
					resourceInfos.add(new Label(6,rowCount,resource.getEntryDate()));
					resourceInfos.add(new Label(7,rowCount,resource.getStatusValue()));
					resourceInfos.add(new Label(8,rowCount,resource.getRemark()));
					for (Label label : resourceInfos) {
						sheet.addCell(label);
					}
					rowCount++;
				}
				// 写入数据并关闭文件
				book.write();
				book.close();
				File file=new File(filePath+FileUtil.SEPARATOR+fileName);
				return file;
				
			}//手机充值卡类别
			else if(type.getId()==ResourceTypeConfig.PHONE_CARD){
				//添加表头
				List<Label> labels=new ArrayList<>();
				labels.add(new Label(0, 0, "资产名称"));
				labels.add(new Label(1, 0, "充值号码"));
				labels.add(new Label(2, 0, "购买人"));
				labels.add(new Label(3, 0, "领用人"));
				labels.add(new Label(4, 0, "入库时间"));
				labels.add(new Label(5, 0, "资产状态"));
				labels.add(new Label(6, 0, "备注信息"));
				for (Label label : labels) {
					sheet.addCell(label);
				}
				//行计数器
				int rowCount=1;
				for (Resource resource : list) {
					//逐行添加数据
					List<Label> resourceInfos=new ArrayList<>();
					resourceInfos.add(new Label(0,rowCount,resource.getName()));
					resourceInfos.add(new Label(1,rowCount,resource.getPhoneNumber()));
					resourceInfos.add(new Label(2,rowCount,resource.getPurchaser()));
					resourceInfos.add(new Label(3,rowCount,resource.getOwner()));
					resourceInfos.add(new Label(4,rowCount,resource.getEntryDate()));
					resourceInfos.add(new Label(5,rowCount,resource.getStatusValue()));
					resourceInfos.add(new Label(6,rowCount,resource.getRemark()));
					for (Label label : resourceInfos) {
						sheet.addCell(label);
					}
					rowCount++;
				}
				// 写入数据并关闭文件
				book.write();
				book.close();
				File file=new File(filePath+FileUtil.SEPARATOR+fileName);
				return file;
			}//消耗品类别
			else if(type.getFatherType()==ResourceTypeConfig.CONSUMABLE){
				//添加表头
				List<Label> labels=new ArrayList<>();
				labels.add(new Label(0, 0, "资产名称"));
				labels.add(new Label(1, 0, "购买人"));
				labels.add(new Label(2, 0, "领用人"));
				labels.add(new Label(3, 0, "入库时间"));
				labels.add(new Label(4, 0, "资产状态"));
				labels.add(new Label(5, 0, "备注信息"));
				for (Label label : labels) {
					sheet.addCell(label);
				}
				//行计数器
				int rowCount=1;
				for (Resource resource : list) {
					//逐行添加数据
					List<Label> resourceInfos=new ArrayList<>();
					resourceInfos.add(new Label(0,rowCount,resource.getName()));
					resourceInfos.add(new Label(1,rowCount,resource.getPurchaser()));
					resourceInfos.add(new Label(2,rowCount,resource.getOwner()));
					resourceInfos.add(new Label(3,rowCount,resource.getEntryDate()));
					resourceInfos.add(new Label(4,rowCount,resource.getStatusValue()));
					resourceInfos.add(new Label(5,rowCount,resource.getRemark()));
					for (Label label : resourceInfos) {
						sheet.addCell(label);
					}
					rowCount++;
				}
				// 写入数据并关闭文件
				book.write();
				book.close();
				File file=new File(filePath+FileUtil.SEPARATOR+fileName);
				return file;
			}//通用设备
			else{
				//添加表头
				List<Label> labels=new ArrayList<>();
				labels.add(new Label(0, 0, "资产名称"));
				labels.add(new Label(1, 0, "资产型号"));
				labels.add(new Label(2, 0, "追踪码"));
				labels.add(new Label(3, 0, "对外追踪码"));
				labels.add(new Label(4, 0, "IMEI"));
				labels.add(new Label(5, 0, "序列号"));
				labels.add(new Label(6, 0, "拥有人"));
				labels.add(new Label(7, 0, "入库时间"));
				labels.add(new Label(8, 0, "资产状态"));
				labels.add(new Label(9, 0, "备注信息"));
				for (Label label : labels) {
					sheet.addCell(label);
				}
				//行计数器
				int rowCount=1;
				for (Resource resource : list) {
					//逐行添加数据
					List<Label> resourceInfos=new ArrayList<>();
					resourceInfos.add(new Label(0,rowCount,resource.getName()));
					resourceInfos.add(new Label(1,rowCount,resource.getModel()));
					resourceInfos.add(new Label(2,rowCount,resource.getTrackingNo()));
					resourceInfos.add(new Label(3,rowCount,resource.getTrackingNo2()));
					resourceInfos.add(new Label(4,rowCount,resource.getImei()));
					resourceInfos.add(new Label(5,rowCount,resource.getSerialNo()));
					resourceInfos.add(new Label(6,rowCount,resource.getOwner()));
					resourceInfos.add(new Label(7,rowCount,resource.getEntryDate()));
					resourceInfos.add(new Label(8,rowCount,resource.getStatusValue()));
					resourceInfos.add(new Label(9,rowCount,resource.getRemark()));
					for (Label label : resourceInfos) {
						sheet.addCell(label);
					}
					rowCount++;
				}
				// 写入数据并关闭文件
				book.write();
				book.close();
				File file=new File(filePath+FileUtil.SEPARATOR+fileName);
				return file;
			}
	}
	
	/**
	 * @return 根据当前时间创建excel文件名
	 */
	private String getFileName(String typeName) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = df.format(new Date());
		return typeName + "_" + time + ".xls";
	}

}
