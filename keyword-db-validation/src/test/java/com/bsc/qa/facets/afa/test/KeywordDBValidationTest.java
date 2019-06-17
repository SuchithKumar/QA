package com.bsc.qa.facets.afa.test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.facets.afa.pojo.DatabaseKeyword;
import com.bsc.qa.facets.afa.pojo.Demographic;
import com.bsc.qa.facets.afa.pojo.KeywordFile;
import com.bsc.qa.facets.afa.utility.Connection;
import com.bsc.qa.facets.afa.utility.HibernateUtil;
import com.bsc.qa.facets.ffp.reader.FileReader;

public class KeywordDBValidationTest {

	Connection conn = new Connection();
	SessionFactory factory;
	Session session;
	List<KeywordFile> keywordlist;
	List<DatabaseKeyword> databaselist;
	List<Demographic> demographiclist;
	public static String keyword_filename;
	String keywordFilePath = System.getenv("keywordFilePath");
	public static Map<String, String> testResults = new HashMap<String, String>();
	Set<Boolean> resultSet = new HashSet<Boolean>();
	Logger logger1 = LoggerFactory.getLogger(KeywordDBValidationTest.class);

	@BeforeSuite
	public void getConnection() {
		AutomationStringUtilities decoder = new AutomationStringUtilities();
		String oracleUser = decoder.decryptValue(System.getenv("ORACLE_USER"));
		String oraclePassword = decoder.decryptValue(System
				.getenv("ORACLE_PASSWORD"));
		String oracleServer = System.getenv("ORACLE_SERVER");
		String oraclePort = System.getenv("ORACLE_PORT");
		String oracleDB = System.getenv("ORACLE_DB");
		String oracleUrl = "jdbc:oracle:thin:@" + oracleServer + ":"
				+ oraclePort + ":" + oracleDB;
		try {
			keyword_filename = keywordFilePath
					+ FileReader.getMatchingAndLatestFile(keywordFilePath,
							"argus_afa*.txt").getName();
		} catch (Exception e) {
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
		logger1.info("Working with Keyword File --- " + keyword_filename);
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		try {
			logger1.info("Connecting to Database...");
			logger1.info("Establishing DB connection with the URL - "+conn.getUrl());
			factory = HibernateUtil.createSessionFactory(conn);
		} catch (Exception e) {
			logger1.error("Provided invalid database environment variables, Unable to Connect to DB");
			if (session != null) {
				logger1.info("Closing DB Connection...");
				session.close();
				logger1.info("Succesfully closed DB connection!");
			}
			logger1.info("Ending test execution...");
			logger1.info("Test execution ended!");
			System.exit(0);
		}
		session = factory.openSession();
		Transaction txn = session.beginTransaction();
		boolean success = session.isConnected();
		txn.commit();
		if (success == true) {
			logger1.info("Succesfully connected to DB!");
		}

	}

	@BeforeTest
	public void setUpData() {
		FileReader dataUtil = new FileReader();
		try {
			keywordlist = dataUtil.parseFile();
		} catch (Exception e) {
			logger1.error("Error while parsing the keyword file - "
					+ e.getMessage());
			if (session != null) {
				logger1.info("Closing DB Connection...");
				session.close();
				logger1.info("Succesfully closed DB connection!");
			}
			logger1.info("Ending test execution...");
			logger1.info("Test execution ended!");
			System.exit(0);
		}

		try {
			databaselist = dataUtil.getDatabaseKeywords(session, keywordlist);
		} catch (Exception e) {
			logger1.error("Error while getting database records - "
					+ e.getMessage());
			if (session != null) {
				logger1.info("Closing DB Connection...");
				session.close();
				logger1.info("Succesfully closed DB connection!");
			}
			logger1.info("Ending test execution...");
			logger1.info("Test execution ended!");
			System.exit(0);
		}
		try {
			demographiclist = dataUtil.getDemographic(keywordlist, session);
		} catch (Exception e) {
			logger1.error("Error while getting demographic records - "
					+ e.getCause());
			if (session != null) {
				logger1.info("Closing DB Connection...");
				session.close();
				logger1.info("Succesfully closed DB connection!");
			}
			logger1.info("Ending test execution...");
			logger1.info("Test execution ended!");
			System.exit(0);
		}
		
		FileReader reader = new FileReader();
		try {
			reader.getqueries();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "allData")
	public void testAllData(String claimID,
			Hashtable<String, KeywordFile> keywordTable,
			Hashtable<String, DatabaseKeyword> databaseTable,
			Hashtable<String, Demographic> demographicTable) {
		SoftAssert softAssert = new SoftAssert();

		// Record Type
		softAssert.assertEquals(keywordTable.get(claimID).getRecord_type(),
				"EXCL", "Record Type for Claim ID : " + claimID);

		// Batch ID
		String keywordBatchID = keywordTable.get(claimID).getRun_date()
				+ keywordTable.get(claimID).getBatch_name()
				+ keywordTable.get(claimID).getSequence_number().trim();
		String databaseBatchID = databaseTable.get(claimID).getBLXP_BATCH_ID()
				.trim();
		softAssert.assertEquals(keywordBatchID, databaseBatchID,
				"Batch ID for Claim ID : " + claimID);

		// Claim ID
		softAssert.assertEquals(keywordTable.get(claimID).getClaim_number(),
				databaseTable.get(claimID).getBLXP_CLCL_ID(),
				"Claim ID for Claim ID : " + claimID);

		// Line Number
		softAssert.assertEquals(keywordTable.get(claimID).getLine_number(),
				"01", "Line Number for Claim ID : " + claimID);

		// Relationship Code
		softAssert.assertEquals(keywordTable.get(claimID)
				.getRelationship_code(), demographicTable.get(claimID)
				.getMEME_REL(), "Relationship Code for Claim ID : " + claimID);

		// Member Suffix
		softAssert.assertEquals(keywordTable.get(claimID).getMember_suffix(),
				"0" + demographicTable.get(claimID).getMEME_SFX(),
				"Member Suffix for Claim ID : " + claimID);

		// Plan Category
		softAssert.assertEquals(keywordTable.get(claimID).getPlan_category(),
				databaseTable.get(claimID).getCSPD_CAT(),
				"Plan Category for Claim ID : " + claimID);

		// Class ID
		softAssert.assertEquals(keywordTable.get(claimID).getClass_id(),
				databaseTable.get(claimID).getCSCS_ID_NVL(),
				"Class ID for Claim ID : " + claimID);

		// Procedure Code
		// softAssert.assertEquals(keywordTable.get(claimID).getProcedure_code()
		// , "99199","Procedure Code for Claim ID : " + claimID);

		// Earliest Service Date
		SimpleDateFormat formatEarliestServiceDate = new SimpleDateFormat(
				"MMddYYYY");
		softAssert.assertEquals(keywordTable.get(claimID)
				.getEarliest_service_date(), formatEarliestServiceDate
				.format(databaseTable.get(claimID).getBLXP_FROM_DT()),
				"Earliest Service Date for Claim ID : " + claimID);

		// Latest Service Date
		SimpleDateFormat formatLatestServiceDate = new SimpleDateFormat(
				"MMddYYYY");
		softAssert.assertEquals(keywordTable.get(claimID)
				.getLatest_service_date(), formatLatestServiceDate
				.format(databaseTable.get(claimID).getBLXP_TO_DT()),
				"Latest Service Date for Claim ID : " + claimID);

		// Paid Amount
		String str = keywordTable.get(claimID).getPaid_amount();
		String str1 = str.substring(0, str.length() - 2) + "."
				+ str.subSequence(str.length() - 2, str.length());
		Double keywordPaidAmount = Double.parseDouble(str1);
		Double databasePaidAmount = databaseTable.get(claimID)
				.getBLXP_PAID_AMT();
		softAssert.assertEquals(keywordPaidAmount, databasePaidAmount,
				"Paid Amount for Claim ID : " + claimID);

		// Accounting Category
		softAssert.assertEquals(keywordTable.get(claimID)
				.getAccounting_category(), "DRUG",
				"Accounting Category for Claim ID : " + claimID);

		// Diagnosis Code
		softAssert.assertEquals(keywordTable.get(claimID).getDiagnosis_code(),
				"9999", "Diagnosis Code for Claim ID : " + claimID);

		// GRGR CK
		softAssert.assertEquals(
				databaseTable.get(claimID).getGRGR_CK(),
				Long.parseLong(demographicTable.get(claimID).getGRGR_CK()
						.toPlainString().trim()), "GRGR CK for Claim ID : "
						+ claimID);

		// SBSB CK
		softAssert.assertEquals(
				databaseTable.get(claimID).getSBSB_CK(),
				Long.parseLong(demographicTable.get(claimID).getSBSB_CK()
						.toPlainString().trim()), "SBSB CK for Claim ID : "
						+ claimID);

		// MEME CK
		softAssert.assertEquals(
				databaseTable.get(claimID).getMEME_CK(),
				Long.parseLong(demographicTable.get(claimID).getMEME_CK()
						.toPlainString().trim()), "MEME CK for Claim ID : "
						+ claimID);

		// Product ID
		softAssert.assertEquals(databaseTable.get(claimID).getPDPD_ID(),
				demographicTable.get(claimID).getPRDCT_ID(),
				"Product ID for Claim ID : " + claimID);

		// LOB ID
		softAssert.assertEquals(databaseTable.get(claimID).getLOBD_ID(),
				demographicTable.get(claimID).getLOB_ID(),
				"LOB ID for Claim ID : " + claimID);

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] allData() {

		Object[][] data = new Object[keywordlist.size()][4];
		Hashtable<String, KeywordFile> keywordTable = null;
		Hashtable<String, DatabaseKeyword> databaseTable = null;
		Hashtable<String, Demographic> demographicTable = null;
		for (int i = 0; i < keywordlist.size(); i++) {
			String kwd = keywordlist.get(i).getClaim_number();
			keywordTable = new Hashtable<String, KeywordFile>();
			keywordTable.put(kwd, keywordlist.get(i));
			databaseTable = new Hashtable<String, DatabaseKeyword>();
			databaseTable.put(kwd, databaselist.get(i));
			demographicTable = new Hashtable<String, Demographic>();
			demographicTable.put(kwd, demographiclist.get(i));

			data[i][0] = kwd;
			data[i][1] = keywordTable;
			data[i][2] = databaseTable;
			data[i][3] = demographicTable;
		}

		return data;

	}

	@AfterSuite
	public void closeConnection() {
		if (session != null) {
			logger1.info("Closing DB Connection...");
			session.close();
			logger1.info("Succesfully closed DB connection!");
		}
		logger1.info("Test Execution Ended!");
	}

}
