package net.cn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.cn.dao.BaseDao;
import net.cn.model.Flow;
import net.cn.model.FlowStep;
import net.cn.model.Group;
import net.cn.service.FlowService;
import net.cn.util.ApplicationFlowConfig;
import net.cn.util.UserGroupConfig;

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
		Flow flow=(Flow)baseDao.get(Flow.class, ApplicationFlowConfig.RECEIVER_FLOW);
		List<FlowStep> steps=flow.getSteps();
		int step=0;
		for(int i=0;i<steps.size();i++){
			Group group=steps.get(i).getGroup();
			if(group.equals(groupDao.get(Group.class, UserGroupConfig.USER))){
				step=i+1;
				break;
			}
		}
		return step;
	}

}
