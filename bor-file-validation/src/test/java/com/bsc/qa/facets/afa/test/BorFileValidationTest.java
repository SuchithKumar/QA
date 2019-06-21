package com.bsc.qa.facets.afa.test;

import static j2html.TagCreator.td;
import static j2html.TagCreator.tr;
import static org.testng.Assert.assertEquals;

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
import com.bsc.qa.facets.afa.dao.DatabaseUtil;
import com.bsc.qa.facets.afa.pojo.BORFile;
import com.bsc.qa.facets.afa.pojo.Connection;
import com.bsc.qa.facets.afa.pojo.DatabaseBOR;
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
	Map<String, DatabaseBOR> borDBMap;
	List<KeywordDB> keywordDBList;
	List<KeywordFile> keywordFileList, sortedKeywordClaimIdList;
	public static Map<String, ErrorStatus> testResults = new HashMap<String, ErrorStatus>();
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
		try {
			bor_filename =borFilePath  + FileReader.getMatchingAndLatestFile(borFilePath, "FACETS_PCT_AFAGL.#*.txt").getName();//
		} catch (Exception e1) {
			if(e1.getMessage().contains("No files matching")){
				logger1.error(e1.getMessage());
			}
			logger1.error("Unable to find the Latest BOR File from the given env variable borFilePath"
					+ borFilePath);
			logger1.info("Ending test execution...");
			if (session != null) {
				logger1.info("Closing DB Connection...");
				session.close();
				logger1.info("Succesfully closed DB connection!");
			}
			logger1.info("Test execution ended!");
			System.exit(0);
		}
		logger1.info("Working with BOR File - "+bor_filename);
		try {
			keyword_filename =keywordFilePath+ FileReader.getMatchingAndLatestFile(keywordFilePath, "argus_afa*.txt").getName();
		} catch (Exception e1) {
			if(e1.getMessage().contains("No files matching")){
				logger1.error(e1.getMessage());
			}
			logger1.error("Unable to find the Latest keyword File from the given env variable keywordFilePath"
					+ keywordFilePath);
			logger1.info("Ending test execution...");
			if (session != null) {
				logger1.info("Closing DB Connection...");
				session.close();
				logger1.info("Succesfully closed DB connection!");
			}
			logger1.info("Test execution ended!");
			System.exit(0);
		}
		logger1.info("Working with Keyword File --- "+keyword_filename);
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		try {
			logger1.info("Connecting to Database...");
			logger1.info("Establishing DB connection with the URL - "+conn.getUrl());
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
		DatabaseUtil util = new DatabaseUtil();
		try {
			borFileList =fileReader.parseBORFile();
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
			borDBMap = util.getDatabaseBorFileHistRecords(session, borFileList);
//			for(Map.Entry<String, DatabaseBOR> entry : borDBMap.entrySet()){
//				System.out.println(entry.getKey());
//			}
		} catch (Exception e1) {
			logger1.info("Something's wrong");
			e1.printStackTrace();
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
		softAssert.assertEquals(keywordTable.get(claimId).getRecord_type(), "EXCL","Record Type Mismatch for Claim ID :"+claimId);

		// Sequence Number
		softAssert.assertEquals(keywordTable.get(claimId).getSequence_number(), "01","Sequence Number for Claim ID :"+claimId);

		// Claim Number
		softAssert.assertEquals(keywordTable.get(claimId).getClaim_number(), borTable.get(claimId).getClaimNumber(),"Claim Number  for Claim ID :"+claimId);

		// Line Number
		softAssert.assertEquals(keywordTable.get(claimId).getLine_number(),	"01","Line Number for Claim ID :"+claimId);

		// Group ID
		softAssert.assertEquals(keywordTable.get(claimId).getGroup_id(), borTable.get(claimId).getGroupNumber(),"Group ID for Claim ID :"+claimId);

		// Subscriber ID
		softAssert.assertEquals(keywordTable.get(claimId).getSubscriber_id(), borTable.get(claimId).getSubscriberId(),"Subscriber ID for Claim ID :"+claimId);

		// RelationShip Code
		softAssert.assertEquals(keywordTable.get(claimId).getRelationship_code(), keywordDBTable.get(claimId).getRelationshipCode(),"RelationShip Code for Claim ID :"+claimId);

		// Member Suffix
		softAssert.assertEquals(keywordTable.get(claimId).getSubscriber_id(),borTable.get(claimId).getSubscriberId(),"Member Suffix for Claim ID :"+claimId);

		// Plan Category
		softAssert.assertEquals(keywordTable.get(claimId).getPlan_category(),borTable.get(claimId).getProductCategory(),"Plan Category for Claim ID :"+claimId);

		// Plan ID
		softAssert.assertEquals(keywordTable.get(claimId).getPlan_id(),borTable.get(claimId).getPlanId(),"Plan ID for Claim ID :"+claimId);

		// Class ID
		softAssert.assertEquals(keywordTable.get(claimId).getClass_id(),borTable.get(claimId).getClassId(),"Class ID for Claim ID :"+claimId);

		// Diagnosis Code Type
		softAssert.assertEquals(keywordTable.get(claimId).getDiagnosis_code_type(), "","Diagnosis Code Type for Claim ID :"+claimId);

		// Procedure Code
		softAssert.assertEquals(keywordTable.get(claimId).getProcedure_code().trim(), borTable.get(claimId).getProcedureCode().trim(),"Procedure Code for Claim ID :"+claimId);

		// Earliest Service Date
		Date sd = new Date(borTable.get(claimId).getServiceDate());
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		softAssert.assertEquals(keywordTable.get(claimId).getEarliest_service_date(), sdf.format(sd),"Earliest Service Date for Claim ID :"+claimId);

		// Paid Amount
		Double borAmt = Double.parseDouble(borTable.get(claimId).getClientPrice());
		MathContext mc = new MathContext(4);
		
		String str = keywordTable.get(claimId).getPaid_amount();
		String str1 = str.substring(0, str.length() - 2) + "."
				+ str.subSequence(str.length() - 2, str.length());
		Double keywordAmt = Double.parseDouble(str1);
		
		BigDecimal bor = new BigDecimal(borAmt,mc);
		BigDecimal keyword = new BigDecimal(keywordAmt,mc);
		
		softAssert.assertEquals(keyword, bor,"Paid Amount for Claim ID :"+claimId);

		// Accounting Category
		softAssert.assertEquals(keywordTable.get(claimId).getAccounting_category(), "DRUG","Accounting Category for Claim ID :"+claimId);

		// Diagnosis Code
		softAssert.assertEquals(keywordTable.get(claimId).getDiagnosis_code(),borTable.get(claimId).getDiagnosisCode(),"Diagnosis Code for Claim ID :"+claimId);

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
	
	@Test(dataProvider="borToDBData")
	public void testBorToDBData(String claimId,Hashtable<String,BORFile> borTable,Hashtable<String,DatabaseBOR> databaseBorTable){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");

		SoftAssert softassert = new SoftAssert();
		BigDecimal claimAmount = databaseBorTable.get(claimId).getCLM_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal clientPrice = databaseBorTable.get(claimId).getCLI_PRC_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal bscRevenueAmount = databaseBorTable.get(claimId).getBSC_RVNU_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal billedAmount = databaseBorTable.get(claimId).getBIL_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal allowedAmount = databaseBorTable.get(claimId).getALLOW_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal deductibleAmount = databaseBorTable.get(claimId).getDED_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal copayAmount = databaseBorTable.get(claimId).getCOPAY_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal coinsAmount = databaseBorTable.get(claimId).getCOINS_AMT().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borclaimAmount 	 =	 new BigDecimal(borTable.get(claimId).getClaimAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borclientPrice 	=    new BigDecimal(borTable.get(claimId).getClientPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borbscRevenueAmount=  new BigDecimal(borTable.get(claimId).getBscRevenueAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borbilledAmount		=new BigDecimal(borTable.get(claimId).getBilledAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borallowedAmount 	=new BigDecimal(borTable.get(claimId).getAllowedAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal bordeductibleAmount = new BigDecimal(borTable.get(claimId).getDeductibleAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borcopayAmount 		=new BigDecimal(borTable.get(claimId).getCopayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal borcoinsAmount 	=    new BigDecimal(borTable.get(claimId).getCoinsuranceAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);	
		
		softassert.assertEquals(borTable.get(claimId).getClaimId(),databaseBorTable.get(claimId).getFICT_CLM_ID(),"FICT_CLM_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getFileName(),databaseBorTable.get(claimId).getFIL_NM(),"FIL_NM mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getVendorName(),databaseBorTable.get(claimId).getVEND_NM(),"VEND_NM mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getGroupNumber(),databaseBorTable.get(claimId).getGRP_NBR(),"GRP_NBR mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getSubgroupId(),databaseBorTable.get(claimId).getSBGRP_ID(),"SBGRP_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getSubscriberId(),databaseBorTable.get(claimId).getSBSCR_ID(),"SBSCR_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getPersonNumber(),databaseBorTable.get(claimId).getPERS_NBR(),"PERS_NBR mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getClaimNumber(),databaseBorTable.get(claimId).getCLM_NBR(),"CLM_NBR() mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getClaimVersionNumber(),databaseBorTable.get(claimId).getCLM_VER_NBR(),"CLM_VER_NBR mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borclaimAmount),String.valueOf(claimAmount),"CLM_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borclientPrice),String.valueOf(clientPrice),"CLI_PRC_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borbscRevenueAmount),String.valueOf(bscRevenueAmount),"BSC_RVNU_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getCheckNumber(),databaseBorTable.get(claimId).getCHK_NBR(),"CHK_NBR mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getCheckDate(),dateFormat.format(databaseBorTable.get(claimId).getCHK_DT()).toUpperCase(),"CHK_DT mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getServiceDate(),dateFormat.format(databaseBorTable.get(claimId).getSVC_DT()).toUpperCase(),"SVC_DT mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getPayeeId(),databaseBorTable.get(claimId).getPAYE_ID(),"PAYE_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getPayeeName(),databaseBorTable.get(claimId).getPAYE_NM(),"PAYE_NM mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getPlanId(),databaseBorTable.get(claimId).getPLN_ID(),"PLN_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getProductId(),databaseBorTable.get(claimId).getPRDCT_ID(),"PRDCT_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getProductCategory(),databaseBorTable.get(claimId).getPRDCT_CATEG_CD(),"PRDCT_CATEG_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getClassId(),databaseBorTable.get(claimId).getCLS_ID(),"CLS_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getProductBusinessCategory(),databaseBorTable.get(claimId).getPRDCT_BUS_CATEG_CD(),"PRDCT_BUS_CATEG_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getProductValueCode(),databaseBorTable.get(claimId).getPRDCT_VAL1_CD(),"PRDCT_VAL1_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getLineOfBusinessId(),databaseBorTable.get(claimId).getLOB_ID(),"LOB_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getLegalEntity(),databaseBorTable.get(claimId).getLGL_ENTY_CD(),"LGL_ENTY_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borbilledAmount),String.valueOf(billedAmount),"BIL_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borallowedAmount),String.valueOf(allowedAmount),"ALLOW_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(bordeductibleAmount),String.valueOf(deductibleAmount),"DED_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borcoinsAmount),String.valueOf(coinsAmount),"COINS_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(String.valueOf(borcopayAmount),String.valueOf(copayAmount),"COPAY_AMT mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getDiagnosisCode(),databaseBorTable.get(claimId).getDIAG_CD(),"DIAG_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getDiagnosisCodeType(),databaseBorTable.get(claimId).getDIAG_TYP_CD(),"DIAG_TYP_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getProcedureCode(),databaseBorTable.get(claimId).getPROC_CD(),"PROC_CD mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getHcpcs_id(),databaseBorTable.get(claimId).getHCPCS_ID(),"HCPCS_ID mismatch for claimID - "+claimId );
		softassert.assertEquals(borTable.get(claimId).getClaimTransactionType(),databaseBorTable.get(claimId).getCLM_TRANS_TYP_CD(),"CLM_TRANS_TYP_CD mismatch for claimID - "+claimId );
		
		softassert.assertAll();
		
	}
	@DataProvider
	public Object[][] borToDBData() {
		Hashtable<String, BORFile> borTable = null;
		Hashtable<String, DatabaseBOR> databaseBorTable = null;
		FileReader reader = new FileReader();
		List<BORFile> borList = reader.parseBORFile();
		Object[][] data = null;
		try {
			data = new Object[borList.size()][3];
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
			borTable.put(borList.get(i).getClaimNumber().trim()+","+borList.get(i).getClaimVersionNumber().trim(),
					borList.get(i));
			databaseBorTable = new Hashtable<String, DatabaseBOR>();
			DatabaseBOR databaseBor = new DatabaseBOR();
			try {
				databaseBor = borDBMap.get(borList.get(i).getClaimNumber().trim()+","+borList.get(i).getClaimVersionNumber().trim());
				databaseBorTable.put(borList.get(i).getClaimNumber().trim()+","+borList.get(i).getClaimVersionNumber().trim(), databaseBor);
//				System.out.println("Database BOR - "+databaseBor);
			} catch (Exception e) {
				DatabaseBOR adj = new DatabaseBOR();
				adj.setCLM_NBR((String)borList.get(i).getClaimNumber().trim());
//				System.out.println("Database BOR - "+adj);
				databaseBorTable.put((String)borList.get(i).getClaimNumber(), adj);
			}
			
			data[i][0] = borList.get(i).getClaimNumber().trim()+","+borList.get(i).getClaimVersionNumber().trim();
			data[i][1] = borTable;
			data[i][2] = databaseBorTable;
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
