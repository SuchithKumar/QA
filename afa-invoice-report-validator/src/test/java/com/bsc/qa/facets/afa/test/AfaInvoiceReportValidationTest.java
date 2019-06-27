package com.bsc.qa.facets.afa.test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.facets.afa.dao.DatabaseUtil;
import com.bsc.qa.facets.afa.excel_reader.AfaInvoiceReportReader;
import com.bsc.qa.facets.afa.pojo.ClaimsActivitySummary;
import com.bsc.qa.facets.afa.pojo.FrontPage;
import com.bsc.qa.facets.afa.pojo.MemberDetails;
import com.bsc.qa.facets.afa.pojo.ShieldSaving;
import com.bsc.qa.facets.afa.utility.Connection;
import com.bsc.qa.facets.afa.utility.HibernateUtil;


public class AfaInvoiceReportValidationTest {

	Connection conn = new Connection();
	AutomationStringUtilities decoder = new AutomationStringUtilities();
	SessionFactory factory;
	Map<String,FrontPage> frontPageDBMap;
	Map<String,FrontPage> frontPageExcelMap;
	Map<String, List<ShieldSaving>> shieldSavingDBMap;
	Map<String, List<ShieldSaving>> shieldSavingExcelMap;
	Map<String, List<ClaimsActivitySummary>> claimsActivitySummaryDBMap;
	Map<String, List<ClaimsActivitySummary>> claimsActivitySummaryExcelMap;
	Map<String, List<MemberDetails>> memberDetailsDBMap;
	Map<String, List<MemberDetails>> memberDetailsExcelMap;
	String oracleUser = decoder.decryptValue(System.getenv("ORACLE_USER"));
	String oraclePassword = decoder.decryptValue(System.getenv("ORACLE_PASSWORD"));
	String oracleServer = System.getenv("ORACLE_SERVER");
	String oraclePort = System.getenv("ORACLE_PORT");
	String oracleDB = System.getenv("ORACLE_DB");
	String oracleUrl = "jdbc:oracle:thin:@" + oracleServer + ":" + oraclePort + ":" + oracleDB ;
	Set<String> inputSet;
	public static Session session;
	
	
	@BeforeSuite
	public void getConnection() {
		AutomationStringUtilities decoder = new AutomationStringUtilities();
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		factory = HibernateUtil.createSessionFactory(conn);
		session = factory.openSession();
		Transaction txn = session.beginTransaction();
		boolean success = session.isConnected();
		txn.commit();
		if (success == true)
			System.out.println("Succesfully connected to DB!!");
	}
	
	@BeforeTest
	public void beforeTest(){
		DatabaseUtil util = new DatabaseUtil();
		AfaInvoiceReportReader reader = new AfaInvoiceReportReader();
		inputSet = reader.getInputSet();
//		frontPageDBMap= util.getFrontPageData();
//		frontPageExcelMap = reader.getFrontPageData();
//		shieldSavingDBMap=util.getShieldSaving();
//		shieldSavingExcelMap=reader.getShieldSavingData(shieldSavingDBMap);
//		claimsActivitySummaryDBMap = util.getClaimsActivitySummary();
//		claimsActivitySummaryExcelMap = reader.getClaimsActivitySummaryData(claimsActivitySummaryDBMap);
		memberDetailsDBMap = util.getMemberDetails();
		memberDetailsExcelMap = reader.getMemberDetailsData(memberDetailsDBMap);
		
	}
	
