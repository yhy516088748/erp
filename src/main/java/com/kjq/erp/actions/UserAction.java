package com.kjq.erp.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.PositionDao;
import com.kjq.erp.dao.hibernate.UserDao;
import com.kjq.erp.model.User;
import com.kjq.erp.util.DES;
import com.kjq.erp.util.PropertyUtils;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
public class UserAction extends ActionSupport {
	
	private static final long serialVersionUID = -961423133882484644L;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PositionDao positionDao;

	private Map<String, String> root;
	private String id;
	private String name;
	private String loginName;
	private String password;

	/*TODO
	 * getUserInfo		para		id=user.id
	 * addUser			para		loginName,name,password(string,string,string)
	 * delUser			para		id=user.id
	 * getUserList		para		null
	 * 
	 * */
	
	@Action(value = "getUserInfo", results = {@Result(type = "json", params = {"root", "root"})})
	public String getUserInfo() throws IOException {
		root = new HashMap<String, String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User user = userDao.get(id);

		Map<String, String> map = new HashMap<String, String>();
		map.put("loginName", user.getLoginName());
		map.put("password", user.getPassword());
		map.put("name", user.getName());
		map.put("idNumber", user.getIdNumber());
		map.put("gender", user.getGender());
		map.put("birthday", sdf.format(user.getBirthday()));
		map.put("email", user.getEmail());
		map.put("phone", user.getPhone());
		map.put("address", user.getAddress());
		map.put("zipCode", user.getZipCode());
		map.put("nativePlace", user.getNativePlace());
		map.put("gjjAccount", user.getGjjAccount());
		map.put("sbAccount", user.getSbAccount());
		map.put("workYear", user.getWorkYear());
		map.put("isMarry", user.getIsMarry().toString());
		map.put("isInsurance", user.getIsInsurance().toString());
		map.put("isPOInsurance", user.getIsPOInsurance().toString());
		map.put("isChild", user.getIsChild().toString());
		map.put("isChildInsurance", user.getIsChildInsurance().toString());
		map.put("Education", user.getEducation());
		map.put("Professional", user.getProfessional());
		map.put("workStartTime", user.getWorkStartTime());
		map.put("workEndTime", user.getWorkEndTime());
		map.put("ContractStartDate", sdf.format(user.getContractEndDate()));
		map.put("ContractEndDate", sdf.format(user.getContractStartDate()));
		map.put("remark", user.getRemark());
		root.put("Status", "OK");
		root.put("Info", map.toString());

		return SUCCESS;
	}
//	@Action(value="addUser",results={@Result(type="json",params={"root","root"})})
//	public String addUser() throws IOException{
//		root = new HashMap<String, Object>();
//		
//		User user = new User();
//		user.setLoginName(loginName);
//		user.setName(name);
//		user.setPosition(positionDao.get("1"));
//		user.setCreateTime(new Date());
//		try {
//			user.setPassword(DES.encrypt(PropertyUtils.getStringValByKey("passwordSeed"), password));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		userDao.save(user);
//		root.put("Status", "OK");
//		return SUCCESS;
//	}
//	@Action(value="delUser",results={@Result(type="json",params={"root","root"})})
//	public String delUser() throws IOException{
//		root = new HashMap<String, Object>();
//		
//		if(id==null||id.isEmpty()){
//			root.put("Status", "ERROR");
//			root.put("Reason", "请选择用户");
//		}else{ 
//			userDao.remove(id);
//			root.put("Status", "OK");
//		}
//		return SUCCESS;
//	}
//
//	@Action(value = "getUserList", results = {@Result(type = "json", params = {
//			"root", "root"})})
//	public String getUserList() throws IOException {
//
//		root = new HashMap<String, Object>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
//		List<User> list = userDao.findByAll();
//		for(User user:list){
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("loginName", user.getLoginName());
//			map.put("password", user.getPassword());
//			map.put("name", user.getName());
//			map.put("idNumber", user.getIdNumber());
//			map.put("gender", user.getGender());
//			map.put("birthday", sdf.format(user.getBirthday()));
//			map.put("email", user.getEmail());
//			map.put("phone", user.getPhone());
//			map.put("address", user.getAddress());
//			map.put("zipCode", user.getZipCode());
//			map.put("nativePlace", user.getNativePlace());
//			map.put("gjjAccount", user.getGjjAccount());
//			map.put("sbAccount", user.getSbAccount());
//			map.put("workYear", user.getWorkYear());
//			map.put("isMarry", user.getIsMarry().toString());
//			map.put("isInsurance", user.getIsInsurance().toString());
//			map.put("isPOInsurance", user.getIsPOInsurance().toString());
//			map.put("isChild", user.getIsChild().toString());
//			map.put("isChildInsurance", user.getIsChildInsurance().toString());
//			map.put("Education", user.getEducation());
//			map.put("Professional", user.getProfessional());
//			map.put("workStartTime", user.getWorkStartTime());
//			map.put("workEndTime", user.getWorkEndTime());
//			map.put("ContractStartDate", sdf.format(user.getContractEndDate()));
//			map.put("ContractEndDate", sdf.format(user.getContractStartDate()));
//			map.put("remark", user.getRemark());
////			map.put("createTime", user.getCreateTime().toString());
//			userList.add(map);
//		}
//
//		
//		root.put("Status", "OK");
//		root.put("List", userList);
//
//		return SUCCESS;
//	}
	
	
	
	
	
	public Map<String, String> getRoot() {
		return root;
	}
	public void setRoot(Map<String, String> root) {
		this.root = root;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
