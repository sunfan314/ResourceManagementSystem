package net.cn.service;

import java.util.List;

import net.cn.model.Log;
import net.cn.model.Resource;
import net.cn.model.Type;

/**
 * @author sunfan314
 *
 */
public interface ResourceService {
	
	/**
	 * @param rid
	 * @param uid
	 * @return	查看用户是否为资产当前拥有者
	 */
	public boolean ifResourceOwner(int rid,String uid);
	
	/**
	 * @param rid
	 * @return	查看资产是否处于空闲状态
	 */
	public boolean isResourceAvailable(int rid);
	
	/**
	 * @param typeId
	 * @return	根据类型Id获取类型
	 */
	public Type getType(int typeId);
	
	/**
	 * @return	获取资产类别列表
	 */
	public List<Type> getResourceTypes();
	
	/**	
	 * 添加新类别
	 * @param typeName
	 * @param fatherType
	 */
	public void addType(String typeName,int fatherType);
	
	
	/**
	 * @param type
	 * @return	获取企业某类资产列表
	 */
	public List<Resource> getCompanyResources(int type);
	
	/**
	 * @param type
	 * @return	获取企业某类别下在库的资产列表
	 */
	public List<Resource> getAvailableResources(int type);
	
	/**
	 * @param uid
	 * @return	获取个人资产列表
	 */
	public List<Resource> getPersonalResources(String uid);
	
	/**
	 * @param rid
	 * @return	获取资产使用记录列表
	 */
	public List<Log> getResourceLogs(int rid);
	
	/**
	 * @param rid
	 * @return	根据资产标识获取资产信息
	 */
	public Resource getResource(int rid);
	
	
	/**
	 * @param type
	 * @return	获取类别的父类别
	 */
	public int getFatherType(int type);
	

}
