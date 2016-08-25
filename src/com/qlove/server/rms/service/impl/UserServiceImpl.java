package com.qlove.server.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.qlove.server.rms.dao.BaseDao;
import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.model.ApplicationApproval;
import com.qlove.server.rms.model.Flow;
import com.qlove.server.rms.model.Log;
import com.qlove.server.rms.model.Property;
import com.qlove.server.rms.model.PurchaseApplication;
import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.model.Type;
import com.qlove.server.rms.service.FlowService;
import com.qlove.server.rms.service.MailService;
import com.qlove.server.rms.service.ResourceService;
import com.qlove.server.rms.service.UserService;
import com.qlove.server.rms.util.ApplicationFlowConfig;
import com.qlove.server.rms.util.ApplicationTypeConfig;
import com.qlove.server.rms.util.LDAP;
import com.qlove.server.rms.util.TimeUtil;
import com.qlove.server.rms.util.UserGroupConfig;

@Service("userService")
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {
	@javax.annotation.Resource
	private ResourceService resourceService;
	
	@javax.annotation.Resource
	private FlowService flowService;
	
	@javax.annotation.Resource
	private BaseDao baseDao;
	
	@javax.annotation.Resource
	private MailService mailService;

	@Override
	public boolean login(String uid, String password) {
		// TODO Auto-generated method stub
		LDAP ldap = new LDAP();
		return ldap.userVerify(uid, password);
	}

	@Override
	public int getUserAuthority(String uid) {
		// TODO Auto-generated method stub
		LDAP ldap=new LDAP();
		if(ldap.getAdmins().contains(uid)){
			return UserGroupConfig.ADMIN;
		}else if(ldap.getManagers().contains(uid)){
			return UserGroupConfig.MANAGER;
		}else{
			return UserGroupConfig.USER;
		}
	}

	@Override
	public List<String> getUserWithResources() {
		// TODO Auto-generated method stub
		List<String> userList = new ArrayList<>();
		List<Resource> resources = baseDao.find("from Resource");
		for (Resource resource : resources) {
			userList.add(resource.getOwner());
		}
		return filterRepeatedUser(userList);

	}

	/**
	 * @param userList
	 * @return 过滤重复用户
	 */
	private List<String> filterRepeatedUser(List<String> userList) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		for (String str : userList) {
			boolean repeated = false;
			for (String str2 : list) {
				if (str.equals(str2)) {
					repeated = true;
				}
			}
			if (!repeated) {
				list.add(str);
			}
		}
		// 去除库存
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals("warehouse")) {
					list.remove(i);
					break;
				}
			}
		}
		return list;
	}

	@Override
	public List<Property> getUsers() {
		// TODO Auto-generated method stub
		LDAP ldap = new LDAP();
		List<String> userList = ldap.getUsers();
		List<Property> list = new ArrayList<>();
		for (String str : userList) {
			Property property = new Property(str, str);
			list.add(property);
		}
		return list;
	}

	@Override
	public List<Property> getUsers(String uid) {
		// TODO Auto-generated method stub
		LDAP ldap = new LDAP();
		List<String> userList = ldap.getUsers();
		List<Property> list = new ArrayList<>();
		for (String str : userList) {
			if (str.equals(uid)) {
				continue;
			} else {
				Property property = new Property(str, str);
				list.add(property);
			}
		}
		return list;
	}
	
	@Override
	public boolean createResourceApplyApplication(int rid, String uid, String remark) {
		// TODO Auto-generated method stub
		if (resourceService.isResourceAvailable(rid)) {
			Application application = new Application(rid, "warehouse", uid, remark,
					ApplicationTypeConfig.APPLY_RESOURCE);
			Flow flow = (Flow)baseDao.get(Flow.class, ApplicationFlowConfig.MANAGER_ADMIN_FLOW);
			application.setFlow(flow);
			baseDao.save(application);
			//创建发送邮件线程
			final Application temp_application=application;
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					LDAP ldap=new LDAP();
					//向经理发送邮件
					mailService.sendApplicationMail(ApplicationTypeConfig.APPLY_RESOURCE, temp_application, ldap.getManagers());
				}
			});
			thread.start();
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	public boolean createResourceTransferApplication(int rid, String uid, String receiver, String remark) {
		// TODO Auto-generated method stub
		if (resourceService.ifResourceOwner(rid, uid)) {
			Application application = new Application(rid, uid, receiver, remark,
					ApplicationTypeConfig.TRANSFER_RESOURCE);
			Flow flow = (Flow)baseDao.get(Flow.class, ApplicationFlowConfig.RECEIVER_FLOW);
			application.setFlow(flow);
			baseDao.save(application);
			//创建发送邮件线程
			final Application temp_application=application;
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//向资产接收者发送邮件
					mailService.sendApplicationMail(ApplicationTypeConfig.TRANSFER_RESOURCE, temp_application, temp_application.getReceiver());
				}
			});
			thread.start();
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean createResourceReturnApplication(int rid, String uid, String remark) {
		// TODO Auto-generated method stub
		if (resourceService.ifResourceOwner(rid, uid)) {
			Application application = new Application(rid, uid, "warehouse", remark,
					ApplicationTypeConfig.RETURN_RESOURCE);
			Flow flow = (Flow)baseDao.get(Flow.class, ApplicationFlowConfig.ADMIN_FLOW);
			application.setFlow(flow);
			baseDao.save(application);
			//创建发送邮件线程
			final Application temp_application=application;
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					LDAP ldap=new LDAP();
					//向管理员发送邮件
					mailService.sendApplicationMail(ApplicationTypeConfig.RETURN_RESOURCE, temp_application, ldap.getAdmins());
				}
			});
			thread.start();
			return true;
		} else {
			// 资产不属于该用户，用户不具有归还资产的权限
			return false;
		}

	}
	
	@Override
	public void createResourcePurchaseApplication(PurchaseApplication application) {
		// TODO Auto-generated method stub
		PurchaseApplication purchaseApplication=new PurchaseApplication(application);
		Flow flow=(Flow)baseDao.get(Flow.class, ApplicationFlowConfig.MANAGER_ADMIN_FLOW);
		purchaseApplication.setFlow(flow);
		baseDao.save(purchaseApplication);
		//创建发送邮件线程
		final PurchaseApplication temp_application=purchaseApplication;	
		Thread thread=new Thread(new Runnable() {	
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//向经理发送邮件
				LDAP ldap=new LDAP();
				mailService.sendPurchaseApplicationMail(temp_application, ldap.getManagers());
			}
		});
		thread.start();
	}

	@Override
	public Application getApplicationInfo(int applicationId) {
		// TODO Auto-generated method stub
		Application application=(Application)baseDao.get(Application.class, applicationId);
		Resource resource=(Resource)baseDao.get(Resource.class, application.getRid());
		application.setResource(resource);
		return application;
	}
	
	@Override
	public PurchaseApplication getPurchaseApplicationInfo(int applicationId) {
		// TODO Auto-generated method stub
		PurchaseApplication application=(PurchaseApplication)baseDao.get(PurchaseApplication.class, applicationId);
		return application;
	}

	@Override
	public List<Application> getResourceApplyApplications(String uid) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add(ApplicationTypeConfig.APPLY_RESOURCE);
		params.add(uid);
		List<Application> list=baseDao.find("from Application where type = ? and receiver = ?",params);
		return setApplicationResource(list);//为申请绑定对应的资产信息
	}

	@Override
	public List<Application> getResourceTransferApplications(String uid) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add(ApplicationTypeConfig.TRANSFER_RESOURCE);
		params.add(uid);
		List<Application> list=baseDao.find("from Application where type = ? and owner = ?",params);
		return setApplicationResource(list);//为申请绑定对应的资产信息
	}

	@Override
	public List<Application> getResourceReturnApplications(String uid) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add(ApplicationTypeConfig.RETURN_RESOURCE);
		params.add(uid);
		List<Application> list= baseDao.find("from Application where type = ? and owner = ?",params);
		return setApplicationResource(list);//为申请绑定对应的资产信息
	}

	@Override
	public List<Application> getReceivedResourceTransferApplication(String uid) {
		// TODO Auto-generated method stub
		
		List<Object> params=new ArrayList<>();
		params.add(uid);
		params.add(ApplicationTypeConfig.TRANSFER_RESOURCE);
		params.add(flowService.getStepInFlow(ApplicationFlowConfig.RECEIVER_FLOW, UserGroupConfig.USER));
		params.add(0);//申请未结束
		List<Application> list=baseDao.find("from Application where receiver = ? and type = ? and step = ? and finished = ?",params);
		return setApplicationResource(list);//为申请绑定对应的资产信息
	}

	@Override
	public List<PurchaseApplication> getResourcePurchaseApplications(String uid) {
		// TODO Auto-generated method stub
		return baseDao.find("from PurchaseApplication where user = ?",uid);
	}

	@Override
	public boolean acceptResourceTransferApplication(int applicationId, String uid) {
		// TODO Auto-generated method stub
		Application application=(Application)baseDao.get(Application.class, applicationId);
		Resource resource=(Resource)baseDao.get(Resource.class,application.getRid());
		//判断申请发起人是否具有资产的转移权限
		if(resourceService.ifResourceOwner(resource.getId(), application.getOwner())){
			//判断用户是否为转移申请的接收人
			if(uid.equals(application.getReceiver())){
				
				int step=flowService.getStepInFlow(ApplicationFlowConfig.RECEIVER_FLOW, UserGroupConfig.USER);
				ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 0, "");
				baseDao.save(approval);//添加申请审核信息
				
				application.setFinished(1);
				baseDao.update(application);//结束资产转移申请
				
				resource.setOwner(uid);
				baseDao.update(resource);//修改资产拥有人状态
				
				List<Object> params=new ArrayList<>();
				params.add(resource.getId());
				params.add(application.getOwner());
				params.add("");
				Log lastLog=(Log)baseDao.get("from Log where rid = ? and owner = ? and endTime = ?", params);
				lastLog.setEndTime(TimeUtil.getCurrentTime());
				baseDao.update(lastLog);//修改资产使用日志信息
				
				Log nextLog=new Log(resource.getId(), uid, application.getRemark());
				baseDao.save(nextLog);//新增资产使用日志信息
				
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}	
	}

	@Override
	public void refuseResourceTransferApplication(int applicationId, String uid, String remark) {
		// TODO Auto-generated method stub
		int step=flowService.getStepInFlow(ApplicationFlowConfig.RECEIVER_FLOW, UserGroupConfig.USER);
		ApplicationApproval approval=new ApplicationApproval(applicationId, step, uid, 1, remark);
		baseDao.save(approval);//添加申请审核信息
		
		Application application=(Application)baseDao.get(Application.class, applicationId);
		application.setFinished(1);
		application.setRefused(1);
		baseDao.update(application);//结束资产转移申请
			
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
