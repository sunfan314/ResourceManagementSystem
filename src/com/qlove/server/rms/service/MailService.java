package com.qlove.server.rms.service;

import java.util.List;

import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.model.PurchaseApplication;

public interface MailService {
	
	/**
	 * ���ʲ����䡢�ʲ��黹���ʲ�ת�����뷢�Ͷ�Ӧ���ʼ����Ѵ����˽��д���
	 * @param type
	 * @param application
	 * @param receiver
	 */
	public void sendApplicationMail(int type,Application application,String receiver);
	
	/**
	 * �Զ���û������ʼ�
	 * @param type
	 * @param application
	 * @param receivers
	 */
	public void sendApplicationMail(int type,Application application,List<String> receivers);
	
	/**
	 * �Զ���û������ʼ�
	 * @param application
	 * @param receivers
	 */
	public void sendPurchaseApplicationMail(PurchaseApplication application,List<String> receivers);
	/**
	 * ���ʲ��������뷢���ʼ����Ѵ����˽��д���
	 * @param application
	 * @param receiver
	 */
	public void sendPurchaseApplicationMail(PurchaseApplication application,String receiver);


}
