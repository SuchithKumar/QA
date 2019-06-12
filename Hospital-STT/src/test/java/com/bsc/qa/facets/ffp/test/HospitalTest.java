package com.bsc.qa.facets.ffp.test;

import static j2html.TagCreator.a;
import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.td;
import static j2html.TagCreator.tr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bsc.qa.lacare.db.DatabaseQueries;
import com.bsc.qa.lacare.ffp.ffpojo.reader.HospitalFileReader;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospBody;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospHeader;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospTrailer;
import com.bsc.qa.lacare.pojo.Connection;
import com.bsc.qa.lacare.pojo.Database;
import com.bsc.qa.lacare.pojo.PartnerRawDB;
import com.bsc.qa.lacare.pojo.User;
import com.bsc.qa.lacare.report.factory.BaseTest;
import com.bsc.qa.lacare.util.FileUtil;
import com.bsc.qa.lacare.util.HibernateUtil;
import com.relevantcodes.extentreports.LogStatus;

/*
 @author jgupta03
 @date May 17, 2019

 */
public class HospitalTest extends BaseTest implements IHookable {

	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmm");
	Connection conn = new Connection();
	SessionFactory factory;
	Session session;
	HospHeader header;
	HospTrailer trailer;
	List<HospBody> bodylist;
	List<HospBody> parsedBodyList;
	List<PartnerRawDB> rawlist;
	List<Database> databaselist;
	SimpleDateFormat sdt = new SimpleDateFormat("yyyyMMdd");
	com.bsc.qa.lacare.util.ExcelUtil excelUtil;
	Workbook testReport;
	Sheet sheet0;
	CellStyle passstyle;
	CellStyle failstyle;
	Font font;
	Set<String> failed = new HashSet<String>();
	int rowdata[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 };
	int nextRecord = 29;
	int testCaseNumber = 1;
	String[] status;
	String[] f;
	String fileName;
	static Logger logging = LogManager.getLogger(HospitalTest.class);
	@BeforeSuite
	public void getConnection() {

		conn.setUsername(User.username);
		conn.setPassword(User.password);
		conn.setUrl(User.url);
		factory = HibernateUtil.createSessionFactory(conn);
		session = factory.openSession();
		Transaction txn = session.beginTransaction();
		boolean success = session.isConnected();
		txn.commit();
		if (success)
			System.out.println("Succesfully connected to DB!!");
	}

	@AfterSuite
	public void closeConnection() {
		getStatus();
		if (session != null) {
			session.close();
			factory.close();
			System.out.println("DB Connection Succesfully closed!");
		}
	}

