package net.cn.service;

import java.util.List;

import net.cn.model.Application;
import net.cn.model.PurchaseApplication;

public interface ManagerService {
	
	/**
	 * @return	��ȡ�ʲ����������б�
	 */
	public List<Application> getResourceAllocationApplications();
	
	/**
	 * @return	��ȡ�ʲ����������б�
	 */
	public List<PurchaseApplication> getResourcePurchaseApplications();
	
	/**
	 * @param application
	 * ��׼�ʲ���������
	 */
	public void agreeResourceAllocationApplication(Application application);
	
	/**
	 * @param request
	 * ��׼�ʲ���������
	 */
	public void agreeResourcePurchaseApplication(PurchaseApplication application);
	
	/**
	 * @param application
	 * �ܾ��ʲ���������
	 */
	public void refuseResourceAllocationApplication(Application application);
	
	/**
	 * @param request
	 * �ܾ��ʲ���������
	 */
	public void refuseResourcePurchaseApplication(PurchaseApplication application);

}
