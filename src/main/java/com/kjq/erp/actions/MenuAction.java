package com.kjq.erp.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.MenuDao;
import com.kjq.erp.model.Menu;
import com.kjq.erp.model.Position;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage("json-default")
public class MenuAction extends ActionSupport{

	private static final long serialVersionUID = 1051017262483268693L;


	/**
	 * 	 
	 * getMenuInfo		para		id=menu.id
	 * getMenuList		para		null
	 */
	
	


	@Autowired
	private MenuDao menuDao;
	

	private String id;

	@Action(value="getMenuInfo")
	public void getMenuInfo() throws IOException{
		JSONObject json = new JSONObject();

		Menu menu = menuDao.get(id);
		JSONObject map = new JSONObject();
		map.put("id", menu.getId());
		map.put("title", menu.getTitle());
		map.put("code", menu.getCode());
		map.put("icon", menu.getIcon());
		JSONArray childMenu = new JSONArray();
		List<Menu> childMenuList = menuDao.findChildMenus(menu);
		for(Menu cm:childMenuList){
			JSONObject m = new JSONObject();
			m.put("id", cm.getId());
			m.put("title", cm.getTitle());
			m.put("code", cm.getCode());
			m.put("icon", cm.getIcon());
			childMenu.add(m);
		}
		if(childMenu.size() > 0){
			map.put("childMenu", childMenu.toString());
		}
		json.put("Status", "OK");
		json.put("Info", map.toString());
		
		Response response = new Response();
		response.doResponse(json.toString());
	}
	
	@Action(value = "getMenuList")
	public void getMenuList() throws IOException {
		
		JSONObject json = new JSONObject();

		JSONArray menuList = new JSONArray();
		List<Menu> objs = menuDao.findParentMenus();
		for(Menu menu:objs){
			JSONObject map = new JSONObject();
			map.put("id", menu.getId());
			map.put("title", menu.getTitle());
			map.put("code", menu.getCode());
			map.put("icon", menu.getIcon());
			
			JSONArray childMenu = new JSONArray();
			List<Menu> childMenuList = menuDao.findChildMenus(menu);
			for(Menu cm:childMenuList){
				JSONObject m = new JSONObject();
				m.put("id", cm.getId());
				m.put("title", cm.getTitle());
				m.put("code", cm.getCode());
				m.put("icon", cm.getIcon());
				childMenu.add(m);
			}
			if(childMenu.size() > 0){
				map.put("childMenu", childMenu.toString());
			}
			menuList.add(map);
		}
		json.put("Status", "OK");
		json.put("List", menuList.toString());
		
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
