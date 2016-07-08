package net.cn.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cn.dao.BaseDao;
import net.cn.model.Log;
import net.cn.model.Resource;
import net.cn.model.Type;
import net.cn.service.ResourceService;


@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{
	@javax.annotation.Resource
	private BaseDao baseDao;
	
	@Override
	public boolean ifResourceOwner(int rid, String uid) {
		// TODO Auto-generated method stub
		Resource resource=(Resource)baseDao.get(Resource.class, rid);
		return resource.getOwner().equals(uid);
	}

	@Override
	public boolean isResourceAvailable(int rid) {
		// TODO Auto-generated method stub
		Resource resource=(Resource)baseDao.get(Resource.class, rid);
		return resource.getOwner().equals("warehouse");
	}
	
	@Override
	public Type getType(int typeId) {
		// TODO Auto-generated method stub
		return (Type)baseDao.get(Type.class, typeId);
	}

	@Override
	public List<Type> getResourceTypes() {
		// TODO Auto-generated method stub
		return baseDao.find("from Type");
	}

	@Override
	public List<Resource> getCompanyResources(int type) {
		// TODO Auto-generated method stub
		return baseDao.find("from Resource where type = ?", type);
	}
	
	

	@Override
	public List<Resource> getAvailableResources(int type) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add(type);
		params.add("warehouse");
		return baseDao.find("from Resource where type = ? and owner = ?",params);
	}
	

	@Override
	public List<Resource> getPersonalResources(String uid) {
		// TODO Auto-generated method stub
		List<Resource> list=baseDao.find("from Resource where owner = ?",uid);
		for (Resource resource : list) {
			resource.setTypeName(((Type)baseDao.get(Type.class, resource.getType())).getName());
		}
		return list;
	}

	@Override
	public List<Log> getResourceLogs(int rid) {
		// TODO Auto-generated method stub
		return baseDao.find("from Log where rid = ?", rid);
	}


}
