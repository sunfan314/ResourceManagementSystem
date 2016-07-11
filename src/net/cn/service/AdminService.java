package net.cn.service;

import java.util.List;

import net.cn.model.Application;
import net.cn.model.PurchaseApplication;
import net.cn.model.Resource;

public interface AdminService {
	
	/**
	 * @param resource
	 * 入库新购资产
	 */
	public void addNewResource(Resource resource);
	
	/**
	 * @return	获取资产分配申请列表
	 */
	public List<Application> getResourceAllocationApplications();
	
	/**
	 * @return	获取资产回收申请列表
	 */
	public List<Application> getResourceCallbackApplications();
	
	/**
	 * @return	获取资产购买申请列表
	 */
	public List<Application> getResourcePurchaseApplications();
	
	
	/**
	 * @param applicationId
	 * @param uid
	 * @return	批准资产分配申请
	 */
	public boolean agreeResourceAllocationApplication(int applicationId,String uid);
	
	/**
	 * 拒绝资产分配申请
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourceAllocationApplication(int applicationId,String uid,String remark);
	
	/**
	 * @param applicationId
	 * @param uid
	 * @return	资产回收入库
	 */
	public boolean agreeResourceCallbackApplication(int applicationId,String uid);
	
	/**
	 * 拒绝资产回收入库请求
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourceCallbackApplication(int applicationId,String uid,String remark);

}
