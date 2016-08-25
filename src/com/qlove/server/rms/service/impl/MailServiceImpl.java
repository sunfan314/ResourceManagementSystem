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
		//对在库资产申请创建对应的邮件
		if(type==ApplicationTypeConfig.APPLY_RESOURCE){
			String htmlEMail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
					+application.getReceiver()+"提出一条资产分配申请:</h4>"
					+"<h4 style='margin-left:50px;'>申请时间："+application.getTime()+"</h4>"
					+"<h4 style='margin-left:50px;'>物品名称："+resource.getName()+"</h4>"
					+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>前往处理</a>"
					+"</body></html>";
			mailUtil.doSendHtmlEmail("RMS系统——资产分配申请", htmlEMail, receiver+"@shuzijiayuan.com");
		}//对资产归还创建对应的邮件
		else if(type==ApplicationTypeConfig.RETURN_RESOURCE){
			String htmlEMail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
					+application.getOwner()+"提出一条资产归还申请:</h4>"
					+"<h4 style='margin-left:50px;'>申请时间："+application.getTime()+"</h4>"
					+"<h4 style='margin-left:50px;'>物品名称："+resource.getName()+"</h4>"
					+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>前往处理</a>"
					+"</body></html>";
			mailUtil.doSendHtmlEmail("RMS系统——资产归还申请", htmlEMail, receiver+"@shuzijiayuan.com");
		}//对资产转移创建对应的邮件
		else if(type==ApplicationTypeConfig.TRANSFER_RESOURCE){
			String htmlEMail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
					+application.getOwner()+"提出一条资产转移申请:</h4>"
					+"<h4 style='margin-left:50px;'>申请时间："+application.getTime()+"</h4>"
					+"<h4 style='margin-left:50px;'>物品名称："+resource.getName()+"</h4>"
					+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>前往处理</a>"
					+"</body></html>";
			mailUtil.doSendHtmlEmail("RMS系统——资产转移申请", htmlEMail, receiver+"@shuzijiayuan.com");
		}
	}

	@Override
	public void sendPurchaseApplicationMail(PurchaseApplication application, String receiver) {
		// TODO Auto-generated method stub
		MailUtil mailUtil=new MailUtil();
		String htmlEmail="<html><head></head><body><h3>Hi "+receiver+":</h3><h4 style='margin-left:50px;'>"
		+application.getUser()+"提出一条资产购买申请:</h4>"
			+"<h4 style='margin-left:50px;'>申请时间："+application.getTime()+"</h4>"
			+"<h4 style='margin-left:50px;'>申请物品："+application.getName()+"</h4>"
			+"<h4 style='margin-left:50px;'>申请数量："+application.getNumber()+"</h4>"
			+"<h4 style='margin-left:50px;'>申购描述："+application.getDescription()+"</h4>"
			+ "<a style='margin-left:50px;' href='"+SystemConfig.PROJECT_PATH+"'>前往处理</a>"
			+"</body></html>";
		mailUtil.doSendHtmlEmail("RMS系统——资产购买申请", htmlEmail, receiver+"@shuzijiayuan.com");
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
