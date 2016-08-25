package com.qlove.server.rms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qlove.server.rms.dao.BaseDao;
import com.qlove.server.rms.model.Flow;
import com.qlove.server.rms.model.FlowStep;
import com.qlove.server.rms.model.Group;
import com.qlove.server.rms.service.FlowService;
import com.qlove.server.rms.util.ApplicationFlowConfig;
import com.qlove.server.rms.util.UserGroupConfig;

@Service("flowService")
@SuppressWarnings("all")
public class FlowServiceImpl implements FlowService{
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BaseDao<Group> groupDao;

	@Override
	public int getStepInFlow(int flowId, int groupId) {
		// TODO Auto-generated method stub
		Flow flow=(Flow)baseDao.get(Flow.class,flowId);
		List<FlowStep> steps=flow.getSteps();
		for (FlowStep flowStep : steps) {
			if(flowStep.getGroup().getId()==groupId){
				return flowStep.getStep();
			}
		}
		return -1;
	}


}
