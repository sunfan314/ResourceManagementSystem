package net.cn.service;

public interface FlowService {

	/**
	 * @param flowId
	 * @param groupId
	 * @return	获取对应群组下用户在流程中所处的步骤
	 */
	public int getStepInFlow(int flowId,int groupId);
}
