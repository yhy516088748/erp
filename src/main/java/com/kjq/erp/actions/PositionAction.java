package com.kjq.erp.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.PositionDao;
import com.kjq.erp.model.Position;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage("json-default")
public class PositionAction extends ActionSupport{

	/*
	 * getPositionInfo	para	id=position.id
	 * getPositionList	para	null
	 * */
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PositionDao positionDao;
	
	private Map<String, Object> root;
	private String id;
	

	@Action(value = "getPositionInfo", results = {@Result(type = "json", params = {"root", "root"})})
	public String getPositionInfo() throws IOException {
		root = new HashMap<String, Object>();
		
		Position position = positionDao.get(id);
		root.put("Status", "OK");
		root.put("Info", position);
		
		return SUCCESS;
	}
	
	@Action(value = "getPositionList", results = {@Result(type = "json", params = {"root", "root"})})
	public String getPositionList() throws IOException {
		root = new HashMap<String, Object>();
		
		List<Position> positionList = positionDao.findByAll();
		root.put("Status", "OK");
		root.put("List", positionList);
		
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
}
