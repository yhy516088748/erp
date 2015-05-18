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
import org.springframework.security.core.context.SecurityContextHolder;

import com.kjq.erp.dao.hibernate.MenuDao;
import com.kjq.erp.ext.spring.security.UserAdapter;
import com.kjq.erp.model.Menu;
import com.kjq.erp.model.Position;
import com.kjq.erp.model.User;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
public class MenuAction extends ActionSupport {

	private static final long serialVersionUID = 1051017262483268693L;

	/**
	 * 
	 * getMenuInfo para id=menu.id getMenuList para null
	 */

	@Autowired
	private MenuDao menuDao;

	private String id;

	@Action(value = "getMenuInfo")
	public void getMenuInfo() throws IOException {
		JSONObject json = new JSONObject();

		Menu menu = menuDao.get(id);
		JSONObject map = new JSONObject();
		map.put("id", menu.getId());
		map.put("title", menu.getTitle());
		map.put("code", menu.getCode());
		map.put("icon", menu.getIcon());
		JSONArray childMenu = new JSONArray();
		List<Menu> childMenuList = menuDao.findChildMenus(menu);
		for (Menu cm : childMenuList) {
			JSONObject m = new JSONObject();
			m.put("id", cm.getId());
			m.put("title", cm.getTitle());
			m.put("code", cm.getCode());
			m.put("icon", cm.getIcon());
			childMenu.add(m);
		}
		if (childMenu.size() > 0) {
			map.put("childMenu", childMenu.toString());
		}
		json.put("Status", "OK");
		json.put("Info", map.toString());

		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "getMenuList")
	public void getMenuList() throws IOException {

		// TODO menu 权限
		// UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext()
		// .getAuthentication().getPrincipal();
		// User user = ua.getUser();

		JSONObject json = new JSONObject();

		JSONObject navbar = new JSONObject();
		JSONArray rootList = new JSONArray();
		JSONArray childList = new JSONArray();

		List<Menu> objs = menuDao.findParentMenus();
		for (Menu obj : objs) {
			JSONObject o = new JSONObject();
			o.put("id", obj.getId());
			o.put("code", obj.getCode());
			o.put("default_code", obj.getDefault_code());
			o.put("en_title", obj.getEnTitle());
			o.put("icon", obj.getIcon());
			o.put("seq", obj.getSeq());
			o.put("tip", obj.getTip());
			o.put("title", obj.getTitle());
			o.put("url", obj.getUrl());
			rootList.add(o);

			List<Menu> childMenuList = menuDao.findChildMenus(obj);
			if (childMenuList != null && childMenuList.size() > 0) {
				JSONObject childObj = new JSONObject();
				JSONArray list = new JSONArray();
				for (Menu cm : childMenuList) {
					JSONObject cmo = new JSONObject();
					cmo.put("id", cm.getId());
					cmo.put("code", cm.getCode());
					cmo.put("default_code", cm.getDefault_code());
					cmo.put("en_title", cm.getEnTitle());
					cmo.put("icon", cm.getIcon());
					cmo.put("seq", cm.getSeq());
					cmo.put("tip", cm.getTip());
					cmo.put("title", cm.getTitle());
					cmo.put("url", cm.getUrl());
					list.add(cmo);
				}
				childObj.put(obj.getCode(), list.toString());
				childList.add(childObj);
			}
		}
		navbar.put("root", rootList.toString());
		navbar.put("child", childList.toString());

		json.put("Status", "OK");
		json.put("navbar", navbar.toString());

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
