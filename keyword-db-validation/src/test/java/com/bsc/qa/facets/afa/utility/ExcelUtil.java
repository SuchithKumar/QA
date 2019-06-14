package com.bsc.qa.facets.afa.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bsc.qa.facets.afa.pojo.DatabaseKeyword;
import com.bsc.qa.facets.afa.pojo.KeywordFile;

public class ExcelUtil {
	Workbook testReport;
	Sheet sheet0;
	int rowdata[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	String fieldName[]={"Record Type","Batch Id","Claim Number","Line Number","Relationship Code","Member Suffix","Plan Category","Class ID",
			"Diagnosis Code Type","Procedure Code","Earliest Service Date","Latest Service Date","Paid Amount",
			"Record Number","Accounting Category","Diagnosis Code"
};
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
		
			
		Row testKeywordRow0 = sheet0.createRow(0);
		Cell testKeywordCell0 = testKeywordRow0.createCell(0);
		testKeywordCell0.setCellValue("Test Name");
		testKeywordCell0.setCellStyle(style);
		Cell testKeywordCell1 = testKeywordRow0.createCell(1);
		testKeywordCell1.setCellValue("Test Method");
		testKeywordCell1.setCellStyle(style);
		Cell testKeywordCell2 = testKeywordRow0.createCell(2);
		testKeywordCell2.setCellValue("Keyword File Data");
		testKeywordCell2.setCellStyle(style);
		Cell testKeywordCell3 = testKeywordRow0.createCell(3);
		testKeywordCell3.setCellValue("BLXP_EXT_PYMT Data");
		testKeywordCell3.setCellStyle(style);
		Cell testKeywordCell4 = testKeywordRow0.createCell(4);
		testKeywordCell4.setCellValue("Results");
		testKeywordCell4.setCellStyle(style);
		
		return testReport;
	}
	
	public void createExcel() throws IOException{
		File file = new File(new File("").getAbsolutePath()+"/Output/Keyword-DB-Validation-Detailed-Report.xlsx");
		OutputStream outputStream = new FileOutputStream(file);
		testReport.write(outputStream);
	}

			
	
	
}
