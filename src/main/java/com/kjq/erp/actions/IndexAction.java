package com.kjq.erp.actions;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

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

		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		JSONObject obj = new JSONObject();
		obj.put("year", cal.get(Calendar.YEAR));
		obj.put("month", cal.get(Calendar.MONTH)+1);
		obj.put("day", cal.get(Calendar.DATE));
		
		obj.put("hour", cal.get(Calendar.HOUR_OF_DAY));
		obj.put("minute", cal.get(Calendar.MINUTE));
		obj.put("second", cal.get(Calendar.SECOND));
		
		json.put("Status", "OK");
		json.put("Timer", obj.toString());
		
		Response response = new Response();
		response.doResponse(json.toString());
	}
}
