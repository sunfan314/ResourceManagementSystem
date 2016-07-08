package net.cn.service;

import java.util.List;

import net.cn.model.Log;
import net.cn.model.Resource;
import net.cn.model.Type;

public interface ResourceService {
	
	/**
	 * @param rid
	 * @param uid
	 * @return	�鿴�û��Ƿ�Ϊ�ʲ���ǰӵ����
	 */
	public boolean ifResourceOwner(int rid,String uid);
	
	/**
	 * @param rid
	 * @return	�鿴�ʲ��Ƿ��ڿ���״̬
	 */
	public boolean isResourceAvailable(int rid);
	
	/**
	 * @param typeId
	 * @return	��������Id��ȡ����
	 */
	public Type getType(int typeId);
	
	/**
	 * @return	��ȡ�ʲ�����б�
	 */
	public List<Type> getResourceTypes();
	
	
	/**
	 * @param type
	 * @return	��ȡ��ҵĳ���ʲ��б�
	 */
	public List<Resource> getCompanyResources(int type);
	
	/**
	 * @param type
	 * @return	��ȡ��ҵĳ������ڿ���ʲ��б�
	 */
	public List<Resource> getAvailableResources(int type);
	
	/**
	 * @param uid
	 * @return	��ȡ�����ʲ��б�
	 */
	public List<Resource> getPersonalResources(String uid);
	
	/**
	 * @param rid
	 * @return	��ȡ�ʲ�ʹ�ü�¼�б�
	 */
	public List<Log> getResourceLogs(int rid);
	

}