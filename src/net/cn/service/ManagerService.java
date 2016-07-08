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
	 * @param application
	 * 批准资产分配申请
	 */
	public void agreeResourceAllocationApplication(Application application);
	
	/**
	 * @param request
	 * 批准资产购买申请
	 */
	public void agreeResourcePurchaseApplication(PurchaseApplication application);
	
	/**
	 * @param application
	 * 拒绝资产分配申请
	 */
	public void refuseResourceAllocationApplication(Application application);
	
	/**
	 * @param request
	 * 拒绝资产购买申请
	 */
	public void refuseResourcePurchaseApplication(PurchaseApplication application);

}
