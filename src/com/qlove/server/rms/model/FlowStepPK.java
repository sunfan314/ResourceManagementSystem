package com.qlove.server.rms.model;

import java.io.Serializable;

/**
 * @author sunfan314
 *	FlowStepÖ÷¼üÀà
 */
public class FlowStepPK implements Serializable{
	
	private int flowId;
	
	private int step;

	public FlowStepPK() {
		super();
	}

	public FlowStepPK(int flowId, int step) {
		super();
		this.flowId = flowId;
		this.step = step;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flowId;
		result = prime * result + step;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowStepPK other = (FlowStepPK) obj;
		if (flowId != other.flowId)
			return false;
		if (step != other.step)
			return false;
		return true;
	}
	
	

}
