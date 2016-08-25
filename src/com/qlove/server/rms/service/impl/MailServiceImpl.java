package com.qlove.server.rms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qlove.server.rms.dao.BaseDao;
import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.model.PurchaseApplication;
import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.service.MailService;
import com.qlove.server.rms.service.UserService;
import com.qlove.server.rms.util.ApplicationTypeConfig;
import com.qlove.server.rms.util.MailUtil;
import com.qlove.server.rms.util.SystemConfig;

@Service("mailService")
public class MailServiceImpl implements MailService{
	@javax.annotation.Resource
	private BaseDao baseDao;

	@Override
	public void sendApplicationMail(int type, Application application, String receiver) {
		// TODO Auto-generated method stub
		MailUtil mailUtil=new MailUtil();
		Resource resource=(Resource)baseDao.get(Resource.class, application.getRid());
		//���ڿ��ʲ����봴����Ӧ���ʼ�
		if(type==ApplicationTypeConfig.APPLY_RESOURCE){
			String htmlEMail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
					+application.getReceiver()+"���һ���ʲ���������:</h4>"
					+"<h4 style='margin-left:50px;'>����ʱ�䣺"+application.getTime()+"</h4>"
					+"<h4 style='margin-left:50px;'>��Ʒ���ƣ�"+resource.getName()+"</h4>"
					+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>ǰ������</a>"
					+"</body></html>";
			mailUtil.doSendHtmlEmail("RMSϵͳ�����ʲ���������", htmlEMail, receiver+"@shuzijiayuan.com");
		}//���ʲ��黹������Ӧ���ʼ�
		else if(type==ApplicationTypeConfig.RETURN_RESOURCE){
			String htmlEMail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
					+application.getOwner()+"���һ���ʲ��黹����:</h4>"
					+"<h4 style='margin-left:50px;'>����ʱ�䣺"+application.getTime()+"</h4>"
					+"<h4 style='margin-left:50px;'>��Ʒ���ƣ�"+resource.getName()+"</h4>"
					+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>ǰ������</a>"
					+"</body></html>";
			mailUtil.doSendHtmlEmail("RMSϵͳ�����ʲ��黹����", htmlEMail, receiver+"@shuzijiayuan.com");
		}//���ʲ�ת�ƴ�����Ӧ���ʼ�
		else if(type==ApplicationTypeConfig.TRANSFER_RESOURCE){
			String htmlEMail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
					+application.getOwner()+"���һ���ʲ�ת������:</h4>"
					+"<h4 style='margin-left:50px;'>����ʱ�䣺"+application.getTime()+"</h4>"
					+"<h4 style='margin-left:50px;'>��Ʒ���ƣ�"+resource.getName()+"</h4>"
					+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>ǰ������</a>"
					+"</body></html>";
			mailUtil.doSendHtmlEmail("RMSϵͳ�����ʲ�ת������", htmlEMail, receiver+"@shuzijiayuan.com");
		}
	}

	@Override
	public void sendPurchaseApplicationMail(PurchaseApplication application, String receiver) {
		// TODO Auto-generated method stub
		MailUtil mailUtil=new MailUtil();
		String htmlEmail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
		+application.getUser()+"���һ���ʲ���������:</h4>"
			+"<h4 style='margin-left:50px;'>����ʱ�䣺"+application.getTime()+"</h4>"
			+"<h4 style='margin-left:50px;'>������Ʒ��"+application.getName()+"</h4>"
			+"<h4 style='margin-left:50px;'>����������"+application.getNumber()+"</h4>"
			+"<h4 style='margin-left:50px;'>�깺������"+application.getDescription()+"</h4>"
			+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>ǰ������</a>"
			+"</body></html>";
		mailUtil.doSendHtmlEmail("RMSϵͳ�����ʲ���������", htmlEmail, receiver+"@shuzijiayuan.com");
	}

	@Override
	public void sendApplicationMail(int type, Application application, List<String> receivers) {
		// TODO Auto-generated method stub
		final int temp_type=type;
		final Application temp_application=application;
		for (String receiver : receivers) {
			final String temp_receiver=receiver;
			Thread thread=new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					sendApplicationMail(temp_type, temp_application, temp_receiver);
				}
			});
			thread.start();
		}
	}

	@Override
	public void sendPurchaseApplicationMail(PurchaseApplication application, List<String> receivers) {
		// TODO Auto-generated method stub
		final PurchaseApplication temp_application=application;
		for (String receiver : receivers) {
			final String temp_receiver=receiver;
			Thread thread=new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					sendPurchaseApplicationMail(temp_application, temp_receiver);
				}
			});
			thread.start();
		}
	}

}
