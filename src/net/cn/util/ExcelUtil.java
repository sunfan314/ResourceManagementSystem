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
			//��ȡ��һ��Sheet��  
			Sheet sheet = workbook.getSheet(0);  
			//��ȡ�ʲ���Ŀ
			int size=sheet.getColumn(0).length-1;
			for(int i=0;i<size;i++){
				Resource resource =new Resource();
				//�����ʲ�״̬Ϊ����״̬
				resource.setStatus(0);
				//�����ʲ����
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
			// ���ļ�
			WritableWorkbook book= Workbook.createWorkbook(new File("C:\\Users\\lenovo\\Desktop\\test.xls"));
			// ������Ϊ����һҳ���Ĺ���������0��ʾ���ǵ�һҳ
			WritableSheet sheet = book.createSheet("��һҳ", 0);
			// ��Label����Ĺ�������ָ����Ԫ��λ���ǵ�һ�е�һ��(0,0)
			// �Լ���Ԫ������Ϊtest
			Label label = new Label(0, 0, "����");
			// ������õĵ�Ԫ����ӵ���������
			sheet.addCell(label);
			jxl.write.Number number = new jxl.write.Number(1, 0, 789.123);
			sheet.addCell(number);
			jxl.write.Label s = new jxl.write.Label(1, 2, "��ʮ��");
			sheet.addCell(s);
			// д�����ݲ��ر��ļ�
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
