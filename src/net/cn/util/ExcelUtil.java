package net.cn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.cn.model.Resource;
import net.cn.model.Type;

public class ExcelUtil {
	
	public List<Resource> readFromFile(String filePath,Type type){
		File file = new File(filePath); 
		InputStream in;
		Workbook workbook;
		List<Resource> list=new ArrayList<>();
		try {
			in= new FileInputStream(file);  
			workbook= Workbook.getWorkbook(in);  
			//获取第一张Sheet表  
			Sheet sheet = workbook.getSheet(0);  
			//获取资产数目
			int size=sheet.getColumn(0).length-1;
			for(int i=0;i<size;i++){
				Resource resource =new Resource();
				//设置资产状态为正常状态
				resource.setStatus(0);
				//设置资产类别
				resource.setType(type);
				Cell[] cells=sheet.getRow(i+1);
				resource.setName(cells[0].getContents());
				resource.setModel(cells[1].getContents());
				resource.setTrackingNo(cells[2].getContents());
				resource.setTrackingNo2(cells[3].getContents());
				resource.setImei(cells[4].getContents());
				resource.setSerialNo(cells[5].getContents());
				resource.setEntryDate(cells[6].getContents());
				resource.setOwner(cells[7].getContents());
				resource.setRemark(cells[8].getContents());
				list.add(resource);
			}
		
			in.close();
			workbook.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return list;
		
	}

	public void write() {
		
		try {
			// 打开文件
			WritableWorkbook book= Workbook.createWorkbook(new File("C:\\Users\\lenovo\\Desktop\\test.xls"));
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("第一页", 0);
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为test
			Label label = new Label(0, 0, "测试");
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
			jxl.write.Number number = new jxl.write.Number(1, 0, 789.123);
			sheet.addCell(number);
			jxl.write.Label s = new jxl.write.Label(1, 2, "三十三");
			sheet.addCell(s);
			// 写入数据并关闭文件
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