	@Test(dataProvider="provideFrontPageData")	
	public void testFrontPageData(String invoiceNo, Hashtable<String, FrontPage> frontPageExcelTable,Hashtable<String, FrontPage> frontPageDBTable){
//		SoftAssert softassert = new SoftAssert();
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getGroupName(), frontPageDBTable.get(invoiceNo).getGroupName());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getGroupAddress(), frontPageDBTable.get(invoiceNo).getGroupAddress());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getAttention(), frontPageDBTable.get(invoiceNo).getAttention());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getGroupBillingId(), frontPageDBTable.get(invoiceNo).getGroupBillingId());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getFundingPeriod(), frontPageDBTable.get(invoiceNo).getFundingPeriod());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getBillDueDate(), frontPageDBTable.get(invoiceNo).getBillDueDate());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getInvoiceNo(), frontPageDBTable.get(invoiceNo).getInvoiceNo());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getInvoiceDate(), frontPageDBTable.get(invoiceNo).getInvoiceDate());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getCurrentPeriodClaims(), frontPageDBTable.get(invoiceNo).getCurrentPeriodClaims());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getBalanceForward(), frontPageDBTable.get(invoiceNo).getBalanceForward());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getTotalDueForClaimsReimbursement(), frontPageDBTable.get(invoiceNo).getTotalDueForClaimsReimbursement());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getBscAccountantName(), frontPageDBTable.get(invoiceNo).getBscAccountantName());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getPhone(), frontPageDBTable.get(invoiceNo).getPhone());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getFax(), frontPageDBTable.get(invoiceNo).getFax());
//		softassert.assertEquals(frontPageExcelTable.get(invoiceNo).getEmail(), frontPageDBTable.get(invoiceNo).getEmail());
//		softassert.assertAll();
		
	}
	
	@Test(dataProvider="provideClaimsActivitySummaryData")
	public void testClaimsActivitySummaryData(String invoiceNo,Hashtable<String,  List<ClaimsActivitySummary>> ClaimsActivitySummaryExcelTable,Hashtable<String,  List<ClaimsActivitySummary>> ClaimsActivitySummaryDBTable){
//		List<ClaimsActivitySummary> claimsActivitySummaryExcelList = claimsActivitySummaryExcelMap.get(invoiceNo);
//		List<ClaimsActivitySummary> claimsActivitySummaryDBList = claimsActivitySummaryDBMap.get(invoiceNo);
//		SoftAssert softAssert = new SoftAssert();
//		for(ClaimsActivitySummary excelClaim : claimsActivitySummaryExcelList){
//			
//			for(int i=0;i<claimsActivitySummaryDBList.size();i++){
//				ClaimsActivitySummary dbClaim = claimsActivitySummaryDBList.get(i);
//				if(excelClaim.getBillingCategory().equalsIgnoreCase(dbClaim.getBillingCategory())){
//					
//					softAssert.assertEquals(excelClaim.getClaimPaymentPeriod()        ,dbClaim.getClaimPaymentPeriod()        ,"claimPaymentPeriod Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbClaim.getBillingCategory());              
//					softAssert.assertEquals(excelClaim.getInvoiceDate()               ,dbClaim.getInvoiceDate()               ,"invoiceDate Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getGroupName()                 ,dbClaim.getGroupName()                 ,"groupName Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getGroupBillingId()            ,dbClaim.getGroupBillingId()            ,"groupBillingId Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getClaimsCycle()               ,dbClaim.getClaimsCycle()               ,"claimsCycle Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getBillDueDate()               ,dbClaim.getBillDueDate()               ,"billDueDate Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getInvoiceNo()                 ,dbClaim.getInvoiceNo()                 ,"invoiceNo Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getBillingCategory()           ,dbClaim.getBillingCategory()           ,"billingCategory Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getMedical()                   ,dbClaim.getMedical()                   ,"medical Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getCostContainment()           ,dbClaim.getCostContainment()           ,"costContainment Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getInterest()                  ,dbClaim.getInterest()                  ,"interest Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getDental()                    ,dbClaim.getDental()                    ,"dental Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getPharmacy()                  ,dbClaim.getPharmacy()                  ,"pharmacy Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getBlueCardAccessFees()        ,dbClaim.getBlueCardAccessFees()        ,"blueCardAccessFees Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getStopLossAdvancedFunding()   ,dbClaim.getStopLossAdvancedFunding()   ,"stopLossAdvancedFunding Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getHealthReimbursementAccount(),dbClaim.getHealthReimbursementAccount(),"healthReimbursementAccount Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getSubTotalClaimsActivity()    ,dbClaim.getSubTotalClaimsActivity()    ,"subTotalClaimsActivity Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					softAssert.assertEquals(excelClaim.getTotal()                     ,dbClaim.getTotal()                     ,"total Mismatch for Invoice NO:"+invoiceNo+" Billing category:"+dbClaim.getBillingCategory());
//					
//				}
//			}
//			
//		}
//		softAssert.assertAll();
	}
	
	@Test(dataProvider="provideShieldSavingData")
	public void testShieldSavingData(String invoiceNo,Hashtable<String,  List<ShieldSaving>> shieldSavingExcelTable,Hashtable<String,  List<ShieldSaving>> shieldSavingDBTable){
//		List<ShieldSaving> shieldSavingExcelList = shieldSavingExcelMap.get(invoiceNo);
//		List<ShieldSaving> shieldSavingDBList = shieldSavingDBMap.get(invoiceNo);
//		SoftAssert softAssert = new SoftAssert();
//		for(ShieldSaving excelShieldSaving : shieldSavingExcelList){
//		for(int i=0;i<shieldSavingDBList.size();i++){
//			ShieldSaving dbShieldSaving = shieldSavingDBList.get(i);
//			if(excelShieldSaving.getBillingCategory().equalsIgnoreCase(dbShieldSaving.getBillingCategory())){
//				softAssert.assertEquals(excelShieldSaving.getClaimPaymentPeriod()       ,dbShieldSaving.getClaimPaymentPeriod()           ,"ClaimPaymentPeriod Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getGroupName()       			,dbShieldSaving.getGroupName()       		        ,"GroupName Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getGroupBillingId()           ,dbShieldSaving.getGroupBillingId()               ,"GroupBillingId Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getClaimsCycle()       	    ,dbShieldSaving.getClaimsCycle()       	        ,"ClaimsCycle Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getBillDueDate()			    ,dbShieldSaving.getBillDueDate()			        ,"BillDueDate Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getInvoiceNo()       			,dbShieldSaving.getInvoiceNo()       		        ,"InvoiceNo Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getBillingCategory()        	,dbShieldSaving.getBillingCategory()              ,"BillingCategory Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getProviderChargedAmount()    ,dbShieldSaving.getProviderChargedAmount()        ,"ProviderChargedAmount Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getSavings()       			,dbShieldSaving.getSavings()       		        ,"Savings Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getDisallowed()        		,dbShieldSaving.getDisallowed()        	        ,"Disallowed Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getAllowedAmount()        	,dbShieldSaving.getAllowedAmount()                ,"AllowedAmount Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getCostContainment()        	,dbShieldSaving.getCostContainment()              ,"CostContainment Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				softAssert.assertEquals(excelShieldSaving.getTotal()        			,dbShieldSaving.getTotal()        		        ,"Total Mismatch for Invoice NO:"+invoiceNo +" Billing category:"+dbShieldSaving.getBillingCategory());
//				
//				}
//			}
//		}
//		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] provideFrontPageData(){
		Object[][] data = new Object[inputSet.size()][3];
//		Hashtable<String, FrontPage> frontPageExcelTable = null;
//		Hashtable<String, FrontPage> frontPageDBTable = null;
//		
//		int i=0;
//		for (String invoiceNo : inputSet) {
//			frontPageExcelTable = new Hashtable<String, FrontPage>();
//			frontPageExcelTable.put(invoiceNo, frontPageExcelMap.get(invoiceNo));
//			frontPageDBTable = new Hashtable<String, FrontPage>();
//			frontPageDBTable.put(invoiceNo,frontPageDBMap.get(invoiceNo));
//			data[i][0] = invoiceNo;
//			data[i][1] = frontPageExcelTable;
//			data[i][2] = frontPageDBTable;
//			i++;
//		}
		return data;
	}
	
	@DataProvider
	public Object[][] provideClaimsActivitySummaryData(){
		Object[][] data = new Object[inputSet.size()][3];
//		Hashtable<String,  List<ClaimsActivitySummary>> ClaimsActivitySummaryExcelTable = null;
//		Hashtable<String,  List<ClaimsActivitySummary>> ClaimsActivitySummaryDBTable = null;
//		
//		int i=0;
//		for (String invoiceNo : inputSet) {
//			ClaimsActivitySummaryExcelTable = new Hashtable<String , List<ClaimsActivitySummary>>();
//			ClaimsActivitySummaryExcelTable.put(invoiceNo, claimsActivitySummaryExcelMap.get(invoiceNo));
//			ClaimsActivitySummaryDBTable = new Hashtable<String, List<ClaimsActivitySummary>>();
//			ClaimsActivitySummaryDBTable.put(invoiceNo,claimsActivitySummaryDBMap.get(invoiceNo));
//			data[i][0] = invoiceNo;
//			data[i][1] = ClaimsActivitySummaryExcelTable;
//			data[i][2] = ClaimsActivitySummaryDBTable;
//			i++;
//		}
		return data;
	}
	
	@DataProvider
	public Object[][] provideShieldSavingData(){
		Object[][] data = new Object[inputSet.size()][3];
//		Hashtable<String,  List<ShieldSaving>> shieldSavingExcelTable = null;
//		Hashtable<String,  List<ShieldSaving>> shieldSavingDBTable = null;
//		
//		int i=0;
//		for (String invoiceNo : inputSet) {
//			shieldSavingExcelTable = new Hashtable<String , List<ShieldSaving>>();
//			System.out.println(shieldSavingExcelMap.get(invoiceNo).size()+" Map");
//			shieldSavingExcelTable.put(invoiceNo, shieldSavingExcelMap.get(invoiceNo));
//			shieldSavingDBTable = new Hashtable<String, List<ShieldSaving>>();
//			shieldSavingDBTable.put(invoiceNo,shieldSavingDBMap.get(invoiceNo));
//			data[i][0] = invoiceNo;
//			data[i][1] = shieldSavingExcelTable;
//			data[i][2] = shieldSavingDBTable;
//			i++;
//		}
		return data;
	}
	
	@AfterSuite
	public void closeConnection() {
		if (session != null) {
			session.close();
			System.out.println("DB Connection Succesfully closed!");
		}
	}
	
}
