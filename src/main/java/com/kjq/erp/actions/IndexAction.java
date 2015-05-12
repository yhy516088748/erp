package com.kjq.erp.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.kjq.erp.model.Menu;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;

/**
 * index ,left ,top, main frame action
 * 
 * @author York
 * 
 */
public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = -1490269001809860571L;

	@Action(value = "index", results = {@Result(name = "success", location = "index.html")})
	public String index() {
		return SUCCESS;
	}
	

	@Action(value = "getToday")
	public void getToday() throws IOException{
		JSONObject json = new JSONObject();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
		
		Date today = new Date();
		json.put("today", sdf.format(today));
		json.put("time", sdft.format(today));
		
		Response response = new Response();
		response.doResponse(json.toString());
	}
}
