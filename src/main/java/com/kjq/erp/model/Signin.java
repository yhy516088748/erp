package com.kjq.erp.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.kjq.erp.util.UUIDEntity;



/**
 * 签到
 * @author YHY
 */
@Entity
@Table(name = "tb_signin")
public class Signin extends UUIDEntity implements Serializable {

	private static final long serialVersionUID = 3068382700225788784L;


//	private Date signinDate;
	private Date signinTime;
	private String siginType;	//1.上班，2.下班，3.加班申请，4，加班
	private String remark;

	private String publicIp;
	private String localIp;
	private String macAddress;
	
	private User user;

	
	@Column(name="signinTime")
	public Date getSigninTime() {
		return signinTime;
	}
	public void setSigninTime(Date signinTime) {
		this.signinTime = signinTime;
	}
	
	@Column(name="siginType",length=10)
	public String getSiginType() {
		return siginType;
	}
	public void setSiginType(String siginType) {
		this.siginType = siginType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="publicIp",length=15)
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	@Column(name="localIp",length=15)
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	@Column(name="macAddress",length=21)
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	

}