	@BeforeTest
	public void getData() {

		HospitalFileReader reader = new HospitalFileReader();
		DatabaseQueries queries = new DatabaseQueries();
		rawlist = queries.getRawData(session);
		System.out.println(rawlist.size());
		databaselist = queries.getDatabaseData(session, rawlist);
		//System.out.println(databaselist.size());
		header = reader.getHospitalHeaderData();
		System.out.println(header.getEff_dt());
		trailer = reader.getHospitalTrailerData();
		System.out.println(trailer.getEff_dt());
		parsedBodyList = reader.getHospitalBodyData();
		System.out.println(parsedBodyList.size());
		f = ((reader.file).split("\\\\"));
		fileName = f[f.length - 1] + "&nbsp;&nbsp;Report";

		// Excel for detailed report
		excelUtil = new com.bsc.qa.lacare.util.ExcelUtil();
		testReport = excelUtil.createWorkSheet();
		sheet0 = testReport.getSheetAt(0);
		font = testReport.createFont();
		font.setBold(true);
		passstyle = testReport.createCellStyle();
		failstyle = testReport.createCellStyle();
		passstyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		passstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		passstyle.setFont(font);
		failstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		failstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		failstyle.setFont(font);
	}

	
	//@AfterTest
	public void getStatus() {
		
		FileWriter fileWriter = null;
		File file=null,excelfile=null;
		String dest=System.getProperty("user.dir")
		+ "\\Output\\";
		file=new File(dest+"Report.html");
		excelfile=new File(dest+"TestReport.xlsx");
		Date htmlmodifieddate=new Date(file.lastModified());
		Date excelmodifieddate=new Date(excelfile.lastModified());
		String html,excel;
		html=sdt.format(htmlmodifieddate);
		excel=sdt.format(excelmodifieddate);
		
		
		if(html.compareTo(excel)==0){
			FileUtil mfe = new FileUtil();
	        List<String> files = new ArrayList<String>();
	        files.add(System.getProperty("user.dir")+"/Output/Report.html");
	        files.add(System.getProperty("user.dir")+"/Output/TestReport.xlsx");
	        mfe.zipFiles(files,sdf.format(htmlmodifieddate));
		}
		else
			FileUtil.zipHtmlFile(file, (dest+sdf.format(new Date(file.lastModified()))+".zip"));
		 if(file.exists()){
			long lastmodified=file.lastModified();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmm");
			file.renameTo(new File(dest+"Report-"+sdf.format(new Date(lastmodified))+".html"));
			//System.out.println(sdf.format(new Date(lastmodified)));
			}
		 if(excelfile.exists()){
				long lastmodified=file.lastModified();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmm");
				file.renameTo(new File(dest+"TestReport-"+sdf.format(new Date(lastmodified))+".xlsx"));
		 }
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		if (!rawlist.isEmpty() && !parsedBodyList.isEmpty()) {
			status = new String[parsedBodyList.size()];
			for (int i = 0; i < parsedBodyList.size(); i++) {
				System.out.println(parsedBodyList.get(i).getNpi());
				if (!failed.isEmpty()) {
					for (String string : failed) {
						System.out.println(string);
						if (string.equalsIgnoreCase(parsedBodyList.get(i)
								.getNpi())) {
							System.out.println(string);
							status[i] = "Fail";
							break;
						} else {
							status[i] = "Pass";
						}
					}
				}
				// Excel Detailed report
				for (int i1 = 0; i1 < 40; i1++) {
					sheet0.autoSizeColumn(i1);
				}
				try {
					excelUtil.createExcel();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// HTML Report
			
			String start = "<html> <head> <style>  table {   font-family: arial, sans-serif;   border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style></head><body><h2 align=center>"
					+ fileName
					+ "</h2><table align=center style=\"width :100%\"><tr><th ><p>Test Case No.</p></th><th><p>PROV ID</p></th><th><p>LOC ID</p></th><th><p>NET ID</p></th><th><p>DATE CREATED</p></th><th><p>SITE ID</p></th><th><p>STATUS</p></th></tr>";
			String end = "</table></body></html>";

						

				
				// inherited method from java.io.OutputStreamWriter
				try {
					fileWriter.append(start);
					for (int i = 0; i < parsedBodyList.size(); i++) {

						String data = tr(
								td("Test Case -" + (i + 1)),
								td(rawlist.get(i).getPIMS_PROV_ID().toPlainString()),
								td(rawlist.get(i).getPIMS_PROV_TIN_LOC_ID()
										.toPlainString()),
								td(rawlist.get(i).getPIMS_NET_ID().toPlainString()),
								td(rawlist.get(i).getDATE_CREATED().toString()),
								td(rawlist.get(i).getSITE_ID().toString()),
								td(status[i])).render();
						fileWriter.append(data);
					}
					fileWriter.append(end);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			for (String set : failed) {
				System.out.println(set + "             failed");
			}
		}
		else if(parsedBodyList.size()==0)
		{
			
			String report=html(head(fileName).withStyle("align=center"),body(h1("Detailed Record Data not found in the file"),a("Click here for log file....").withHref(new File("").getAbsolutePath()+"\\logs"))).render();
				try {
					fileWriter.append(report);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else if(databaselist.size()==0){
			String report=html(head(fileName).withStyle("align=center"),body(h1("NO Data present in database for the detailed record"),a("Click here for log file....").withHref(new File("").getAbsolutePath()+"\\logs"))).render();
			try {
				System.out.println("I am here");
				fileWriter.append(report);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Trans Type
	@Test(dataProvider = "getTransTypeData")
	public void testTranstype(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			softAssertion.fail("No Data");
		} else {
			Row row = sheet0.createRow(rowdata[0]);
			row.createCell(0).setCellValue("Test Case -" + testCaseNumber++);
			row.createCell(2).setCellValue("Data");
			row.createCell(2).setCellValue("TRAN_TYPE");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[0] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTransTypeData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		if (databaselist != null) {
			for (int i = 0; i < parsedBodyList.size(); i++) {
				object[i][0] = parsedBodyList.get(i).getTran_type();
				object[i][1] = databaselist.get(i).getTran_type();
				object[i][2] = parsedBodyList.get(i).getNpi();

			}
		}
		return object;
	}

	// Plan Code

	@Test(dataProvider = "getPlanCdData")
	public void testPlanCd(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[1]);
			row.createCell(2).setCellValue("PLAN_CD");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[1] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getPlanCdData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getPlan_cd();
			object[i][1] = databaselist.get(i).getPlan_cd();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Product Data
	@Test(dataProvider = "getProductData")
	public void testProduct(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[2]);
			row.createCell(2).setCellValue("PRODUCT");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[2] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();
	}

	@DataProvider
	public Object[][] getProductData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getProduct();
			object[i][1] = databaselist.get(i).getProduct();
			object[i][2] = databaselist.get(i).getNpi();
		}
		return object;
	}

	// Effective Date
	@Test(dataProvider = "getEffDtData")
	public void testEffDt(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[3]);
			row.createCell(2).setCellValue("EFF_DT");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[3] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();
	}

	@DataProvider
	public Object[][] getEffDtData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getEff_dt();
			object[i][1] = databaselist.get(i).getEff_dt();
			object[i][2] = databaselist.get(i).getNpi();
		}
		return object;
	}

	// Term Date

	@Test(dataProvider = "getTermDtData")
	public void testTermDt(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[4]);
			row.createCell(2).setCellValue("TERM_DT");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[4] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTermDtData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTerm_dt();
			object[i][1] = databaselist.get(i).getTerm_dt();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Address Line1

	@Test(dataProvider = "getAddLine1Data")
	public void testAddLine1(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[5]);
			row.createCell(2).setCellValue("ADDR_LINE1");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[5] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getAddLine1Data() {
		System.out.println("Size of body");
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getAddr_line1();
			object[i][1] = databaselist.get(i).getAddr_line1();
			object[i][2] = databaselist.get(i).getNpi();
		}
		return object;
	}

	// Address Line2

	@Test(dataProvider = "getAddLine2Data")
	public void testAddLine2(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[6]);
			row.createCell(2).setCellValue("ADDR_LINE2");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[6] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getAddLine2Data() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getAddr_line2();
			object[i][1] = databaselist.get(i).getAddr_line2();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// City

	@Test(dataProvider = "getCityData")
	public void testCity(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[7]);
			row.createCell(2).setCellValue("CITY");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[7] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getCityData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getCity();
			object[i][1] = databaselist.get(i).getCity();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// County Code

	@Test(dataProvider = "getCountyCdData")
	public void testCountyCd(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[8]);
			row.createCell(2).setCellValue("COUNTY_CD");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[8] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getCountyCdData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getCounty_cd();
			object[i][1] = databaselist.get(i).getCounty_cd();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// State

	@Test(dataProvider = "getStateCdData")
	public void testState(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[9]);
			row.createCell(2).setCellValue("STATE_CD");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[9] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getStateCdData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getState_cd();
			object[i][1] = databaselist.get(i).getState_cd();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Zip Code

	@Test(dataProvider = "getZipCdData")
	public void testZip(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[10]);
			row.createCell(2).setCellValue("ZIP_CD");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[10] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getZipCdData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getZip_cd();
			object[i][1] = databaselist.get(i).getZip_cd();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Phone

	@Test(dataProvider = "getPhoneData")
	public void testPhone(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[11]);
			row.createCell(2).setCellValue("PHONE");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[11] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getPhoneData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getPhone();
			object[i][1] = databaselist.get(i).getPhone();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Phone Extension

	@Test(dataProvider = "getPhoneExtensionData")
	public void testPhoneExtension(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[12]);
			row.createCell(2).setCellValue("PHONE_EXTN");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[12] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getPhoneExtensionData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getPhone_extn();
			object[i][1] = databaselist.get(i).getPhone_extn();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Fax

	@Test(dataProvider = "getFaxData")
	public void testFax(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[13]);
			row.createCell(2).setCellValue("FAX");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[13] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getFaxData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getFax();
			object[i][1] = databaselist.get(i).getFax();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Fed Tax ID

	@Test(dataProvider = "getFedTaxID")
	public void testFedTaxID(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[14]);
			row.createCell(2).setCellValue("FED_TAXID");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[14] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getFedTaxID() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getFed_taxid();
			object[i][1] = databaselist.get(i).getFed_taxid();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Prov_Type

	@Test(dataProvider = "getProvTypeData")
	public void testProvType(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[15]);
			row.createCell(2).setCellValue("PROV_TYPE");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[15] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getProvTypeData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getProv_type();
			object[i][1] = databaselist.get(i).getProv_type();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Site ID

	@Test(dataProvider = "getSiteId")
	public void testSiteID(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[16]);
			row.createCell(2).setCellValue("SITE_ID");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[16] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getSiteId() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getSite_id();
			object[i][1] = databaselist.get(i).getSite_id();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Hospital Name

	@Test(dataProvider = "getHospitalData")
	public void testHospital(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[17]);
			row.createCell(2).setCellValue("HOSP_NM");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[17] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getHospitalData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getHosp_nm();
			object[i][1] = databaselist.get(i).getHosp_nm();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Medicare Number

	@Test(dataProvider = "getMedicareNumData")
	public void testMedicareNum(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[18]);
			row.createCell(2).setCellValue("MEDICARE");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[18] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getMedicareNumData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getMedicare();
			object[i][1] = databaselist.get(i).getMedicare();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// NPI

	@Test(dataProvider = "getNPIData")
	public void testNPI(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[19]);
			row.createCell(2).setCellValue("NPI");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[19] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getNPIData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getNpi();
			object[i][1] = databaselist.get(i).getNpi();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Parent NPI

	@Test(dataProvider = "getParentNPIData")
	public void testParentNPI(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[20]);
			row.createCell(2).setCellValue("PARENT NPI");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[20] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getParentNPIData() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getParent_npi();
			object[i][1] = databaselist.get(i).getParent_npi();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy1

	@Test(dataProvider = "getTaxonomy1")
	public void testTaxonomy1(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[21]);
			row.createCell(2).setCellValue("TAXONOMY_CD1");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[21] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy1() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd1();
			object[i][1] = databaselist.get(i).getTaxonomy_cd1();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy2

	@Test(dataProvider = "getTaxonomy2")
	public void testTaxonomy2(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[22]);
			row.createCell(2).setCellValue("TAXONOMY_CD2");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[22] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy2() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd2();
			object[i][1] = databaselist.get(i).getTaxonomy_cd2();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy3

	@Test(dataProvider = "getTaxonomy3")
	public void testTaxonomy3(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[23]);
			row.createCell(2).setCellValue("TAXONOMY_CD3");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[23] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy3() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd3();
			object[i][1] = databaselist.get(i).getTaxonomy_cd3();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy4

	@Test(dataProvider = "getTaxonomy4")
	public void testTaxonomy4(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[24]);
			row.createCell(2).setCellValue("TAXONOMY_CD4");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[24] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy4() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd4();
			object[i][1] = databaselist.get(i).getTaxonomy_cd4();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy5

	@Test(dataProvider = "getTaxonomy5")
	public void testTaxonomy5(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[25]);
			row.createCell(2).setCellValue("TAXONOMY_CD5");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[25] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy5() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd5();
			object[i][1] = databaselist.get(i).getTaxonomy_cd5();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy6

	@Test(dataProvider = "getTaxonomy6")
	public void testTaxonomy6(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[26]);
			row.createCell(2).setCellValue("TAXONOMY_CD6");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[26] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy6() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd6();
			object[i][1] = databaselist.get(i).getTaxonomy_cd6();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	// Taxonomy 7

	@Test(dataProvider = "getTaxonomy7")
	public void testTaxonomy7(String file, String database, String npi) {
		SoftAssert softAssertion = new SoftAssert();
		if (file == null && database == null) {
			// softAssertion.assertTrue(false, "No Data Found");
			softAssertion.fail();
		} else {
			Row row = sheet0.createRow(rowdata[27]);
			row.createCell(2).setCellValue("TAXONOMY_CD7");
			row.createCell(3).setCellValue(file);
			row.createCell(4).setCellValue(database);

			if (file.equalsIgnoreCase(database)) {
				row.createCell(5).setCellValue("PASS");
				row.getCell(5).setCellStyle(passstyle);
			} else {
				row.createCell(5).setCellValue("FAIL");
				row.getCell(5).setCellStyle(failstyle);
				failed.add(npi);
			}
			rowdata[27] += nextRecord;
			softAssertion.assertEquals(file, database);
		}
		softAssertion.assertAll();

	}

	@DataProvider
	public Object[][] getTaxonomy7() {
		Object[][] object = new Object[parsedBodyList.size()][3];
		for (int i = 0; i < parsedBodyList.size(); i++) {
			object[i][0] = parsedBodyList.get(i).getTaxonomy_cd7();
			object[i][1] = databaselist.get(i).getTaxonomy_cd7();
			object[i][2] = databaselist.get(i).getNpi();

		}
		return object;
	}

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		reportInit(testResult.getTestContext().getName(), testResult.getName());
		softAssert = new SoftAssert();
		logger.log(LogStatus.INFO, "Starting test " + testResult.getName());
		callBack.runTestMethod(testResult);
		softAssert.assertAll();
	}

}
