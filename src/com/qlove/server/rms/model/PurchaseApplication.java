package com.qlove.server.rms.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.qlove.server.rms.util.TimeUtil;

/**
 * @author sunfan314
 *	id				申请标识
 *	user			发起请求的用户
 *	name			物品名称
 *	number			物品数量
 *	description		物品描述
 *	time			申购时间
 *	flow			申购流程
 *	step			申请所处步骤
 *	finished		申请是否结束
 *	refused			申请是否被拒绝	
 */
@Entity
@Table(name = "purchase_application")
public class PurchaseApplication {
	@Id
	private int id;
	
	private String user;

	private String name;

	private String number;

	private String description;

	private String time;

	@ManyToOne(targetEntity = com.qlove.server.rms.model.Flow.class)
	@JoinColumn(name = "flow")
	private Flow flow;

	private int step;

	private int finished;

	private int refused;
	
	@OneToMany
	@JoinColumn(name="application_id")
	private List<PurchaseApplicationApproval> approvals;
	
	public PurchaseApplication(){
		super();
	}
	
	public PurchaseApplication(PurchaseApplication application){
		super();
		this.user=application.getUser();
		this.name=application.getName();
		this.number=application.getNumber();
		this.description=application.getDescription();
		this.time=TimeUtil.getCurrentTime();
		this.step=1;
		this.finished=0;
		this.refused=0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public List<PurchaseApplicationApproval> getApprovals() {
		return approvals;
	}

	public void setApprovals(List<PurchaseApplicationApproval> approvals) {
		this.approvals = approvals;
	}

}
