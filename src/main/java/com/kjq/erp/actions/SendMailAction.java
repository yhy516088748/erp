package com.kjq.erp.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.kjq.erp.util.Mail;
import com.opensymphony.xwork2.ActionSupport;

public class SendMailAction extends ActionSupport {

	private static final InputStream InputStream = null;

	@Action(value = "mail", results = {@Result(name = "success", location = "mail.html")})
	public String mail() {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		
		
//		TaskExecutor; 任务模式
		
		return SUCCESS;
	}
	
}
