package com.kjq.erp.actions;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.PositionDao;
import com.kjq.erp.model.Position;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;


public class PositionAction extends ActionSupport{

	/*
	 * getPositionInfo	para	id=position.id
	 * getPositionList	para	null
	 * */
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PositionDao positionDao;
	
	private String id;
	

	@Action(value = "getPositionInfo")
	public void getPositionInfo() throws IOException {

		JSONObject json = new JSONObject();

		Position position = positionDao.get(id);
		json.put("Status", "OK");
		json.put("Info", position.toString());
		
		Response response = new Response();
		response.doResponse(json.toString());
	}
	
	@Action(value = "getPositionList")
	public void getPositionList() throws IOException {
		JSONObject json = new JSONObject();

		JSONArray list = new JSONArray();
		List<Position> positionList = positionDao.findByAll();
		for(Position position:positionList){
			JSONObject obj = new JSONObject();
			obj.put("id", position.getId());
			obj.put("code", position.getCode());
			obj.put("name", position.getName());
			list.add(obj.toString());
		}
		json.put("Status", "OK");
		json.put("List", list.toString());
		
		Response response = new Response();
		response.doResponse(json.toString());
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
