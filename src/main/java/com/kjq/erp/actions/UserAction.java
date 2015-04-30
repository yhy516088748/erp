package com.kjq.erp.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.GroupDao;
import com.kjq.erp.dao.hibernate.MenuDao;
import com.kjq.erp.dao.hibernate.UserDao;
import com.kjq.erp.model.Group;
import com.kjq.erp.model.Menu;
import com.kjq.erp.model.User;
import com.kjq.erp.util.DES;
import com.kjq.erp.util.PropertyUtils;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private MenuDao menuDao;

	private Map root;
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
	 * getGroupInfo		para		id=group.id
	 * addGroup			para		name
	 * delGroup			para		id=group.id
	 * getGroupList		para		null
	 * 
	 * getMenuInfo		para		id=menu.id
	 * getMenuList		para		null
	 * 
	 * 
	 * */
	
	
	@Action(value = "getUserInfo", results = {@Result(type = "json", params = {"root", "root"})})
	public String getUserInfo() throws IOException {
		root = new HashMap();

		User user = userDao.get(id);
		root.put("Status", "OK");
		root.put("Info", user);

		return SUCCESS;
	}
	@Action(value="addUser",results={@Result(type="json",params={"root","root"})})
	public String addUser() throws IOException{
		root = new HashMap();
		
		User user = new User();
		user.setLoginName(loginName);
		user.setName(name);
		try {
			user.setPassword(DES.encrypt(PropertyUtils.getStringValByKey("passwordSeed"), password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userDao.save(user);
		root.put("Status", "OK");
		return SUCCESS;
	}
	@Action(value="delUser",results={@Result(type="json",params={"root","root"})})
	public String delUser() throws IOException{
		root = new HashMap();
		
		if(id==null||id.isEmpty()){
			root.put("Status", "ERROR");
			root.put("Reason", "请选择用户");
		}else{
			userDao.remove(id);
			root.put("Status", "OK");
		}
		return SUCCESS;
	}

	@Action(value = "getUserList", results = {@Result(type = "json", params = {
			"root", "root"})})
	public String getUserList() throws IOException {

		root = new HashMap();

		List<User> userList = userDao.findByAll();

		
		root.put("Status", "OK");
		root.put("List", userList);

		return SUCCESS;
	}
	
	@Action(value="getGroupInfo",results={@Result(type="json",params={"root","root"})})
	public String getGroupInfo() throws IOException{
		root = new HashMap();
		Group group = groupDao.get(id);
		Map map = new HashMap();
		map.put("name", group.getName());
		root.put("Status", "OK");
		root.put("Info", map);
		return SUCCESS;
	}
	
	@Action(value="addGroup",results={@Result(type="json",params={"root","root"})})
	public String addGroup() throws IOException{
		root = new HashMap();
		Group group = new Group();
		group.setName(name); 
		groupDao.save(group);
		
		root.put("Status", "OK");		
		
		return SUCCESS;
	}
	
	@Action(value="delGroup",results={@Result(type="json",params={"root","root"})})
	public String delGroup() throws IOException{
		root = new HashMap();
		
		if(id==null||id.isEmpty()){
			root.put("Status", "ERROR");
			root.put("Reason", "请选择组");
		}else{
			groupDao.remove(id);
			root.put("Status", "OK");
		}
		
		return SUCCESS;
	}
	
	@Action(value="getGroupList",results={@Result(type="json",params={"root","root"})})
	public String getGroupList() throws IOException{
		root = new HashMap();
		
		List<Group> groupList = groupDao.findByAll();
		List list = new ArrayList();
		for(Group group:groupList){
			Map map = new HashMap();
			map.put("id", group.getId());
			map.put("name", group.getName());
			if(!map.isEmpty()){
				list.add(map);
			}
		}
		root.put("Status", "OK");
		root.put("List", list);
		
		return SUCCESS;
	}
	
	@Action(value="getMenuInfo",results = {@Result(type = "json", params = {
			"root", "root"})})
	public String getMenuInfo() throws IOException{
		root = new HashMap();
		
		Menu menu = menuDao.get(id);
		
		Map map = new HashMap();
		map.put("id", menu.getId());
		map.put("title", menu.getTitle());
		map.put("code", menu.getCode());
		map.put("icon", menu.getIcon());
		System.out.println(map);
		System.out.println(menu.getChildMenu().toArray());
		
//		menu.getChildMenu().;
//		Object[] oa = menu.getChildMenu().toArray();
//		System.out.println(oa.length);
//		for(Object m:oa){
//			System.out.println(((Menu)m).getTitle());
//		}
		
		root.put("Status", "OK");
//		menu.setParent(null);
//		root.put("Info", menu);
		return SUCCESS;
	}
	
	@Action(value = "getMenuList", results = {@Result(type = "json", params = {
			"root", "root"})})
	public String getMenuList() throws IOException {
		root = new HashMap();
		List<Menu> menuList = menuDao.findByAll();
		root.put("Status", "OK");
		root.put("List", menuList);
		return SUCCESS;

	}
	
	
	
	public Map getRoot() {
		return root;
	}
	public void setRoot(Map root) {
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
