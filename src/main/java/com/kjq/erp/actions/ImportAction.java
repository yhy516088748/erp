package com.kjq.erp.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;

import com.kjq.erp.util.FileUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ImportAction extends ActionSupport {

	private static final long serialVersionUID = 4614287922939775855L;
	private transient File importFile;
	private transient String importFileFileName;
	private transient String importFileContentType;


	@Action(value = "importTest", results = {@Result(name = "success", location = "importTest.html")}, interceptorRefs = {
			@InterceptorRef(params = {"maximumSize", "20971520"}, value = "fileUpload"),
			@InterceptorRef("params"), @InterceptorRef("defaultStack")})
	public String importTest() throws IOException, ParseException {

		String storeDirectory = "kjqImportFile";

		System.out.println(importFileFileName);
		System.out.println("-------------------");

		if (importFile != null) {
			String absFilePath = FileUtils.storeFile(importFile,
					importFileFileName, storeDirectory);

			/**
			 * 仅做存储，读取 使用
			 * */
			// File imgFile = new File(absFilePath);
			// File pdfFile = new File(absFilePath);
			// File docFile = new File(absFilePath);

			System.out.println(absFilePath);

			/**
			 * txt csv 文件处理
			 * */
			File txtFile = new File(absFilePath);
			// File csvFile = new File(absFilePath);

			InputStreamReader read = new InputStreamReader(new FileInputStream(
					txtFile), "GBK");// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				System.out.println(lineTxt);
			}
			read.close();

			// FileReader fileReader = new FileReader(txtFile);
			// BufferedReader bufferedReader = new BufferedReader(fileReader);
			// String str = bufferedReader.readLine();
			// while(str != null){
			// System.out.println(str);
			// }
			/*
			 * xls 文件处理
			 */
			// File xlsFile = new File(absFilePath);
			// FileInputStream xlsFis = new FileInputStream(xlsFile);
			// HSSFWorkbook workbook = new HSSFWorkbook(xlsFis);
			// HSSFSheet sheet = workbook.getSheetAt(0);
			// int first = sheet.getFirstRowNum();
			// int last = sheet.getLastRowNum();
			// for (int i=first+1;i<=last;i++){
			// HSSFRow row = sheet.getRow(i);
			// Cell cell = row.getCell(0);
			// }
		}

		return SUCCESS;
	}


	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public String getImportFileFileName() {
		return importFileFileName;
	}

	public void setImportFileFileName(String importFileFileName) {
		this.importFileFileName = importFileFileName;
	}

	public String getImportFileContentType() {
		return importFileContentType;
	}

	public void setImportFileContentType(String importFileContentType) {
		this.importFileContentType = importFileContentType;
	}

}
