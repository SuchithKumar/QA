package com.bsc.qa.facets.afa.excel_reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import com.bsc.qa.facets.afa.pojo.ClaimsActivitySummary;
import com.bsc.qa.facets.afa.pojo.FrontPage;
import com.bsc.qa.facets.afa.pojo.ShieldSaving;
import com.bsc.qa.facets.afa.test.AfaInvoiceReportValidationTest;
import com.monitorjbl.xlsx.StreamingReader;

public class AfaInvoiceReportReader {
	static File inputFile = new File(new File("").getAbsoluteFile()
			+ "\\src\\test\\resources\\input\\input.xlsx");
	static File afaInvoiceReport = new File(
			new File("").getAbsoluteFile()
					+ "\\src\\test\\resources\\input\\BRD543709-AFA Claims Billing Invoice report_01.xlsx");
	private static Set<String> inputInvoiceNoSet = new HashSet<String>();
	private Map<String,FrontPage> frontPageExcelMap ;
	private Map<String,List<ShieldSaving>> shieldSavingExcelMap;
	private Map<String,List<ClaimsActivitySummary>> claimsActivitySummaryExcelMap;
	
	public static Set<String> getInputSet() {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(inputFile);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				Cell cell = row.getCell(1);
				String invoiceNo = getCellValue(cell);
				if (invoiceNo.length() == 12) {
					inputInvoiceNoSet.add(invoiceNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputInvoiceNoSet;

	}

	private static String getCellValue(Cell cell) {
		String celldata = "";
		try {
			switch (cell.getCellTypeEnum()) {
			case STRING:
				celldata = cell.getRichStringCellValue().getString().trim();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					celldata = cell.getDateCellValue().toString();
				} else {
					BigDecimal bigDecimal = BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros();
					celldata = bigDecimal.toPlainString().trim();
				}
				break;
			default:
				celldata = "";

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return celldata;

	}

	public  Map<String,FrontPage> getFrontPageData() {
		frontPageExcelMap = new HashMap<String, FrontPage>();
		
		inputInvoiceNoSet = getInputSet();
		for (String invoiceNO : inputInvoiceNoSet) {
			FrontPage frontPage = new FrontPage();
			try {
				
				Workbook workbook = WorkbookFactory.create(afaInvoiceReport);
				Sheet frontPageSheet = workbook.getSheetAt(0);

				for (int i = 1; i <= frontPageSheet.getLastRowNum(); i++) {
					Row row = frontPageSheet.getRow(i);
					Cell cell = row.getCell(2);
					String celldata = getCellValue(cell);
					if (celldata.contains(invoiceNO)) {
						int rowno = cell.getRowIndex();
						
						Cell groupName = frontPageSheet.getRow(rowno-8).getCell(2);
						Cell groupAddressLine1 = frontPageSheet.getRow(rowno-7).getCell(2);
						Cell groupAddressLine2 = frontPageSheet.getRow(rowno-6).getCell(2);
						Cell attention = frontPageSheet.getRow(rowno-5).getCell(2);
						Cell groupBillingId = frontPageSheet.getRow(rowno-3).getCell(2);
						Cell fundingPeriod = frontPageSheet.getRow(rowno-2).getCell(2);
						Cell billDueDate = frontPageSheet.getRow(rowno-1).getCell(2);
						Cell invoiceNo = frontPageSheet.getRow(rowno).getCell(2);
						Cell invoiceDate = frontPageSheet.getRow(rowno+1).getCell(2);
						Cell currentPeriodClaims = frontPageSheet.getRow(rowno+3).getCell(4);
						Cell balanceForward = frontPageSheet.getRow(rowno+4).getCell(4);
						Cell totalDueForClaimsReimbursement = frontPageSheet.getRow(rowno+6).getCell(4);
						Cell bscAccountName = frontPageSheet.getRow(rowno+10).getCell(6);
						Cell phone = frontPageSheet.getRow(rowno+12).getCell(5);
						Cell fax = frontPageSheet.getRow(rowno+13).getCell(5);
						Cell email = frontPageSheet.getRow(rowno+14).getCell(5);

						frontPage.setGroupName(getCellValue(groupName).trim());
						frontPage.setGroupAddress(getCellValue(groupAddressLine1).trim()+"  "+getCellValue(groupAddressLine2).trim());
						frontPage.setAttention(getCellValue(attention).trim());
						frontPage.setGroupBillingId(getCellValue(groupBillingId).trim());
						frontPage.setAttention(getCellValue(attention).trim().trim());
						frontPage.setGroupBillingId(getCellValue(groupBillingId).trim());
						frontPage.setFundingPeriod(getCellValue(fundingPeriod).trim());
						frontPage.setBillDueDate(getCellValue(billDueDate).trim());
						frontPage.setInvoiceNo(getCellValue(invoiceNo).trim());
						frontPage.setInvoiceDate(getCellValue(invoiceDate).trim());
						frontPage.setCurrentPeriodClaims("$"+getCellValue(currentPeriodClaims).trim());
						frontPage.setBalanceForward("$"+getCellValue(balanceForward).trim());
						frontPage.setTotalDueForClaimsReimbursement("$"+getCellValue(totalDueForClaimsReimbursement).trim());
						frontPage.setBscAccountantName(getCellValue(bscAccountName).trim());
						frontPage.setPhone(getCellValue(phone).trim());
						frontPage.setFax(getCellValue(fax).trim());
						frontPage.setEmail(getCellValue(email).trim());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			frontPageExcelMap.put(invoiceNO,frontPage);
			System.out.println("Excel Row   : "+frontPage);
		}
		return frontPageExcelMap;
	}
	
	
	public  Map<String,List<ShieldSaving>> getShieldSavingData(Map<String,List<ShieldSaving>> shieldSavingDBData) {
		shieldSavingExcelMap = new HashMap<String, List<ShieldSaving>>();
		inputInvoiceNoSet = getInputSet();
		
		for (String invoiceNO : inputInvoiceNoSet) {
			List<ShieldSaving> shieldSavingDBList = shieldSavingDBData.get(invoiceNO);
			List<ShieldSaving> shieldSavingExcelList = new ArrayList<ShieldSaving>();
			try {
				Workbook workbook = WorkbookFactory.create(afaInvoiceReport);
				Sheet shieldSavingSheet = workbook.getSheetAt(2);

				for (int i = 1; i <= shieldSavingSheet.getLastRowNum(); i++) {
					Row row = shieldSavingSheet.getRow(i);
					Cell cell = row.getCell(3);
					String celldata = getCellValue(cell);
					if (celldata.contains(invoiceNO)) {
						int rowno = cell.getRowIndex();
						for(int j=0;j<shieldSavingDBList.size();j++){
							ShieldSaving shieldSaving = new ShieldSaving();
							Cell claimPaymentPeriod = shieldSavingSheet.getRow(rowno-6).getCell(2);
							Cell groupName = shieldSavingSheet.getRow(rowno-4).getCell(3);
							Cell groupBillingID = shieldSavingSheet.getRow(rowno-3).getCell(3);
							Cell claimsCycle = shieldSavingSheet.getRow(rowno-2).getCell(3);
							Cell billDueDate = shieldSavingSheet.getRow(rowno-1).getCell(3);
							Cell invoiceNo = shieldSavingSheet.getRow(rowno).getCell(3);
							
							shieldSaving.setClaimPaymentPeriod(getCellValue(claimPaymentPeriod));
							shieldSaving.setGroupName(getCellValue(groupName));
							shieldSaving.setGroupBillingId(getCellValue(groupBillingID));
							shieldSaving.setClaimsCycle(getCellValue(claimsCycle));
							shieldSaving.setBillDueDate(getCellValue(billDueDate));
							shieldSaving.setInvoiceNo(getCellValue(invoiceNo));
							String findbillingCategory = shieldSavingDBList.get(j).getBillingCategory();
//							System.out.println("findbillingCategory : "+findbillingCategory);
							Row billingCategoryRow = shieldSavingSheet.getRow(rowno+4);
							Cell billingCategory 	   = null;
							Cell providerChargedAmount = null;
							Cell savings 			   = null;
							Cell disAllowed 		   = null;
							Cell allowedAmount 		   = null;
							Cell costContainment 	   = null;
							Cell total 				   = null;
							int billingCatIndex = 0;
							int totalIndex = 0;
							for(int a=0;a<billingCategoryRow.getLastCellNum();a++){
								Cell bc = billingCategoryRow.getCell(a);
								if(getCellValue(bc).trim().replace("Billing Category ", "").contains(findbillingCategory)){
									billingCatIndex = bc.getColumnIndex();
//									System.out.println(billingCatIndex+" - billingCatIndex");
									 billingCategory = shieldSavingSheet.getRow(rowno+4).getCell(billingCatIndex);
									 providerChargedAmount = shieldSavingSheet.getRow(rowno+6).getCell(billingCatIndex);
									 savings = shieldSavingSheet.getRow(rowno+7).getCell(billingCatIndex);
									 disAllowed = shieldSavingSheet.getRow(rowno+8).getCell(billingCatIndex);
									 allowedAmount = shieldSavingSheet.getRow(rowno+9).getCell(billingCatIndex);
									 costContainment = shieldSavingSheet.getRow(rowno+10).getCell(billingCatIndex);
									
									
								}
								if(getCellValue(bc).trim().contains("Total")){
								 total = shieldSavingSheet.getRow(rowno+11).getCell(billingCatIndex);
								}
							}
							
							String stringBillingCategory = getCellValue(billingCategory);
							shieldSaving.setBillingCategory(stringBillingCategory.replace("Billing Category ", "").trim());
							
							shieldSaving.setProviderChargedAmount("$"+getCellValue(providerChargedAmount));
							
							String stringSavings = getCellValue(savings);
							shieldSaving.setSavings("$"+stringSavings.replace("-", "").trim());
							
							shieldSaving.setDisallowed("$"+getCellValue(disAllowed));
							shieldSaving.setAllowedAmount("$"+getCellValue(allowedAmount));
							shieldSaving.setCostContainment("$"+getCellValue(costContainment));
							shieldSaving.setTotal("$"+getCellValue(total));
							shieldSavingExcelList.add(shieldSaving);
							System.out.println("Excel Row   : "+shieldSaving);
							
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			shieldSavingExcelMap.put(invoiceNO,shieldSavingExcelList);
			
		}
		return shieldSavingExcelMap;
	}

	public  Map<String,List<ClaimsActivitySummary>>getClaimsActivitySummaryData(Map<String,List<ClaimsActivitySummary>> claimsActivitySummaryDBMap) {
		Map<String,List<ClaimsActivitySummary>> claimsActivitySummaryExcelMap = new HashMap<String, List<ClaimsActivitySummary>>();
		
		inputInvoiceNoSet = getInputSet();
		for (String invoiceNO : inputInvoiceNoSet) {
			List<ClaimsActivitySummary> claimsActivitySummaryDBList = claimsActivitySummaryDBMap.get(invoiceNO);
			List<ClaimsActivitySummary> claimsActivitySummaryExcelList = new ArrayList<ClaimsActivitySummary>();
//			for(ClaimsActivitySummary claims:claimsActivitySummaryDBList){
//				System.out.println("DataBase Row: "+claims);
//			}
			try {
				
				Workbook workbook = WorkbookFactory.create(afaInvoiceReport);
				Sheet claimsActivitySummarySheet = workbook.getSheetAt(1);

				for (int i = 1; i <= claimsActivitySummarySheet.getLastRowNum(); i++) {
					
					Row row = claimsActivitySummarySheet.getRow(i);
					Cell cell = row.getCell(3);
					String celldata = getCellValue(cell);
					if (celldata.contains(invoiceNO)) {
						int rowno = cell.getRowIndex();
					for(int j=0;j<claimsActivitySummaryDBList.size();j++){
						ClaimsActivitySummary claimsActivitySummary = new ClaimsActivitySummary();
						Cell claimPaymentPeriod = claimsActivitySummarySheet.getRow(rowno-7).getCell(2);
						Cell invoiceDate = claimsActivitySummarySheet.getRow(rowno-5).getCell(3);
						Cell groupName = claimsActivitySummarySheet.getRow(rowno-4).getCell(3);
						Cell groupBillingId = claimsActivitySummarySheet.getRow(rowno-3).getCell(3);
						Cell claimsCycle = claimsActivitySummarySheet.getRow(rowno-2).getCell(3);
						Cell billDueDate = claimsActivitySummarySheet.getRow(rowno-1).getCell(3);
						Cell invoiceNo = claimsActivitySummarySheet.getRow(rowno).getCell(3);
						Row billingCategoryRow = claimsActivitySummarySheet.getRow(rowno+2);
						int billingCatIndex = 0;
						int totalIndex=0;
						String findbillingCategory = claimsActivitySummaryDBList.get(j).getBillingCategory();
//						System.out.println(findbillingCategory+"-findbillingCategory");
//						System.out.println(claimsActivitySummaryDBList.get(j)+" "+j);
						Cell billingCategory	    	=    null;
						Cell medical 					=    null;
						Cell costContainment	  	 	=    null;
						Cell interest			   	 	=    null;
						Cell dental 					=    null;
						Cell pharmacy			    	=    null;
						Cell blueCardAcessFees 			=    null;
						Cell stoplossAdvancedFunding 	=    null;
						Cell healthReimbursementAccount =    null;
						Cell subTotalClaimsActivity 	=    null;
						Cell total 						=    null;
						for(int a=0;a<billingCategoryRow.getLastCellNum();a++){
							Cell bc = billingCategoryRow.getCell(a);
							if(getCellValue(bc).trim().replace("Billing Category ", "").contains(findbillingCategory)){
								billingCatIndex = bc.getColumnIndex();
								 billingCategory	    	= claimsActivitySummarySheet.getRow(rowno+2).getCell(billingCatIndex);
								 medical 					= claimsActivitySummarySheet.getRow(rowno+4).getCell(billingCatIndex);
								 costContainment	  	 	= claimsActivitySummarySheet.getRow(rowno+5).getCell(billingCatIndex);
								 interest			   	 	= claimsActivitySummarySheet.getRow(rowno+6).getCell(billingCatIndex);
								 dental 					= claimsActivitySummarySheet.getRow(rowno+7).getCell(billingCatIndex);
								 pharmacy			    	= claimsActivitySummarySheet.getRow(rowno+8).getCell(billingCatIndex);
								 blueCardAcessFees 			= claimsActivitySummarySheet.getRow(rowno+9).getCell(billingCatIndex);
								 stoplossAdvancedFunding 	= claimsActivitySummarySheet.getRow(rowno+10).getCell(billingCatIndex);
								 healthReimbursementAccount = claimsActivitySummarySheet.getRow(rowno+11).getCell(billingCatIndex);
								 subTotalClaimsActivity 	= claimsActivitySummarySheet.getRow(rowno+12).getCell(billingCatIndex);
								 
							}
							if(getCellValue(bc).trim().contains("Total")){
								totalIndex = bc.getColumnIndex();
								total 						= claimsActivitySummarySheet.getRow(rowno+12).getCell(totalIndex);
							}
						}
							
						claimsActivitySummary.setClaimPaymentPeriod(getCellValue(claimPaymentPeriod));
						claimsActivitySummary.setInvoiceDate(getCellValue(invoiceDate));
						claimsActivitySummary.setGroupName(getCellValue(groupName));
						claimsActivitySummary.setGroupBillingId(getCellValue(groupBillingId));
						claimsActivitySummary.setClaimsCycle(getCellValue(claimsCycle));
						claimsActivitySummary.setBillDueDate(getCellValue(billDueDate));
						claimsActivitySummary.setInvoiceNo(getCellValue(invoiceNo));
						String stringBillingCategory = getCellValue(billingCategory);
						claimsActivitySummary.setBillingCategory(stringBillingCategory.replace("Billing Category ", "").trim());
						claimsActivitySummary.setMedical("$"+getCellValue(medical));
						claimsActivitySummary.setCostContainment("$"+getCellValue(costContainment));
						claimsActivitySummary.setInterest("$"+getCellValue(interest));
						claimsActivitySummary.setDental("$"+getCellValue(dental));
						claimsActivitySummary.setPharmacy("$"+getCellValue(pharmacy));
						claimsActivitySummary.setBlueCardAccessFees("$"+getCellValue(blueCardAcessFees));
						claimsActivitySummary.setStopLossAdvancedFunding("$"+getCellValue(stoplossAdvancedFunding));
						claimsActivitySummary.setHealthReimbursementAccount("$"+getCellValue(healthReimbursementAccount));
						claimsActivitySummary.setSubTotalClaimsActivity("$"+getCellValue(subTotalClaimsActivity));
						claimsActivitySummary.setTotal("$"+getCellValue(total));
						claimsActivitySummaryExcelList.add(claimsActivitySummary);	
						System.out.println("Excel Row   : "+claimsActivitySummary);
						
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			claimsActivitySummaryExcelMap.put(invoiceNO, claimsActivitySummaryExcelList);
		}
		return claimsActivitySummaryExcelMap;
	}
}
