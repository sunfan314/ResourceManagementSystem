package net.cn.service.impl;

import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import net.cn.dao.BaseDao;
import net.cn.model.Application;
import net.cn.model.ApplicationApproval;
import net.cn.model.PurchaseApplication;
import net.cn.model.PurchaseApplicationApproval;
import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.FlowService;
import net.cn.service.ManagerService;
import net.cn.util.ApplicationFlowConfig;
import net.cn.util.ApplicationTypeConfig;
import net.cn.util.UserGroupConfig;

@Service("managerService")
@SuppressWarnings("all")
public class ManagerServiceImpl implements ManagerService{
	@javax.annotation.Resource
	private BaseDao baseDao;
	
	@javax.annotation.Resource
	private FlowService flowService;

	@Override
	public List<Application> getResourceAllocationApplications() {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add(ApplicationTypeConfig.APPLY_RESOURCE);
		params.add(flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER));
		params.add(0);//申请未结束
		List<Application> list=baseDao.find("from Application where type = ? and step = ? and finished = ?",params);
		return setApplicationResource(list);//为申请绑定对应的资产信息
	}

	@Override
	public List<PurchaseApplication> getResourcePurchaseApplications() {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add(flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER));
		params.add(0);//申请未结束
		return baseDao.find("from PurchaseApplication where step = ? and finished = ?", params);
	}

	@Override
	public void agreeResourceAllocationApplication(int applicationId,String uid) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 0, "");
		baseDao.save(approval);//添加审核信息
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setStep(step+1);
		baseDao.update(application);//修改申请信息，流程进入下一步
		
		
	}

	@Override
	public void agreeResourcePurchaseApplication(int applicationId,String uid) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER);
		PurchaseApplicationApproval approval=new PurchaseApplicationApproval(applicationId, step, uid, 0, "");
		baseDao.save(approval);//添加审核信息
		
		PurchaseApplication application=(PurchaseApplication)baseDao.get(PurchaseApplication.class, applicationId);
		application.setStep(step+1);
		baseDao.update(application);//修改申请信息，流程进入下一步
	}

	@Override
	public void refuseResourceAllocationApplication(int applicationId,String uid,String remark) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 1, remark);
		baseDao.save(approval);//添加审核信息
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setFinished(1);
		application.setRefused(1);
		baseDao.update(application);//结束资产分配申请
		
	}

	@Override
	public void refuseResourcePurchaseApplication(int applicationId,String uid,String remark) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER);
		PurchaseApplicationApproval approval=new PurchaseApplicationApproval(applicationId, step, uid, 1, remark);
		baseDao.save(approval);//添加审核信息
		
		PurchaseApplication application=(PurchaseApplication)baseDao.get(PurchaseApplication.class, applicationId);
		application.setFinished(1);
		application.setRefused(1);
		baseDao.update(application);//结束资产购买申请
	}
	
	/**
	 * 设置申请对应的资产信息
	 * @param list
	 */
	private List<Application> setApplicationResource(List<Application> list){
		for (Application application : list) {
			Resource resource=(Resource)baseDao.get(Resource.class, application.getRid());
			application.setResource(resource);
			application.setResourceName(resource.getName());
		}
		return list;
	}

}
