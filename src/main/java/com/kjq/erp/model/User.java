package com.kjq.erp.model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.kjq.erp.util.DES;
import com.kjq.erp.util.PropertyUtils;
import com.kjq.erp.util.UUIDEntity;


/**
 * 用户
 * @author York
 * 
 *
 */
@Entity
@Table(name="tb_user")
public class User extends UUIDEntity implements Serializable{
	
	private static final long serialVersionUID = -4648795661707094202L;

	private String loginName;							    // 登录名
	private String password;								// 用户密码
	private String name;									// 用户姓名
	private String gender;								    // 性别 0：男 1：女
	private Date birthday;									// 出生日期	
//	private Department department;							// 部门
	private Position position;								// 职位
	private String email;									// email
	private String phone;									// 手机
	private String address;									// 地址
	
	private Date inductionDate;								//入职时间
	
	
	private Set<Group> groups;						        // 权限
	
	private boolean enabled = true;
	private boolean locked;
	private Date expiryDate;
	private Date passwordExpiryDate;

	@Column
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column
	public Date getInductionDate() {
		return inductionDate;
	}
	public void setInductionDate(Date inductionDate) {
		this.inductionDate = inductionDate;
	}
	
	@ManyToMany
	@JoinTable(
            name = "tb_user_group",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
	@JSON(serialize=false)
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	
	
	@Column
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Column
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	@Column
	public Date getPasswordExpiryDate() {
		return passwordExpiryDate;
	}
	public void setPasswordExpiryDate(Date passwordExpiryDate) {
		this.passwordExpiryDate = passwordExpiryDate;
	}
	
	@Transient
	public boolean isAccountNonExpired() {
		return (getExpiryDate() == null) || (getExpiryDate().compareTo(new Date()) > 0);
	}
	
	@Transient
	public boolean isCredentialsNonExpired() {
		return (getExpiryDate() == null) || (getPasswordExpiryDate().compareTo(new Date()) > 0);
	}
//	@ManyToOne(fetch=FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
//	public Department getDepartment() {
//		return department;
//	}
//	public void setDepartment(Department department) {
//		this.department = department;
//	}
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
