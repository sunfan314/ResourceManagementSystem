package com.qlove.server.rms.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author sunfan314
 *	id		流程标识
 *	name	流程名
 */
@Entity
@Table(name="flow")
public class Flow {
	@Id
	private int id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(name="flowId")
	private List<FlowStep> steps;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FlowStep> getSteps() {
		return steps;
	}

	public void setSteps(List<FlowStep> steps) {
		this.steps = steps;
	}
	
	

}
