package net.cn.model;

import java.io.Serializable;

public class ApplicationApprovalPK implements Serializable{
	
	private int applicationId;
	
	private int step;

	public ApplicationApprovalPK() {
		super();
	}

	public ApplicationApprovalPK(int applicationId, int step) {
		super();
		this.applicationId = applicationId;
		this.step = step;
	}



	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + applicationId;
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
		ApplicationApprovalPK other = (ApplicationApprovalPK) obj;
		if (applicationId != other.applicationId)
			return false;
		if (step != other.step)
			return false;
		return true;
	}
	
	

}
