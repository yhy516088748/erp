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

import com.kjq.erp.dao.hibernate.MenuDao;
import com.kjq.erp.model.Menu;
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
	

	private Map<String, Object> root;
	private String id;

	@Action(value="getMenuInfo",results = {@Result(type = "json", params = {
			"root", "root"})})
	public String getMenuInfo() throws IOException{
		root = new HashMap<String, Object>();
		
		Menu menu = menuDao.get(id);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", menu.getId());
		map.put("title", menu.getTitle());
		map.put("code", menu.getCode());
		map.put("icon", menu.getIcon());
		
		List<Map<String, String>> childMenu = new ArrayList<Map<String, String>>();
		List<Menu> childMenuList = menuDao.findChildMenus(menu);
		for(Menu cm:childMenuList){
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", cm.getId());
			m.put("title", cm.getTitle());
			m.put("code", cm.getCode());
			m.put("icon", cm.getIcon());
			childMenu.add(m);
		}
		if(childMenu.size() > 0){
			map.put("childMenu", childMenu.toString());
		}
		root.put("Status", "OK");
		root.put("Info", map);
		return SUCCESS;
	}
	
	@Action(value = "getMenuList", results = {@Result(type = "json", params = {
			"root", "root"})})
	public String getMenuList() throws IOException {
		root = new HashMap<String, Object>();
		List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();
		List<Menu> objs = menuDao.findParentMenus();
		for(Menu menu:objs){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", menu.getId());
			map.put("title", menu.getTitle());
			map.put("code", menu.getCode());
			map.put("icon", menu.getIcon());
			
			List<Map<String, String>> childMenu = new ArrayList<Map<String, String>>();
			List<Menu> childMenuList = menuDao.findChildMenus(menu);
			for(Menu cm:childMenuList){
				Map<String, String> m = new HashMap<String, String>();
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
		root.put("Status", "OK");
		root.put("List", menuList);
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
