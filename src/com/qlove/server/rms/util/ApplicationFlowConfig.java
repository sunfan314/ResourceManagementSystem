package com.qlove.server.rms.util;

/**
 * @author sunfan314
 * 请求流程常量
 */
public class ApplicationFlowConfig {
	
	/**
	 * 	流程：经理审核-管理员处理
	 */
	public static int MANAGER_ADMIN_FLOW=1;
	
	/**
	 * 流程：管理员直接处理
	 */
	public static int ADMIN_FLOW=2;
	
	/**
	 * 流程：资产接收者直接处理
	 */
	public static int RECEIVER_FLOW=3;

}
