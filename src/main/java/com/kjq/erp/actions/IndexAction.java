package com.kjq.erp.actions;

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

	@Action(value = "index", results = {@Result(name = "success", location = "index.html")})
	public String index() {
		return SUCCESS;
	}
	
}
