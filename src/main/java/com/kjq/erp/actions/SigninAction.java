package com.kjq.erp.actions;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kjq.erp.dao.hibernate.SigninDao;
import com.kjq.erp.ext.spring.security.UserAdapter;
import com.kjq.erp.model.Signin;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage("json-default")
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
	private Map<String, Object> root;
	
	@Action(value = "getSigninTypeList", results = {@Result(type = "json", params = {"root", "root"})})
	public String getSigninTypeList() throws IOException{
		root = new HashMap<String, Object>();
		Object[] list = {"上班","下班","加班申请","加班"};
		root.put("Status", "OK");
		root.put("List", list);
		return SUCCESS;
	}

	@Action(value = "getSigninDay", results = {@Result(type = "json", params = {"root", "root"})})
	public String getSigninDay() throws IOException{
		root = new HashMap<String, Object>();
		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date today = new Date();
		List<?> list = signinDao.getSigninByDayByUser(today,ua.getUser().getId());
		
		root.put("Status", "OK");
		root.put("List", list);
		
		return SUCCESS;
	}
	
	@Action(value = "getSigninMonth", results = {@Result(type = "json", params = {"root", "root"})})
	public String getSigninMonth() throws IOException{
		root = new HashMap<String, Object>();
		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date today = new Date();
		List<?> list = signinDao.getSigninByMonthByUser(today,ua.getUser().getId());
		
		root.put("Status", "OK");
		root.put("List", list);
		
		return SUCCESS;
	}

	@Action(value = "addSigninDay", results = {@Result(type = "json", params = {"root", "root"})})
	public String addSigninDay() throws IOException{
		Date today = new Date();
		root = new HashMap<String, Object>();
		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Signin signin = new Signin();
		signin.setSiginType("上班");
		signin.setSigninTime(today);
		signin.setRemark("remarkreamrk");
		signin.setUser(ua.getUser());
		
		signinDao.save(signin);
		
		root.put("Status", "OK");
		
		return SUCCESS;
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

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}
	
}
