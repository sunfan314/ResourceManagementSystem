package com.qlove.server.rms.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.qlove.server.rms.dao.BaseDao;
import com.qlove.server.rms.model.Log;
import com.qlove.server.rms.model.Resource;
import com.qlove.server.rms.model.Type;
import com.qlove.server.rms.service.ResourceService;
import com.qlove.server.rms.util.ResourceTypeConfig;


@Service("resourceService")
@SuppressWarnings("all")
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
	public void addType(String typeName, int fatherType) {
		// TODO Auto-generated method stub
		Type type=new Type();
		type.setName(typeName);
		type.setFatherType(fatherType);
		baseDao.save(type);
	}

	@Override
	public List<Resource> getCompanyResources(int type) {
		// TODO Auto-generated method stub
		Type typeEntity=(Type)baseDao.get(Type.class, type);
		List<Resource> resources=baseDao.find("from Resource where type = ?", typeEntity);
		//手机充值卡添加使用时间属性
		if(type==ResourceTypeConfig.PHONE_CARD){
				for (Resource resource : resources) {
					//资产已被消耗
					if(!resource.getOwner().equals("warehouse")){
						List<Log> logs=baseDao.find("from Log where rid = ?",resource.getId());
						if(logs.size()>0){
							resource.setConsumeDate(logs.get(logs.size()-1).getStartTime());
						}
					}else{//资产在库
						resource.setConsumeDate("资产尚未使用");
					}
					
				}	
		}
		return resources;
	}
	
	

	@Override
	public List<Resource> getAvailableResources(int type) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<>();
		params.add((Type)baseDao.get(Type.class, type));
		params.add("warehouse");
		params.add(0);
		return baseDao.find("from Resource where type = ? and owner = ? and status = ?",params);
	}
	

	@Override
	public List<Resource> getPersonalResources(String uid) {
		// TODO Auto-generated method stub
		List<Resource> list=baseDao.find("from Resource where owner = ?",uid);
		return list;
	}

	@Override
	public List<Log> getResourceLogs(int rid) {
		// TODO Auto-generated method stub
		return baseDao.find("from Log where rid = ?", rid);
	}

	@Override
	public Resource getResource(int rid) {
		// TODO Auto-generated method stub
		return (Resource)baseDao.get(Resource.class, rid);
	}

	@Override
	public int getFatherType(int type) {
		// TODO Auto-generated method stub
		Type t=(Type)baseDao.get(Type.class, type);
		return t.getFatherType();
	}


}
