package com.kjq.erp.actions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kjq.erp.dao.hibernate.SigninDao;
import com.kjq.erp.ext.spring.security.UserAdapter;
import com.kjq.erp.model.Signin;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;


public class SigninAction extends ActionSupport{

	
	/*
	 * getSigninDay
	 * getSigninMonth
	 * addSigninDay
	 * */

	
	private static final long serialVersionUID = -8195166186503158105L;

	@Autowired
	private SigninDao signinDao;
	
	private Date signidDate;
	
	@Action(value = "getSigninTypeList")
	public void getSigninTypeList() throws IOException{
		JSONObject json = new JSONObject();
		String[] list = {"上班","下班","加班申请","加班"};
		json.put("Status", "OK");
		json.put("List", list.toString());
		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "getSigninDay")
	public void getSigninDay() throws IOException{
		JSONObject json = new JSONObject();

		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date today = new Date();
		List<Signin> list = signinDao.getSigninByDayByUser(today,ua.getUser().getId());
		
		json.put("Status", "OK");
		json.put("List", list.toString());
		
		Response response = new Response();
		response.doResponse(json.toString());
	}
	
	@Action(value = "getSigninMonth")
	public void getSigninMonth() throws IOException{
		JSONObject json = new JSONObject();

		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date today = new Date();
		List<Signin> list = signinDao.getSigninByMonthByUser(today,ua.getUser().getId());
		
		json.put("Status", "OK");
		json.put("List", list.toString());
		
		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "addSigninDay")
	public void addSigninDay() throws IOException{
		JSONObject json = new JSONObject();

		Date today = new Date();
		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Signin signin = new Signin();
		signin.setSiginType("上班");
		signin.setSigninTime(today);
		signin.setRemark("remarkreamrk");
		signin.setUser(ua.getUser());
		signinDao.save(signin);
		
		json.put("Status", "OK");
		Response response = new Response();
		response.doResponse(json.toString());
	}
	
//	@Action(value = "updateSignin", results = {@Result(type = "json", params = {"root", "root"})})
//	public String updateSignin() throws IOException{
//		Date today = new Date();
//		root = new HashMap();
//		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		Signin signin = new Signin();
//		signin.setSigninDate(today);
//		signin.setSiginType("上班");
//		signin.setSigninTime(today);
//		signin.setRemark("remarkreamrk");
//		signin.setUser(ua.getUser());
//		
//		signinDao.save(signin);
//		
//		root.put("Status", "OK");
//		
//		return SUCCESS;
//	}
	

	public Date getSignidDate() {
		return signidDate;
	}

	public void setSignidDate(Date signidDate) {
		this.signidDate = signidDate;
	}

	
}
