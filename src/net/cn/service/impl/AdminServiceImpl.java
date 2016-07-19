package net.cn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cn.dao.BaseDao;
import net.cn.model.Application;
import net.cn.model.ApplicationApproval;
import net.cn.model.Log;
import net.cn.model.Property;
import net.cn.model.PurchaseApplication;
import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.AdminService;
import net.cn.service.FlowService;
import net.cn.service.ResourceService;
import net.cn.util.ApplicationFlowConfig;
import net.cn.util.ApplicationTypeConfig;
import net.cn.util.TimeUtil;
import net.cn.util.UserGroupConfig;

@Service("adminService")
@SuppressWarnings("all")
public class AdminServiceImpl implements AdminService {
	@javax.annotation.Resource
	private BaseDao baseDao;

	@javax.annotation.Resource
	private FlowService flowService;

	@javax.annotation.Resource
	private ResourceService resourceService;

	@Override
	public void addNewResource(Resource resource) {
		// TODO Auto-generated method stub
		//获取最大id
		int id=(Integer)baseDao.find("select max(id) from Resource").get(0)+1;
		resource.setId(id);
		baseDao.save(resource);
		Log log=new Log(id, resource.getOwner(), resource.getEntryDate(), resource.getRemark());
		baseDao.save(log);
	}

	@Override
	public void editResource(Resource resource) {
		// TODO Auto-generated method stub
		baseDao.update(resource);
	}

	@Override
	public List<Application> getResourceAllocationApplications() {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<>();
		params.add(ApplicationTypeConfig.APPLY_RESOURCE);
		params.add(flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.ADMIN));
		params.add(0);// 申请未结束
		List<Application> list = baseDao.find("from Application where type = ? and step = ? and finished = ?", params);
		return setApplicationResource(list);// 为申请绑定对应的资产信息
	}

	@Override
	public List<Application> getResourceCallbackApplications() {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<>();
		params.add(ApplicationTypeConfig.RETURN_RESOURCE);
		params.add(flowService.getStepInFlow(ApplicationFlowConfig.ADMIN_FLOW, UserGroupConfig.ADMIN));
		params.add(0);// 申请未结束
		List<Application> list = baseDao.find("from Application where type = ? and step = ? and finished = ?", params);
		return setApplicationResource(list);// 为申请绑定对应的资产信息
	}

	@Override
	public List<Application> getResourcePurchaseApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean agreeResourceAllocationApplication(int applicationId, String uid) {
		// TODO Auto-generated method stub
		Application application = (Application) baseDao.get(Application.class, applicationId);
		Resource resource = (Resource) baseDao.get(Resource.class, application.getRid());
		// 判断资产是否在库
		if (resourceService.ifResourceOwner(resource.getId(), application.getOwner())) {
			int step = flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.ADMIN);
			ApplicationApproval approval = new ApplicationApproval(applicationId, step, uid, 0, "");
			baseDao.save(approval);// 添加申请审核信息

			application.setFinished(1);
			baseDao.update(application);// 结束资产分配申请

			resource.setOwner(application.getReceiver());
			baseDao.update(resource);// 修改资产拥有人状态

			List<Object> params = new ArrayList<>();
			params.add(resource.getId());
			params.add(application.getOwner());
			params.add("");
			Log lastLog = (Log) baseDao.get("from Log where rid = ? and owner = ? and endTime = ?", params);
			lastLog.setEndTime(TimeUtil.getCurrentTime());
			baseDao.update(lastLog);// 修改资产使用日志信息

			Log nextLog = new Log(resource.getId(), application.getReceiver(), application.getRemark());
			baseDao.save(nextLog);// 新增资产使用日志信息

			return true;

		} else {
			return false;
		}
	}

	@Override
	public void refuseResourceAllocationApplication(int applicationId, String uid, String remark) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.ADMIN);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 1, remark);
		baseDao.save(approval);//添加申请审核信息
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setFinished(1);
		application.setRefused(1);
		baseDao.update(application);//结束资产转移申请
			

	}
	
	@Override
	public boolean agreeResourceCallbackApplication(int applicationId, String uid) {
		// TODO Auto-generated method stub
		Application application = (Application) baseDao.get(Application.class, applicationId);
		Resource resource = (Resource) baseDao.get(Resource.class, application.getRid());
		// 判断资产是否属于资产回收申请人
		if (resourceService.ifResourceOwner(resource.getId(), application.getOwner())) {
			int step = flowService.getStepInFlow(ApplicationFlowConfig.ADMIN_FLOW, UserGroupConfig.ADMIN);
			ApplicationApproval approval = new ApplicationApproval(applicationId, step, uid, 0, "");
			baseDao.save(approval);// 添加申请审核信息

			application.setFinished(1);
			baseDao.update(application);// 结束资产转移申请

			resource.setOwner(application.getReceiver());
			baseDao.update(resource);// 修改资产拥有人状态

			List<Object> params = new ArrayList<>();
			params.add(resource.getId());
			params.add(application.getOwner());
			params.add("");
			Log lastLog = (Log) baseDao.get("from Log where rid = ? and owner = ? and endTime = ?", params);
			lastLog.setEndTime(TimeUtil.getCurrentTime());
			baseDao.update(lastLog);// 修改资产使用日志信息

			Log nextLog = new Log(resource.getId(), application.getReceiver(), application.getRemark());
			baseDao.save(nextLog);// 新增资产使用日志信息

			return true;

		} else {
			return false;
		}
	}

	@Override
	public void refuseResourceCallbackApplication(int applicationId, String uid, String remark) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.ADMIN_FLOW, UserGroupConfig.ADMIN);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 1, remark);
		baseDao.save(approval);//添加申请审核信息
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setFinished(1);
		application.setRefused(1);
		baseDao.update(application);//结束资产转移申请
	}
	
	/**
	 * 设置申请对应的资产信息
	 * 
	 * @param list
	 */
	private List<Application> setApplicationResource(List<Application> list) {
		for (Application application : list) {
			Resource resource = (Resource) baseDao.get(Resource.class, application.getRid());
			application.setResource(resource);
			application.setResourceName(resource.getName());
		}
		return list;
	}


}
