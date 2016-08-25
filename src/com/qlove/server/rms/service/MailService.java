package com.qlove.server.rms.service;

import java.util.List;

import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.model.PurchaseApplication;

public interface MailService {
	
	/**
	 * 对资产分配、资产归还和资产转移申请发送对应的邮件提醒处理人进行处理
	 * @param type
	 * @param application
	 * @param receiver
	 */
	public void sendApplicationMail(int type,Application application,String receiver);
	
	/**
	 * 对多个用户发送邮件
	 * @param type
	 * @param application
	 * @param receivers
	 */
	public void sendApplicationMail(int type,Application application,List<String> receivers);
	
	/**
	 * 对多个用户发送邮件
	 * @param application
	 * @param receivers
	 */
	public void sendPurchaseApplicationMail(PurchaseApplication application,List<String> receivers);
	/**
	 * 对资产购买申请发送邮件提醒处理人进行处理
	 * @param application
	 * @param receiver
	 */
	public void sendPurchaseApplicationMail(PurchaseApplication application,String receiver);


}
