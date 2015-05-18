package com.kjq.erp.actions;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.DepartmentDao;
import com.kjq.erp.model.Department;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;

public class DepartmentAction extends ActionSupport {

	private static final long serialVersionUID = -3223819682746833772L;

	@Autowired
	private DepartmentDao departmentDao;
	

	@Action(value = "getDepartmentInfo")
	public void getDepartmentInfo() throws IOException {

		JSONObject json = new JSONObject();
		
		Response response = new Response();
		response.doResponse(json.toString());
	}
	
	@Action(value = "getDepartmentList")
	public void getDepartmentList() throws IOException {

		JSONObject json = new JSONObject();
		
//		List<Department> departmentList = departmentDao.getDepartmentParent();
//		for(Department department:departmentList ){
//			JSONObject obj = new JSONObject();
//			obj.put("id", department.getId());
//			obj.put("code", department.getCode());
//			obj.put("name", department.getName());
//			obj.put("parentID", department.getParent().getId());
//			obj.put("parentName", department.getParent().getName());
//		}
//		
//		List<Department> departmentList = departmentDao.getDepartmentParent();
//		JSONArray list = new JSONArray();
//		for(Department department:departmentList){
//			JSONObject obj = new JSONObject();
//			obj.put("id", department.getId());
//			obj.put("code", department.getCode());
//			obj.put("name", department.getName());
//			obj.put("parentID", department.getParent().getId());
//			obj.put("parentName", department.getParent().getName());
//			list.add(obj);
//		}
		JSONArray list = new JSONArray();
		
		List<Department> departmentList = departmentDao.findByAll();
		for(Department department:departmentList ){
			JSONObject obj = new JSONObject();
			obj.put("id", department.getId());
			obj.put("code", department.getCode());
			obj.put("name", department.getName());
			list.add(obj);
		}
			
		
		json.put("Status", "OK");
		json.put("List", list.toString());
		
		
		
		Response response = new Response();
		response.doResponse(json.toString());
	}

}
