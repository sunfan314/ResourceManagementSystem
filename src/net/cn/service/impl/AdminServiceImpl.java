package net.cn.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.cn.model.Application;
import net.cn.model.PurchaseApplication;
import net.cn.model.Resource;
import net.cn.service.AdminService;

@Service("adminService")
@SuppressWarnings("all")
public class AdminServiceImpl implements AdminService {

	@Override
	public void addNewResource(Resource resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Application> getResourceCallbackApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean returnResource(int rid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Application> getApplicationApprovals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean allocateResource(Application application) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PurchaseApplication> getPurchaseApprovals() {
		// TODO Auto-generated method stub
		return null;
	}

}
