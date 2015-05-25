package com.kjq.erp.actions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kjq.erp.dao.hibernate.UserDao;
import com.kjq.erp.ext.spring.security.UserAdapter;
import com.kjq.erp.model.User;
import com.kjq.erp.util.DES;
import com.kjq.erp.util.DataFormat;
import com.kjq.erp.util.FileUtils;
import com.kjq.erp.util.PropertyUtils;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = -961423133882484644L;
	@Autowired
	private UserDao userDao;

	private User user;

	private String userid;
	private String name;
	private String loginName;
	private String password;
	private String idNumber;
	private String gender;
	private String birthday;
	private String email;
	private String phone;
	private String address;
	private String zipCode;
	private String nativePlace;
	private String bankName;
	private String bankNumber;
	private String gjjAccount;
	private String sbAccount;
	private String workYear;
	private String isMarry;
	private String isInsurance;
	private String isPOInsurance;
	private String isChild;
	private String isChildInsurance;
	private String education;
	private String professional;
	private String workStartTime;
	private String workEndTime;
	private String contractStartDate;
	private String contractEndDate;
	private String remark;
	private String iconUrl;
	private String departments;
	private String positions;
	// private String roles;

	private transient File data;
	private transient String dataFileName;
	private transient String dataContentType;

	private String imageUrl;

	/*
	 * getUserInfo para id=user.id addUser para
	 * loginName,name,password(string,string,string) delUser para id=user.id
	 * getUserList para null
	 */

	@Action(value = "getUserInfo")
	public void getUserInfo() throws IOException {
		User user = userDao.get(userid);

		JSONObject json = new JSONObject();

		if (user != null) {
			json.put("Status", "OK");
			JSONObject map = new JSONObject();

			map.put("userid", user.getId());
			map.put("loginName", user.getLoginName());
			map.put("password", user.getPassword());
			map.put("name", user.getName());
			map.put("idNumber", user.getIdNumber());
			map.put("gender", user.getGender());
			map.put("birthday", DataFormat.date2str(user.getBirthday()));
			map.put("email", user.getEmail());
			map.put("phone", user.getPhone());
			map.put("address", user.getAddress());
			map.put("zipCode", user.getZipCode());
			map.put("nativePlace", user.getNativePlace());
			map.put("bankName", user.getBankName());
			map.put("bankNumber", user.getBankNumber());
			map.put("gjjAccount", user.getGjjAccount());
			map.put("sbAccount", user.getSbAccount());
			map.put("workYear", user.getWorkYear());
			map.put("isMarry", user.getIsMarry() == null ? "" : user
					.getIsMarry().toString());
			map.put("isInsurance", user.getIsInsurance() == null ? "" : user
					.getIsInsurance().toString());
			map.put("isPOInsurance", user.getIsPOInsurance() == null
					? ""
					: user.getIsPOInsurance().toString());
			map.put("isChild", user.getIsChild() == null ? "" : user
					.getIsChild().toString());
			map.put("isChildInsurance", user.getIsChildInsurance() == null
					? ""
					: user.getIsChildInsurance().toString());
			map.put("education", user.getEducation());
			map.put("professional", user.getProfessional());
			map.put("workStartTime", user.getWorkStartTime());
			map.put("workEndTime", user.getWorkEndTime());
			map.put("contractStartDate",
					DataFormat.date2str(user.getContractStartDate()));
			map.put("contractEndDate",
					DataFormat.date2str(user.getContractEndDate()));
			map.put("remark", user.getRemark());
			map.put("iconUrl", user.getIconUrl());

			json.put("Info", map.toString());
		} else {
			json.put("Status", "ERROR");
			json.put("Reason", "未找到该用户");
		}

		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "addUser")
	public void addUser() throws Exception {
		Date now = new Date();

		User user = null;
		if (userid == null || userid.isEmpty()) {
			user = new User();
			user.setEnabled(true);
			user.setLocked(false);
			user.setExpiryDate(null);
			user.setPasswordExpiryDate(null);
		} else {
			user = userDao.get(userid);
		}
		// user.setLoginName(loginName);
		// user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		user.setGender(gender);
		user.setBirthday(DataFormat.str2date(birthday));
		user.setPhone(phone);
		user.setAddress(address);
		user.setIdNumber(idNumber);
		user.setZipCode(zipCode);
		user.setNativePlace(nativePlace);
		user.setBankName(bankName);
		user.setBankNumber(bankNumber);
		user.setGjjAccount(gjjAccount);
		user.setSbAccount(sbAccount);
		user.setEducation(education);
		user.setProfessional(professional);
		user.setWorkStartTime(workStartTime);
		user.setWorkEndTime(workEndTime);
		user.setContractStartDate(DataFormat.str2date(contractStartDate));
		user.setContractEndDate(DataFormat.str2date(contractEndDate));
		user.setRemark(remark);
		user.setCreateTime(now);
		user.setWorkYear(workYear);
		user.setIsMarry(Boolean.valueOf(isMarry));
		user.setIsInsurance(Boolean.valueOf(isInsurance));
		user.setIsPOInsurance(Boolean.valueOf(isPOInsurance));
		user.setIsChild(Boolean.valueOf(isChild));
		user.setIsChildInsurance(Boolean.valueOf(isChildInsurance));
		user.setIconUrl(iconUrl);

		// try {
		// user.setPassword(DES.encrypt(
		// PropertyUtils.getStringValByKey("passwordSeed"),
		// user.getPassword()));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// // TODO 部门，职务，权限 设定
		// System.out.println("--------------department--------------");
		// System.out.println(departments);
		// System.out.println("--------------position--------------");
		// System.out.println(positions);
		// System.out.println("--------------role--------------");
		// System.out.println(roles);

		User newUser = userDao.save(user);

		JSONObject json = new JSONObject();
		json.put("Status", "OK");

		Response response = new Response();
		response.doResponse(json.toString());
	}
	@Action(value = "delUser", results = {@Result(type = "json", params = {
			"root", "root"})})
	public void delUser() throws IOException {
		JSONObject json = new JSONObject();

		if (userid == null || userid.isEmpty()) {
			json.put("Status", "ERROR");
			json.put("Reason", "请选择用户");
		} else {
			userDao.remove(userid);
			json.put("Status", "OK");
		}

		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "getUserList")
	public void getUserList() throws IOException {

		JSONObject json = new JSONObject();
		List<User> list = userDao.findByAll();
		JSONArray userList = new JSONArray();
		for (User user : list) {
			JSONObject map = new JSONObject();
			map.put("userid", user.getId());
			map.put("loginName", user.getLoginName());
			map.put("password", user.getPassword());
			map.put("name", user.getName());
			map.put("idNumber", user.getIdNumber());
			map.put("gender", user.getGender());
			map.put("birthday", DataFormat.date2str(user.getBirthday()));
			map.put("email", user.getEmail());
			map.put("phone", user.getPhone());
			map.put("address", user.getAddress());
			map.put("zipCode", user.getZipCode());
			map.put("nativePlace", user.getNativePlace());
			map.put("bankName", user.getBankName());
			map.put("bankNumber", user.getBankNumber());
			map.put("gjjAccount", user.getGjjAccount());
			map.put("sbAccount", user.getSbAccount());
			map.put("workYear", user.getWorkYear());
			map.put("isMarry",
					user.getIsMarry() == null ? null : user.getIsMarry().toString());
			map.put("isInsurance",
					user.getIsInsurance() == null ? null : user
							.getIsInsurance().toString());
			map.put("isPOInsurance", user.getIsPOInsurance() == null
					? null
					: user.getIsPOInsurance().toString());
			map.put("isChild",
					user.getIsChild() == null ? null : user.getIsChild().toString());
			map.put("isChildInsurance", user.getIsChildInsurance() == null
					? null
					: user.getIsChildInsurance().toString());
			map.put("Education", user.getEducation());
			map.put("Professional", user.getProfessional());
			map.put("workStartTime", user.getWorkStartTime());
			map.put("workEndTime", user.getWorkEndTime());
			map.put("ContractStartDate",
					DataFormat.date2str((user.getContractEndDate())));
			map.put("ContractEndDate",
					DataFormat.date2str(user.getContractStartDate()));
			map.put("remark", user.getRemark());
			map.put("iconUrl", user.getIconUrl());
			// map.put("createTime", user.getCreateTime().toString());
			userList.add(map.toString());
		}

		json.put("Status", "OK");
		json.put("List", userList.toString());

		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "uploadKJQImg")
	public void uploadKJQImg() throws IOException {
		JSONObject json = new JSONObject();
		String fileType = dataFileName
				.substring(dataFileName.lastIndexOf(".") + 1);
		if ("jpg".equals(fileType) || "JPG".equals(fileType)
				|| "png".equals(fileType) || "PNG".equals(fileType)) {
			User user = ((UserAdapter) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUser();
			String storeDirectory = "kjqImg" + File.separator + user.getId();
			String newFileUrl = FileUtils.kjqImgStore(data, dataFileName,
					storeDirectory);

			user.setIconUrl(newFileUrl);
			userDao.save(user);

			json.put("Status", "OK");
			json.put("url", newFileUrl);
		} else {
			json.put("Status", "ERROR");
			json.put("Reason", "文件类型错误");
		}

		Response response = new Response();
		response.doResponse(json.toString());
	}

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public File getData() {
		return data;
	}

	public void setData(File data) {
		this.data = data;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public void setDataContentType(String dataContentType) {
		this.dataContentType = dataContentType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getGjjAccount() {
		return gjjAccount;
	}

	public void setGjjAccount(String gjjAccount) {
		this.gjjAccount = gjjAccount;
	}

	public String getSbAccount() {
		return sbAccount;
	}

	public void setSbAccount(String sbAccount) {
		this.sbAccount = sbAccount;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(String isMarry) {
		this.isMarry = isMarry;
	}

	public String getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}

	public String getIsPOInsurance() {
		return isPOInsurance;
	}

	public void setIsPOInsurance(String isPOInsurance) {
		this.isPOInsurance = isPOInsurance;
	}

	public String getIsChild() {
		return isChild;
	}

	public void setIsChild(String isChild) {
		this.isChild = isChild;
	}

	public String getIsChildInsurance() {
		return isChildInsurance;
	}

	public void setIsChildInsurance(String isChildInsurance) {
		this.isChildInsurance = isChildInsurance;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}

	public String getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// public String getRoles() {
	// return roles;
	// }
	//
	// public void setRoles(String roles) {
	// this.roles = roles;
	// }

}
