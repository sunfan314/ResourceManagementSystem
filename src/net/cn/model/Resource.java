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
 * id 			�ʲ���ʶ 
 * name 		�ʲ��� 
 * model 		�ʲ��ͺ� 
 * trackingNo 	�ʲ�׷���� 
 * IMEI 		�ƶ��豸��ʶ��
 * serialNo 	�ʲ����к� 
 * entryDate 	�ʲ�������� 
 * owner 		�ʲ���ǰ�����ˣ��ڿ�ʱ������Ϊwarehouse��
 * status 		�ʲ�״̬��0�����ã�1���𻵣� 
 * remark 		��ע��Ϣ 
 * type 		�ʲ����
 * phoneNumber	�ֻ�����(��ֵ����ֵ�����SIM������)
 * purchaser	������
 * consumer		��������Ʒʹ����
 * imsi			�ƶ��û���ʶ(SIM����ʶ)
 * pack			SIM���ײ�
 * password		�ʲ�����
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
	
	@ManyToOne(targetEntity=Type.class)
	@JoinColumn(name="type")
	private Type type;
	
	private String phoneNumber;
	
	private String purchaser;
	
	private String consumer;
	
	@Column(name="IMSI")
	private String imsi;
	
	@Column(name="package")
	private String pack;
	
	private String password;
	
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
			temp="�ʲ�����";
			break;
		case 1:
			temp="�ʲ���";
			break;
		case 2:
			temp="�ʲ�������";
			break;
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
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

}
