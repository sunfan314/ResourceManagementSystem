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
	 * @return	获取资产回收申请列表
	 */
	public List<Application> getResourceCallbackApplications();
	
	/**
	 * @param rid
	 * @return	回收资产
	 */
	public boolean returnResource(int rid);
	
	/**
	 * @return	获取资产分配批准列表
	 */
	public List<Application> getApplicationApprovals();
	
	/**
	 * @param application
	 * @return	分配在库资产
	 */
	public boolean allocateResource(Application application);
	
	/**
	 * @return	获取资产购买批准列表
	 */
	public List<PurchaseApplication> getPurchaseApprovals();

}
