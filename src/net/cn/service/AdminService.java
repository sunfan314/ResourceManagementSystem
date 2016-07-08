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
	public List<Application> getResourceCallbackApplications();
	
	/**
	 * @param rid
	 * @return	�����ʲ�
	 */
	public boolean returnResource(int rid);
	
	/**
	 * @return	��ȡ�ʲ�������׼�б�
	 */
	public List<Application> getApplicationApprovals();
	
	/**
	 * @param application
	 * @return	�����ڿ��ʲ�
	 */
	public boolean allocateResource(Application application);
	
	/**
	 * @return	��ȡ�ʲ�������׼�б�
	 */
	public List<PurchaseApplication> getPurchaseApprovals();

}
