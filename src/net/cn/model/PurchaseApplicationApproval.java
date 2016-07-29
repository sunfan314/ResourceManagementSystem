package net.cn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import net.cn.util.TimeUtil;

/**
 * @author sunfan314
 *	applicationID		申请标识
 *	step				所处步骤
 *	reviewer			审核人
 *	approve				是否通过审核
 *	time				审核时间
 *	remark				备注信息
 *	
 */
@Entity
@Table(name="purchase_application_approval")
@IdClass(net.cn.model.ApplicationApprovalPK.class)
public class PurchaseApplicationApproval {
	@Id
	@Column(name="application_id")
	private int applicationId;
	
	@Id
	private int step;
	
	private String reviewer;
	
	private int approve;
	
	private String time;
	
	private String remark;
	
	public PurchaseApplicationApproval(){
		super();
		this.time=TimeUtil.getCurrentTime();
	}
	
	public PurchaseApplicationApproval(int applicationId, int step, String reviewer, int approve, String remark) {
		super();
		this.applicationId = applicationId;
		this.step = step;
		this.reviewer = reviewer;
		this.approve = approve;
		this.remark = remark;
		this.time=TimeUtil.getCurrentTime();
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

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public int getApprove() {
		return approve;
	}

	public void setApprove(int approve) {
		this.approve = approve;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
