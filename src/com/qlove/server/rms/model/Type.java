package com.qlove.server.rms.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sunfan314
 *	id			资产类型标识
 *	name		资产类型名
 *	fatherType	资产父类目
 */
@Entity
@Table(name="type")
public class Type {
	@Id
	private int id;
	
	private String name;
	
	private int fatherType;

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

	public int getFatherType() {
		return fatherType;
	}

	public void setFatherType(int fatherType) {
		this.fatherType = fatherType;
	}
	
	

}
