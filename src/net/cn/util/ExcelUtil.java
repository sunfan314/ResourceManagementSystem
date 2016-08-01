package net.cn.util;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {

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
	
	public static void main(String[] args){
		ExcelUtil excelUtil=new ExcelUtil();
		excelUtil.write();
	}
}
