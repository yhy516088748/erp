package com.kjq.erp.actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kjq.erp.dao.hibernate.PositionDao;
import com.kjq.erp.dao.hibernate.UserDao;
import com.kjq.erp.ext.spring.security.UserAdapter;
import com.kjq.erp.model.User;
import com.kjq.erp.util.DES;
import com.kjq.erp.util.FileUtils;
import com.kjq.erp.util.PropertyUtils;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
public class UserAction extends ActionSupport {
	
	private static final long serialVersionUID = -961423133882484644L;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PositionDao positionDao;

//	private Map<String, String> root;
	private String userid;
	private String name;
	private String loginName;
	private String password;
	
	private transient File data;
	private transient String dataFileName;
	private transient String dataContentType;
	
	private String imageUrl;

	/*TODO
	 * getUserInfo		para		id=user.id
	 * addUser			para		loginName,name,password(string,string,string)
	 * delUser			para		id=user.id
	 * getUserList		para		null
	 * 
	 * */
	

	@Action(value = "getUserInfo")
	public void getUserInfo() throws IOException {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setCharacterEncoding("UTF-8");
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate,"); // HTTP
//																					// 1.1
//		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setDateHeader("Expires", 0); // Proxies

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User user = userDao.get(userid);


		JSONObject json = new JSONObject();
		json.put("Status", "OK");
		JSONObject map = new JSONObject();
		map.put("loginName", user.getLoginName());
		map.put("password", user.getPassword());
		map.put("name", user.getName());
		map.put("idNumber", user.getIdNumber());
		map.put("gender", user.getGender());
		map.put("birthday", date2str(user.getBirthday()));
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
		map.put("education", user.getEducation());
		map.put("professional", user.getProfessional());
		map.put("workStartTime", user.getWorkStartTime());
		map.put("workEndTime", user.getWorkEndTime());
		map.put("contractStartDate", date2str(user.getContractStartDate()));
		map.put("contractEndDate", date2str(user.getContractEndDate()));
		map.put("remark", user.getRemark());
		json.put("Info", map.toString());

		Response response = new Response();
		response.doResponse(json.toString());
//		response.getWriter().write(json.toString());
	}
	
	@Action(value="addUser")
	public void addUser() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate,"); // HTTP
																					// 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0); // Proxies
		
		User user = new User();
		user.setLoginName(loginName);
		user.setName(name);
		user.setCreateTime(new Date());
		try {
			user.setPassword(DES.encrypt(PropertyUtils.getStringValByKey("passwordSeed"), password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userDao.save(user);
		
		JSONObject json = new JSONObject();
		json.put("Status", "OK");
		response.getWriter().write(json.toString());
	}
	@Action(value="delUser",results={@Result(type="json",params={"root","root"})})
	public void delUser() throws IOException{		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate,"); // HTTP
																					// 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0); // Proxies
		JSONObject json = new JSONObject();
		
		if(userid==null||userid.isEmpty()){
			json.put("Status", "ERROR");
			json.put("Reason", "请选择用户");
		}else{ 
			userDao.remove(userid);
			json.put("Status", "OK");
		}
		response.getWriter().write(json.toString());
	}

	@Action(value = "getUserList")
	public void getUserList() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate,"); // HTTP
																					// 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0); // Proxies
		JSONObject json = new JSONObject();
		
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<User> list = userDao.findByAll();
		JSONArray userList = new JSONArray();
		for(User user:list){
			JSONObject map = new JSONObject();
			map.put("loginName", user.getLoginName());
			map.put("password", user.getPassword());
			map.put("name", user.getName());
			map.put("idNumber", user.getIdNumber());
			map.put("gender", user.getGender());
			map.put("birthday", date2str(user.getBirthday()));
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
			map.put("ContractStartDate", date2str(user.getContractEndDate()));
			map.put("ContractEndDate", date2str(user.getContractStartDate()));
			map.put("remark", user.getRemark());
//			map.put("createTime", user.getCreateTime().toString());
			userList.add(map.toString());
		}

		
		json.put("Status", "OK");
		json.put("List", userList.toString());

		response.getWriter().write(json.toString());
	}
	

	@Action(value = "uploadKJQImg")
	public void uploadKJQImg() throws IOException {
		String fileType = dataFileName.substring(dataFileName.lastIndexOf(".") + 1);
		if("jpg".equals(fileType)||"JPG".equals(fileType)||"png".equals(fileType)||"PNG".equals(fileType)){
			User user = ((UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
			String storeDirectory = "kjqImg"+File.separator+user.getId();
			String newFileUrl = FileUtils.kjqImgStore(data,dataFileName,storeDirectory);

			user.setIconUrl(newFileUrl);
			userDao.save(user);
			
			JSONObject json = new JSONObject();
			json.put("Status", "OK");
			json.put("url", newFileUrl);
			Response response = new Response();
			response.doResponse(json.toString());
		}else{
			JSONObject json = new JSONObject();
			json.put("Status", "ERROR");
			json.put("Reason", "文件类型错误");
			Response response = new Response();
			response.doResponse(json.toString());
		}
		
		
	}
	
	public String date2str(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date == null || "".equals(date.toString())){
			return "";
		}
		return sdf.format(date);
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

}
