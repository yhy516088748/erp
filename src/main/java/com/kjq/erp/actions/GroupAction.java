package com.kjq.erp.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.GroupDao;
import com.kjq.erp.model.Group;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
public class GroupAction extends ActionSupport{


	private static final long serialVersionUID = 2000113674709174737L;

	/**
	 * 	 
	 * getGroupInfo		para		id=group.id
	 * addGroup			para		name
	 * delGroup			para		id=group.id
	 * getGroupList		para		null
	 * 
	 */

	@Autowired
	private GroupDao groupDao;

	private Map<String, Object> root;
	private String id;
	private String name;
	
	
	
	@Action(value="getGroupInfo",results={@Result(type="json",params={"root","root"})})
	public String getGroupInfo() throws IOException{
		root = new HashMap<String, Object>();
		Group group = groupDao.get(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", group.getName());
		root.put("Status", "OK");
		root.put("Info", map);
		return SUCCESS;
	}
	
	@Action(value="addGroup",results={@Result(type="json",params={"root","root"})})
	public String addGroup() throws IOException{
		root = new HashMap<String, Object>();
		Group group = new Group();
		group.setName(name); 
		groupDao.save(group);
		
		root.put("Status", "OK");		
		
		return SUCCESS;
	}
	
	@Action(value="delGroup",results={@Result(type="json",params={"root","root"})})
	public String delGroup() throws IOException{
		root = new HashMap<String, Object>();
		
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
		root = new HashMap<String, Object>();
		
		List<Group> groupList = groupDao.findByAll();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for(Group group:groupList){
			Map<String, String> map = new HashMap<String, String>();
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

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
