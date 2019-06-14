package com.bsc.qa.facets.afa.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import com.bsc.qa.facets.afa.test.BorFileValidationTest;





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
		
		Row testKeywordRow0 = sheet0.createRow(0);
		Cell testKeywordCell0 = testKeywordRow0.createCell(0);
		testKeywordCell0.setCellValue("Test Name");
		testKeywordCell0.setCellStyle(style);
		Cell testKeywordCell1 = testKeywordRow0.createCell(1);
		testKeywordCell1.setCellValue("Test Method");
		testKeywordCell1.setCellStyle(style);
		Cell testKeywordCell2 = testKeywordRow0.createCell(2);
		testKeywordCell2.setCellValue("BOR File Data");
		testKeywordCell2.setCellStyle(style);
		Cell testKeywordCell3 = testKeywordRow0.createCell(3);
		testKeywordCell3.setCellValue("Keyword Data");
		testKeywordCell3.setCellStyle(style);
		Cell testKeywordCell4 = testKeywordRow0.createCell(4);
		testKeywordCell4.setCellValue("Results");
		testKeywordCell4.setCellStyle(style);
		
		
		
		return testReport;
	}
	
	public void createExcel() throws IOException{
		new File("Output").mkdir();
		String filename = new File(System.getProperty("user.dir")) + "\\Output\\Bor-Keyword-Validation-Detailed-Report.xlsx";

		File file = new File(filename);
		OutputStream outputStream = new FileOutputStream(file);
		testReport.write(outputStream);
	}
	
	
	
	
	
}
