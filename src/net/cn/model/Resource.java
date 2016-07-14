package net.cn.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author sunfan314 
 * id 			资产标识 
 * name 		资产名 
 * model 		资产型号 
 * trackingNo 	资产追踪码 
 * IMEI 		移动设备标识码
 * serialNo 	资产序列号 
 * entryDate 	资产入库日期 
 * owner 		资产当前用有人（在库时用有人为warehouse）
 * status 		资产状态（0：可用；1：损坏） 
 * remark 		备注信息 
 * type 		资产类别
 */
@Entity
@Table(name = "resource")
public class Resource {
	@Id
	private int id;

	private String name;

	private String model;

	private String trackingNo;

	@Column(name = "IMEI")
	private String imei;

	private String serialNo;

	private String entryDate;

	private String owner;

	private int status;
	
	@Transient
	private String statusValue;

	private String remark;

	private int type;
	
	@Transient
	private String typeName;
	
	@OneToMany
	@JoinColumn(name="rid")
	private List<Log> logs;
	
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		String temp="";
		switch (status) {
		case 0:
			temp="资产正常";
			break;
		case 1:
			temp="资产损坏";
		default:
			break;
		}
		return temp;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

}
