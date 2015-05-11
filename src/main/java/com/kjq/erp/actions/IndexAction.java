package com.kjq.erp.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

/**
 * index ,left ,top, main frame action
 * 
 * @author York
 * 
 */
@ParentPackage("json-default")
public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = -1490269001809860571L;
	private Map root;

	@Action(value = "index", results = {@Result(name = "success", location = "index.html")})
	public String index() {
		return SUCCESS;
	}
	

	@Action(value = "getToday", results = {@Result(type = "json", params = {"root", "root"})})
	public String getToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
		
		Date today = new Date();
		root = new HashMap();
		root.put("today", sdf.format(today));
		root.put("time", sdft.format(today));
		
		return "success";
	}
	

	public Map getRoot() {
		return root;
	}


	public void setRoot(Map root) {
		this.root = root;
	}
	
}
