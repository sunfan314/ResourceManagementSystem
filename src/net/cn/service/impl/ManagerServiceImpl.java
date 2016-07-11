package net.cn.service.impl;

import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import net.cn.dao.BaseDao;
import net.cn.model.Application;
import net.cn.model.ApplicationApproval;
import net.cn.model.PurchaseApplication;
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
		params.add(0);//����δ����
		List<Application> list=baseDao.find("from Application where type = ? and step = ? and finished = ?",params);
		return setApplicationResource(list);//Ϊ����󶨶�Ӧ���ʲ���Ϣ
	}

	@Override
	public List<PurchaseApplication> getResourcePurchaseApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agreeResourceAllocationApplication(int applicationId,String uid) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 0, "");
		baseDao.save(approval);//��������Ϣ
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setStep(step+1);
		baseDao.update(application);//�޸�������Ϣ�����̽�����һ��
		
		
	}

	@Override
	public void agreeResourcePurchaseApplication(PurchaseApplication application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refuseResourceAllocationApplication(int applicationId,String uid,String remark) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.MANAGER_ADMIN_FLOW, UserGroupConfig.MANAGER);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 1, remark);
		baseDao.save(approval);//��������Ϣ
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setFinished(1);
		application.setRefused(1);
		baseDao.update(application);//�����ʲ���������
		
	}

	@Override
	public void refuseResourcePurchaseApplication(PurchaseApplication application) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ���������Ӧ���ʲ���Ϣ
	 * @param list
	 */
	private List<Application> setApplicationResource(List<Application> list){
		for (Application application : list) {
			Resource resource=(Resource)baseDao.get(Resource.class, application.getRid());
			Type type=(Type)baseDao.get(Type.class, resource.getType());
			resource.setTypeName(type.getName());
			application.setResource(resource);
			application.setResourceName(resource.getName());
		}
		return list;
	}

}
