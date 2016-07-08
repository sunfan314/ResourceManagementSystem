package net.cn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cn.dao.BaseDao;
import net.cn.model.Application;
import net.cn.model.ApplicationApproval;
import net.cn.model.Flow;
import net.cn.model.Log;
import net.cn.model.Property;
import net.cn.model.PurchaseApplication;
import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.FlowService;
import net.cn.service.ResourceService;
import net.cn.service.UserService;
import net.cn.util.ApplicationFlowConfig;
import net.cn.util.ApplicationTypeConfig;
import net.cn.util.LDAP;
import net.cn.util.TimeUtil;
import net.cn.util.UserGroupConfig;

@Service("userService")
public class UserServiceImpl implements UserService {
	@javax.annotation.Resource
	private ResourceService resourceService;
	
	@javax.annotation.Resource
	private FlowService flowService;
	
	@javax.annotation.Resource
	private BaseDao baseDao;

	@Override
	public boolean login(String uid, String password) {
		// TODO Auto-generated method stub
		LDAP ldap = new LDAP();
		return ldap.userVerify(uid, password);
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
			Flow flow = (Flow)baseDao.get(Flow.class, ApplicationFlowConfig.MANAGER_AMDIN_FLOW);
			application.setFlow(flow);
			baseDao.save(application);
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
			return true;
		} else {
			return false;
		}

	}

	public boolean createResourceReturnApplication(int rid, String uid, String remark) {
		// TODO Auto-generated method stub
		if (resourceService.ifResourceOwner(rid, uid)) {
			Application application = new Application(rid, uid, "warehouse", remark,
					ApplicationTypeConfig.RETURN_RESOURCE);
			Flow flow = (Flow)baseDao.get(Flow.class, ApplicationFlowConfig.ADMIN_FLOW);
			application.setFlow(flow);
			baseDao.save(application);
			return true;
		} else {
			// 资产不属于该用户，用户不具有归还资产的权限
			return false;
		}

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
	public List<Application> getResourceRetrunApplications(String uid) {
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
			Type type=(Type)baseDao.get(Type.class, resource.getType());
			resource.setTypeName(type.getName());
			application.setResource(resource);
		}
		return list;
	}


}
