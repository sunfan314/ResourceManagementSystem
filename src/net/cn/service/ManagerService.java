package net.cn.service;

import java.util.List;

import net.cn.model.Application;
import net.cn.model.PurchaseApplication;

public interface ManagerService {
	
	/**
	 * @return	获取资产分配请求列表
	 */
	public List<Application> getResourceAllocationApplications();
	
	/**
	 * @return	获取资产购买申请列表
	 */
	public List<PurchaseApplication> getResourcePurchaseApplications();
	
	
	/**
	 * @param applicationId
	 * @param uid
	 * @return	经理批准资产分配申请
	 */
	public void agreeResourceAllocationApplication(int applicationId,String uid);
	
	/**
	 * @param request
	 * 批准资产购买申请
	 */
	public void agreeResourcePurchaseApplication(int applicationId,String uid);
	
	
	/**
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourceAllocationApplication(int applicationId,String uid,String remark);
	
	/**
	 * @param request
	 * 拒绝资产购买申请
	 */
	public void refuseResourcePurchaseApplication(int applicationId,String uid,String remark);

}
