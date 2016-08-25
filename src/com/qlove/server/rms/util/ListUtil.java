package com.qlove.server.rms.util;

import java.util.List;

import com.qlove.server.rms.model.Application;
import com.qlove.server.rms.model.PurchaseApplication;

public class ListUtil {
	
	/**
	 * @param applictionId
	 * @param list
	 * @return	判断申请是否在列表中
	 */
	public static boolean applicationContained(int applicationId,List<Application> list){
		for (Application application : list) {
			if(application.getId()==applicationId){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param applictionId
	 * @param list
	 * @return	判断申请是否在列表中
	 */
	public static boolean purchaseApplicationContained(int applicationId,List<PurchaseApplication> list){
		for (PurchaseApplication application : list) {
			if(application.getId()==applicationId){
				return true;
			}
		}
		return false;
	}

}
