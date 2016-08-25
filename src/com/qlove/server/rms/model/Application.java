package com.qlove.server.rms.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.qlove.server.rms.util.TimeUtil;

/**
 * @author sunfan314
 *	id			申请标识
 *	rid			资产标识
 *	owner		资产拥有人
 *	receiver	资产接收人
 *	remark		申请备注信息
 *	time		申请时间
 *	type		申请类型（0：在库资产申请；1：个人资产转移；2：个人资产归还）
 *	flow		申请流程
 *	step		申请所处流程步骤(初始为第一步)
 *	finished	申请是否结束（0表示未结束，1表示结束）
 *	refused		申请是否被拒绝（0表示未被拒绝，1表示被拒绝）
 */	
@Entity
@Table(name="application")
public class Application {
	@Id
	private int id;
	
	private int rid;
	
	private String owner;
	
	private String receiver;
	
	private String remark;
	
	private String time;
	
	private int type;
	
	@ManyToOne(targetEntity=com.qlove.server.rms.model.Flow.class)
	@JoinColumn(name="flow")
	private Flow flow;
	
	private int step;
	
	private int finished;
	
	private int refused;
	
	@OneToMany
	@JoinColumn(name="application_id")
	private List<ApplicationApproval> approvals;
	
	@Transient
	private Resource resource;
	
	@Transient
	private String resourceName;
	
	public Application(){
		super();
		this.time=TimeUtil.getCurrentTime();
	}
	
	public Application(int rid,String owner,String receiver,String remark,int type){
		super();
		this.rid=rid;
		this.owner=owner;
		this.receiver=receiver;
		this.remark=remark;
		this.type=type;
		this.step=1;
		this.finished=0;
		this.refused=0;
		this.time=TimeUtil.getCurrentTime();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public int getRefused() {
		return refused;
	}

	public void setRefused(int refused) {
		this.refused = refused;
	}

	public List<ApplicationApproval> getApprovals() {
		return approvals;
	}

	public void setApprovals(List<ApplicationApproval> approvals) {
		this.approvals = approvals;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	

}
