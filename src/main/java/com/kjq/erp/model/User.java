package com.kjq.erp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.kjq.erp.util.UUIDEntity;

/**
 * 用户
 * 
 * @author YHY
 * 
 * 
 */
@Entity
@Table(name = "tb_user")
public class User extends UUIDEntity implements Serializable {

	private static final long serialVersionUID = -4648795661707094202L;

	private String loginName;// 登陆账号
	private String password;// 登陆密码
	private String iconUrl;

	private String name;// 员工编号
	private String idNumber;// 员工姓名
	private String gender;// 性别
	private Date birthday;// 生日
	private String email;// 邮箱
	private String phone;// 联系电话
	private String address;// 住址
	private String zipCode;// 邮编
	private String nativePlace;// 籍贯

	private String bankNumber;// 银行账号
	private String bankName;// 开户行名称
	private String gjjAccount;// 公积金账号
	private String sbAccount;// 社保账号
	private String workYear;// 参加工作年

	private Boolean isMarry;// 是否已婚(1 是 0 否)
	private Boolean isInsurance;// 是否保险
	private Boolean isPOInsurance;// 是否配偶保险
	private Boolean isChild;// 是否子女
	private Boolean isChildInsurance;// 是否子女保险

	private String education;// 学历
	private String professional;// 专业

	private String workStartTime;// 标准上班时间
	private String workEndTime;// 标准下班时间
	private Date contractStartDate;// 合同起始日期
	private Date contractEndDate;// 合同失效日期
	private String remark;// 备注
	private Date createTime;

	private Set<Role> roles;// 角色
	private Set<Department> departments;// 部门
	private Set<Position> positions;// 职务

	private boolean enabled = true;
	private boolean locked;
	private Date expiryDate;
	private Date passwordExpiryDate;

	@Column(name = "loginName", length = 50)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany
	@JoinTable(name = "tb_user_department", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "department_id"))
	@JSON(serialize = false)
	public Set<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	@ManyToMany
	@JoinTable(name = "tb_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JSON(serialize = false)
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@ManyToMany
	@JoinTable(name = "tb_user_position", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "position_id"))
	@JSON(serialize = false)
	public Set<Position> getPositions() {
		return positions;
	}
	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

	@Column(columnDefinition = "INT default 1")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(columnDefinition = "INT default 0")
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
		return (getExpiryDate() == null)
				|| (getExpiryDate().compareTo(new Date()) > 0);
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return (getExpiryDate() == null)
				|| (getPasswordExpiryDate().compareTo(new Date()) > 0);
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "gender", length = 4)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column
	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "phone", length = 30)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "idNumber", length = 20)
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "zipCode", length = 10)
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "nativePlace", length = 200)
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	@Column(name = "bankNumber", length = 100)
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	@Column(name = "bankName", length = 50)
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name = "gjjAccount", length = 20)
	public String getGjjAccount() {
		return gjjAccount;
	}
	public void setGjjAccount(String gjjAccount) {
		this.gjjAccount = gjjAccount;
	}
	@Column(name = "sbAccount", length = 20)
	public String getSbAccount() {
		return sbAccount;
	}
	public void setSbAccount(String sbAccount) {
		this.sbAccount = sbAccount;
	}
	@Column(name = "education", length = 10)
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	@Column(name = "professional", length = 50)
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	@Column(name = "workStartTime", length = 5)
	public String getWorkStartTime() {
		return workStartTime;
	}
	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}
	@Column(name = "workEndTime", length = 5)
	public String getWorkEndTime() {
		return workEndTime;
	}
	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}
	@Column
	@Temporal(TemporalType.DATE)
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	@Column
	@Temporal(TemporalType.DATE)
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "workYear", length = 4)
	public String getWorkYear() {
		return workYear;
	}
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	public Boolean getIsMarry() {
		return isMarry;
	}
	public void setIsMarry(Boolean isMarry) {
		this.isMarry = isMarry;
	}
	public Boolean getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(Boolean isInsurance) {
		this.isInsurance = isInsurance;
	}
	public Boolean getIsPOInsurance() {
		return isPOInsurance;
	}
	public void setIsPOInsurance(Boolean isPOInsurance) {
		this.isPOInsurance = isPOInsurance;
	}
	public Boolean getIsChild() {
		return isChild;
	}
	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}
	public Boolean getIsChildInsurance() {
		return isChildInsurance;
	}
	public void setIsChildInsurance(Boolean isChildInsurance) {
		this.isChildInsurance = isChildInsurance;
	}
	@Column(name = "iconUrl", length = 200)
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
