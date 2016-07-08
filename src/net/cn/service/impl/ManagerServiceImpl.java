package net.cn.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.cn.model.Application;
import net.cn.model.PurchaseApplication;
import net.cn.service.ManagerService;

@Service("managerService")
@SuppressWarnings("all")
public class ManagerServiceImpl implements ManagerService{

	@Override
	public List<Application> getResourceAllocationApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseApplication> getResourcePurchaseApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agreeResourceAllocationApplication(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agreeResourcePurchaseApplication(PurchaseApplication application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refuseResourceAllocationApplication(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refuseResourcePurchaseApplication(PurchaseApplication application) {
		// TODO Auto-generated method stub
		
	}

}
