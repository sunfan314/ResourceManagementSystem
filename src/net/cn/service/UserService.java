package net.cn.service;

import java.util.List;
import net.cn.model.Application;
import net.cn.model.Property;


public interface UserService {
	
	/**
	 * @param uid
	 * @param password
	 * @return	用户身份验证
	 */
	public boolean login(String uid,String password);
	
	/**
	 * @param uid
	 * @return	获取用户权限
	 */
	public int getUserAuthority(String uid);
	
	
	/**
	 * @return	获取拥有资产的用户列表
	 */
	public List<String> getUserWithResources();
	
	/**
	 * @return	获取所有用户
	 */
	public List<Property> getUsers();
	
	/**
	 * @param uid
	 * @return	获取所有用户（除去某个用户uid）
	 */
	public List<Property> getUsers(String uid);
	
	/**
	 * @param rid
	 * @param uid
	 * @param remark
	 * @return	创建资产申请请求
	 */
	public boolean createResourceApplyApplication(int rid,String uid,String remark);
	
	/**
	 * @param rid
	 * @param uid	请求发起人
	 * @param receiver
	 * @param remark
	 * @return	创建资产转移请求
	 */
	public boolean createResourceTransferApplication(int rid,String uid,String receiver,String remark);
	
	/**
	 * @param rid	资产标识
	 * @param uid	请求发起人
	 * @return	创建资产归还请求
	 */
	public boolean createResourceReturnApplication(int rid,String uid,String remark);
	
	/**
	 * @param applicationId
	 * @return	获取申请的详细信息
	 */
	public Application getApplicationInfo(int applicationId);
	
	/**
	 * @param uid
	 * @return	获得用户发起的资产分配申请（包含已处理和未处理的）
	 */
	public List<Application> getResourceApplyApplications(String uid);
	
	/**
	 * @param uid
	 * @return	获得用户发起的资产转移申请
	 */
	public List<Application> getResourceTransferApplications(String uid);
	
	/**
	 * @param uid
	 * @return	获得用户发起的资产归还申请（包含已处理和未处理的）
	 */
	public List<Application> getResourceReturnApplications(String uid);
	
	/**
	 * @param uid
	 * @return	获得用户收到的资产转移申请（未处理的）
	 */
	public List<Application> getReceivedResourceTransferApplication(String uid);
	
	/**
	 * @param applicationId
	 * @param uid
	 * @return	接受资产转移申请
	 */
	public boolean acceptResourceTransferApplication(int applicationId,String uid);
	
	
	/**
	 * 拒绝资产转移申请
	 * @param applicationId
	 * @param uid
	 * @param remark
	 */
	public void refuseResourceTransferApplication(int applicationId,String uid,String remark);
	
	


}
