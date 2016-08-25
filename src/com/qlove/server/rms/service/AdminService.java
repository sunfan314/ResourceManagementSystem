package com.qlove.server.rms.service;

import java.util.List;

import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.model.Property;
import com.qlove.server.rms.model.PurchaseApplication;
import com.qlove.server.rms.model.Resource;

public interface AdminService {
	
	/**
	 * 入库新购资产
	 * @param resource
	 */
	public void addNewResource(Resource resource);
	
	/**	
	 * 修改资产信息
	 * @param resource
	 */
	public void editResource(Resource resource);
	
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
	public List<PurchaseApplication> getResourcePurchaseApplications();
	
	
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
	
	/**
	 * 同意资产购买申请
	 * @param applicationId
	 * @param uid
	 */
	public void agreeResourcePurchaseApplication(int applicationId,String uid);
	
	/**
	 * 拒绝资产购买申请
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourcePurchaseApplication(int applicationId,String uid,String remark);

}
