package com.kjq.erp.actions;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import com.kjq.erp.dao.hibernate.SigninDao;
import com.kjq.erp.dao.hibernate.UserDao;
import com.kjq.erp.ext.spring.security.UserAdapter;
import com.kjq.erp.model.Signin;
import com.kjq.erp.model.User;
import com.kjq.erp.util.Response;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SigninAction extends ActionSupport implements Preparable {

	/*
	 * getSigninDay getSigninMonth addSigninDay
	 */

	private static final long serialVersionUID = -8195166186503158105L;

	@Autowired
	private SigninDao signinDao;
	@Autowired
	private UserDao userDao;

	private String signinType;
	private String remark;

	private String publicIp;
	private String localIp;
	private String macAddress;
	private String localAddress;

	@Action(value = "getSigninTypeList")
	public void getSigninTypeList() throws IOException {
		JSONObject json = new JSONObject();
		String[] list = {"上班", "下班", "加班申请", "加班"};
		json.put("Status", "OK");
		json.put("List", list.toString());
		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "getSigninDay")
	public void getSigninDay() throws IOException {
		JSONObject json = new JSONObject();

		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = ua.getUser();
		// User user = userDao.get("8a8a8a044d4b26b9014d4b3464800000");
		Date today = new Date();
		List<Signin> list = signinDao.getSigninByDayByUser(today, user.getId());

		json.put("Status", "OK");
		json.put("List", list.toString());

		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "getSigninMonth")
	public void getSigninMonth() throws IOException {
		JSONObject json = new JSONObject();

		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = ua.getUser();
		// User user = userDao.get("8a8a8a044d4b26b9014d4b3464800000");
		Date today = new Date();
		List<Signin> signinList = signinDao.getSigninByMonthByUser(today, user.getId());

		JSONArray list = new JSONArray();
		for (Signin signin : signinList) {
			JSONObject obj = new JSONObject();
			Date signinDate = signin.getSigninTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");

			obj.put("signinDate", sdf.format(signinDate));
			obj.put("signinTime", sdft.format(signinDate));
			obj.put("signinType", signin.getSigninType());
			// obj.put("remark", signin.getRemark());
			// obj.put("publicIp", signin.getPublicIp());
			// obj.put("localIp", signin.getLocalIp());
			// obj.put("macAddress", signin.getMacAddress());
			// obj.put("localAddress", signin.getLocalAddress());
			list.add(obj);
		}

		json.put("Status", "OK");
		json.put("List", list.toString());

		Response response = new Response();
		response.doResponse(json.toString());
	}

	@Action(value = "addSigninDay")
	public void addSigninDay() throws IOException {
		JSONObject json = new JSONObject();

		Date time = new Date();
		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Signin signin = new Signin();
		signin.setSigninType(signinType);

		Signin checkSignin = signinDao.findByTypeUser(signinType, ua.getUser().getId());
		if (checkSignin != null) {
			json.put("Status", "ERROR");
			Response response = new Response();
			response.doResponse(json.toString());
		} else {
			signin.setRemark(remark);
			signin.setPublicIp(publicIp);
			signin.setLocalIp(localIp);
			// signin.setMacAddress(macAddress);
			signin.setLocalAddress(localAddress);

			signin.setSigninTime(time);
			signin.setUser(ua.getUser());

			signinDao.save(signin);

			json.put("Status", "OK");
			Response response = new Response();
			response.doResponse(json.toString());
		}
	}

	@Action(value = "countSingin")
	public void countSingin() {
		// JSONArray lateList = new JSONArray();// 迟到
		// JSONArray earlyList = new JSONArray(); // 早退
		UserAdapter ua = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Signin> lateList = signinDao.countLateSinginByDay(new Date(), ua.getUser().getId());
		// List<Signin> lateList = signinDao.countLateSinginBymonth();
		for (Signin signin : lateList) {
			System.out.println(signin.getId());
			System.out.println(sdf.format(signin.getSigninTime()));
			System.out.println(signin.getSigninType());
			System.out.println(signin.getRemark());
			System.out.println(signin.getPublicIp());
			System.out.println(signin.getLocalIp());
			System.out.println(signin.getUser().getName());
		}

	}
	private static Boolean checkIsLate(Date date,String userid){
		
		return false;
	}
	private static Boolean checkIsEarly(Date date,String userid){
		
		return false;
	}
	private static int countWorkOverTime(Date date,String userid){
		
		return 1;
	}

	@Action(value = "getLocalMacAddress")
	public void getLocalMacAddress() throws IOException {
		JSONObject json = new JSONObject();
		InetAddress ia = InetAddress.getLocalHost();
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			if (str.length() == 1) {
				sb.append("0" + str);
			} else {
				sb.append(str);
			}
		}

		json.put("Status", "OK");
		json.put("macAddress", sb.toString().toUpperCase());
		Response response = new Response();
		response.doResponse(json.toString());
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public String getSigninType() {
		return signinType;
	}

	public void setSigninType(String signinType) {
		this.signinType = signinType;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
