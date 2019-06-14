package com.bsc.qa.facets.afa.test;

import static j2html.TagCreator.td;
import static j2html.TagCreator.tr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.facets.afa.pojo.BORFile;
import com.bsc.qa.facets.afa.pojo.Connection;
import com.bsc.qa.facets.afa.pojo.ErrorStatus;
import com.bsc.qa.facets.afa.pojo.KeywordDB;
import com.bsc.qa.facets.afa.pojo.KeywordFile;
import com.bsc.qa.facets.afa.utility.ExcelUtil;
import com.bsc.qa.facets.afa.utility.HibernateUtil;
import com.bsc.qa.facets.ffp.reader.FileReader;
import com.bsc.qa.framework.base.BaseTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BorFileValidationTest extends BaseTest implements IHookable {
	
	Connection conn = new Connection();
	SessionFactory factory;
	public static Session session;
	List<BORFile> borFileList, commonClaimIdList;
	List<KeywordDB> keywordDBList;
	List<KeywordFile> keywordFileList, sortedKeywordClaimIdList;
	public static Map<String, ErrorStatus> testResults = new HashMap<String, ErrorStatus>();
	ExcelUtil excelUtil;
	Workbook testReport;
	Sheet sheet0;
	CellStyle passstyle;
	CellStyle failstyle;
	Font font;
	Set<String> failed = new HashSet<String>();
	int rowdata[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18 };
	int testCaseNumber = 1;
	int nextRecord = 18;
	public static String keywordFilePath = System.getenv("keywordFilePath");
	public static String borFilePath = System.getenv("borFilepath");
	private String bor_filename;
	private String keyword_filename;
	Logger logger1 = LoggerFactory.getLogger(BorFileValidationTest.class);
	
	@BeforeSuite
	public void getConnection() {
		logger1.info("Starting test execution...");
		AutomationStringUtilities decoder = new AutomationStringUtilities();
		String oracleUser = decoder.decryptValue(System.getenv("ORACLE_USER"));
		String oraclePassword = decoder.decryptValue(System.getenv("ORACLE_PASSWORD"));
		String oracleServer = System.getenv("ORACLE_SERVER");
		String oraclePort = System.getenv("ORACLE_PORT");
		String oracleDB = System.getenv("ORACLE_DB");
		String oracleUrl = "jdbc:oracle:thin:@" + oracleServer + ":" + oraclePort + ":" + oracleDB ;
		bor_filename =borFilePath  + FileReader.getMatchingAndLatestFile(borFilePath, "FACETS_PCT_AFAGL#*.txt").getName();
		logger1.info("Working with BOR File --- "+bor_filename);
		keyword_filename =keywordFilePath+ FileReader.getMatchingAndLatestFile(keywordFilePath, "argus_afa*.txt").getName();
		logger1.info("Working with Keyword File --- "+keyword_filename);
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		try {
			logger1.info("Connecting to Database...");
			factory = HibernateUtil.createSessionFactory(conn);
		} catch (Exception e) {
			logger1.error( "Provided invalid database environment variables, Unable to Connect to DB");
			logger1.info( "Ending test execution...");
			System.exit(0);
		}
		session = factory.openSession();
		Transaction txn = session.beginTransaction();
		boolean success = session.isConnected();
		txn.commit();
		if (success == true){
			logger1.info("Succesfully connected to DB!");
		}
	}
	
	@BeforeTest
	public void getData() throws Exception {
		borFilePath= bor_filename;
		keywordFilePath= keyword_filename;
		FileReader fileReader = new FileReader();
		try {
			borFileList = fileReader.parseBORFile();
		} catch (Exception e) {
			logger1.error( "Unable to locate BOR File / BOR File does not exist is given file location!");
			e.printStackTrace();
			if(session.isConnected()){
				logger1.info("Closing DB Connection...");
				logger1.info("DB Connection Succesfully closed!");
				session.close();
				logger1.info("Ending test exection...");
			}else{
				logger1.info("Ending test exection...");
			}
			System.exit(0);
		}
		try {
			keywordFileList = fileReader.parseKeywordFile();
		} catch (Exception e) {
			logger1.error("Unable to locate Keyword File / Keyword File does not exist is given file location!");
			if(session.isConnected()){
				logger1.info("Closing DB Connection...");
				logger1.info( "DB Connection Succesfully closed!");
				session.close();
				logger1.info( "Ending test exection...");
			}else{
				logger1.info( "Ending test exection...");
			}
			System.exit(0);
		}
		try {
			commonClaimIdList = fileReader.getCommonClaimId();
		} catch (Exception e) {
			logger1.trace("Something wrong in getClaimID method of FileReaderClass!",e.getCause());
			if(session.isConnected()){
				logger1.info( "Closing DB Connection...");
				logger1.info( "DB Connection Succesfully closed!");
				session.close();
				logger1.info( "Ending test exection...");
			}else{
				logger1.info( "Ending test exection...");
			}
			System.exit(0);
		}
		sortedKeywordClaimIdList = fileReader.getSortedKeywordClaimId();
		try {
			keywordDBList = fileReader.getKeywordDBList(commonClaimIdList);
		} catch (Exception e) {
			logger1.trace("Something wrong in getSortedKeywordClaimId method of FileReaderClass!",e.getCause());
			if(session.isConnected()){
				logger1.info( "Closing DB Connection...");
				logger1.info( "DB Connection Succesfully closed!");
				session.close();
				logger1.info( "Ending test exection...");
			}else{
				logger1.info( "Ending test exection...");
			}
			System.exit(0);
		}
		
	}


	@Test(dataProvider = "allData")
	public void testAllData(String claimId,
			Hashtable<String, BORFile> borTable,
			Hashtable<String, KeywordFile> keywordTable,
			Hashtable<String, KeywordDB> keywordDBTable) {

		SoftAssert softAssert = new SoftAssert();
		
		// Record Type
		softAssert.assertEquals(keywordTable.get(claimId).getRecord_type(), "EXCL");

		// Sequence Number
		softAssert.assertEquals(keywordTable.get(claimId).getSequence_number(), "01");

		// Claim Number
		softAssert.assertEquals(keywordTable.get(claimId).getClaim_number(), borTable.get(claimId).getClaimNumber());

		// Line Number
		softAssert.assertEquals(keywordTable.get(claimId).getLine_number(),	"01");

		// Group ID
		softAssert.assertEquals(keywordTable.get(claimId).getGroup_id(), borTable.get(claimId).getGroupNumber());

		// Subscriber ID
		softAssert.assertEquals(keywordTable.get(claimId).getSubscriber_id(), borTable.get(claimId).getSubscriberId());

		// RelationShip Code
		softAssert.assertEquals(keywordTable.get(claimId).getRelationship_code(), keywordDBTable.get(claimId).getRelationshipCode());

		// Member Suffix
		softAssert.assertEquals(keywordTable.get(claimId).getSubscriber_id(),borTable.get(claimId).getSubscriberId());

		// Plan Category
		softAssert.assertEquals(keywordTable.get(claimId).getPlan_category(),borTable.get(claimId).getProductCategory());

		// Plan ID
		softAssert.assertEquals(keywordTable.get(claimId).getPlan_id(),borTable.get(claimId).getPlanId());

		// Class ID
		softAssert.assertEquals(keywordTable.get(claimId).getClass_id(),borTable.get(claimId).getClassId());

		// Diagnosis Code Type
		softAssert.assertEquals(keywordTable.get(claimId).getDiagnosis_code_type(), "");

		// Procedure Code
		softAssert.assertEquals(keywordTable.get(claimId).getProcedure_code().trim(), borTable.get(claimId).getProcedureCode().trim());

		// Earliest Service Date
		Date sd = new Date(borTable.get(claimId).getServiceDate());
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		softAssert.assertEquals(keywordTable.get(claimId).getEarliest_service_date(), sdf.format(sd));

		// Paid Amount
		Double borAmt = Double.parseDouble(borTable.get(claimId).getClientPrice());
		MathContext mc = new MathContext(4);
		
		String str = keywordTable.get(claimId).getPaid_amount();
		String str1 = str.substring(0, str.length() - 2) + "."
				+ str.subSequence(str.length() - 2, str.length());
		Double keywordAmt = Double.parseDouble(str1);
		
		BigDecimal bor = new BigDecimal(borAmt,mc);
		BigDecimal keyword = new BigDecimal(keywordAmt,mc);
		
		softAssert.assertEquals(keyword, bor);

		// Accounting Category
		softAssert.assertEquals(keywordTable.get(claimId).getAccounting_category(), "DRUG");

		// Diagnosis Code
		softAssert.assertEquals(keywordTable.get(claimId).getDiagnosis_code(),borTable.get(claimId).getDiagnosisCode());

		softAssert.assertAll();
		

	}

	@DataProvider
	public Object[][] allData() {
		Hashtable<String, BORFile> borTable = null;
		Hashtable<String, KeywordFile> keywordTable = null;
		Hashtable<String, KeywordDB> keywordDBTable = null;
		Object[][] data = null;
		try {
			data = new Object[commonClaimIdList.size()][4];
		} catch (Exception e) {
			logger1.trace("No Data to be provided...", e.getCause());
			if(session.isConnected()){
				logger1.info( "Closing DB Connection...");
				logger1.info( "DB Connection Succesfully closed!");
				session.close();
				logger1.info( "Ending test exection...");
			}else{
				logger1.info( "Ending test exection...");
			}
			System.exit(0);
		}

		for (int i = 0; i < data.length; i++) {
			borTable = new Hashtable<String, BORFile>();
			borTable.put((String) commonClaimIdList.get(i).getClaimId(),
					commonClaimIdList.get(i));
			keywordTable = new Hashtable<String, KeywordFile>();
			keywordTable.put((String) commonClaimIdList.get(i).getClaimId(),
					sortedKeywordClaimIdList.get(i));
			keywordDBTable = new Hashtable<String, KeywordDB>();
			keywordDBTable.put((String) commonClaimIdList.get(i).getClaimId(), keywordDBList.get(i));
			data[i][0] = (String) commonClaimIdList.get(i).getClaimId();
			data[i][1] = borTable;
			data[i][2] = keywordTable;
			data[i][3] = keywordDBTable;
		}

		return data;
	}
	
	@AfterSuite
	public void closeConnection() {
		if (session != null) {
		logger1.info( "Closing DB Connection...");
		logger1.info( "DB Connection Succesfully closed!");
		session.close();
		}
		logger1.info( "Completed test execution...");
	}

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		SoftAssert softassert = new SoftAssert();
		reportInit(testResult.getTestContext().getName(), testResult.getName());
		logger.log(LogStatus.INFO, testResult.getName());
		callBack.runTestMethod(testResult);
		softassert.assertAll();

	}

}