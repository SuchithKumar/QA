package com.bsc.qa.lacare.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;





public class ExcelUtil {
	Workbook testReport;
	Sheet sheet0;
	Row row[];
	
	public Workbook createWorkSheet(){
		
		testReport = new XSSFWorkbook();
		sheet0 = testReport.createSheet("Detailed Report");
	
		CellStyle style=testReport.createCellStyle();
		Font font=testReport.createFont();
		font.setFontHeight((short)246);
		font.setBold(true);
		
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setFont(font);
		
		Row testScenarioRow0 = sheet0.createRow(0);
		Cell testScenarioCell0 = testScenarioRow0.createCell(0);
		testScenarioCell0.setCellValue("Test Case");
		testScenarioCell0.setCellStyle(style);
		Cell testScenarioCell1 = testScenarioRow0.createCell(1);
		testScenarioCell1.setCellValue("Data");
		testScenarioCell1.setCellStyle(style);
		Cell testScenarioCell2 = testScenarioRow0.createCell(2);
		testScenarioCell2.setCellValue("Field Name");
		testScenarioCell2.setCellStyle(style);
		Cell testScenarioCell3 = testScenarioRow0.createCell(3);
		testScenarioCell3.setCellValue("Data in File");
		testScenarioCell3.setCellStyle(style);
		Cell testScenarioCell4 = testScenarioRow0.createCell(4);
		testScenarioCell4.setCellValue("Data in Database");
		testScenarioCell4.setCellStyle(style);
		Cell testScenarioCell5 = testScenarioRow0.createCell(5);
		testScenarioCell5.setCellValue("Results");
		testScenarioCell5.setCellStyle(style);
		
		
		
		return testReport;
	}
	
	public void createExcel() throws IOException{
		Date rundate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmm");
		new File("Output").mkdir();
		String filename = new File(System.getProperty("user.dir")) + "\\Output\\TestReport.xlsx";

		File file = new File(filename);
		OutputStream outputStream = new FileOutputStream(file);
		testReport.write(outputStream);
	}
	
	
	
	
	
}
