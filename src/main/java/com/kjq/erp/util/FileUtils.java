package com.kjq.erp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.struts2.ServletActionContext;


public class FileUtils {
	
	public static String kjqImgStore(File attach,String rawAttachFileName,String directory){
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String uploadDir = ServletActionContext.getServletContext().getRealPath(File.separator+directory);
		File dirPath = new File(uploadDir);
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
		try {
			fis = new FileInputStream(attach);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Date date = new Date();
		Random r = new Random(date.getTime());
		
		String fileName = rawAttachFileName.substring(0,rawAttachFileName.lastIndexOf("."));
		String fileType = rawAttachFileName.substring(rawAttachFileName.lastIndexOf(".") + 1);
		
		String newFileName = uploadDir + File.separator + fileName + r.nextInt(1000000) + "." + fileType;

		try {
			fos = new FileOutputStream(newFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer = null;
		
		return newFileName;
	}
	
	public static String storeFile(File attach,String rawAttachFileName,String directory){
		Date date = new Date();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(attach);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Random r = new Random(date.getTime());
		String fileType = rawAttachFileName.substring(rawAttachFileName.lastIndexOf(".") + 1);
		String newFileNamePrefix = ServletActionContext.getRequest().getSession().getId()+ r.nextInt(10000) ;
		String newFileName = newFileNamePrefix+ "." + fileType;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateDir = formatter.format(date);
		String tmp = File.separator + dateDir + File.separator;
		String uploadDir = ServletActionContext.getServletContext().getRealPath(File.separator+directory)+ tmp;
		String viewPathPrefix = "/"+directory+"/"+dateDir+"/";
		String viewPath = viewPathPrefix + newFileName;
		String absPath = uploadDir + newFileName;
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		try {
			fos = new FileOutputStream(absPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer = null;
		
		return absPath;
	}


}
