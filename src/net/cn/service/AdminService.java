package net.cn.service;

import java.util.List;

import net.cn.model.Application;
import net.cn.model.PurchaseApplication;
import net.cn.model.Resource;

public interface AdminService {
	
	/**
	 * @param resource
	 * ����¹��ʲ�
	 */
	public void addNewResource(Resource resource);
	
	/**
	 * @return	��ȡ�ʲ����������б�
	 */
	public List<Application> getResourceAllocationApplications();
	
	/**
	 * @return	��ȡ�ʲ����������б�
	 */
	public List<Application> getResourceCallbackApplications();
	
	/**
	 * @return	��ȡ�ʲ����������б�
	 */
	public List<Application> getResourcePurchaseApplications();
	
	
	/**
	 * @param applicationId
	 * @param uid
	 * @return	��׼�ʲ���������
	 */
	public boolean agreeResourceAllocationApplication(int applicationId,String uid);
	
	/**
	 * �ܾ��ʲ���������
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourceAllocationApplication(int applicationId,String uid,String remark);
	
	/**
	 * @param applicationId
	 * @param uid
	 * @return	�ʲ��������
	 */
	public boolean agreeResourceCallbackApplication(int applicationId,String uid);
	
	/**
	 * �ܾ��ʲ������������
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourceCallbackApplication(int applicationId,String uid,String remark);

}
