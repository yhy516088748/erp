package com.kjq.erp.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.kjq.erp.dao.hibernate.MenuDao;
import com.kjq.erp.model.Menu;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage("json-default")
public class DataAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Map root;
	
	@Autowired
	private MenuDao menuDao;
	
	
	public String getMenuList() throws IOException{
		root = new HashMap();
		List<Menu> menuList = menuDao.findByAll();
		
		return SUCCESS;
		
	}


	public Map getRoot() {
		return root;
	}


	public void setRoot(Map root) {
		this.root = root;
	}
}
