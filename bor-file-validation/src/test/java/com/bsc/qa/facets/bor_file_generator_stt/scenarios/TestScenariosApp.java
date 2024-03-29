package com.bsc.qa.facets.bor_file_generator_stt.scenarios;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsc.qa.facets.afa.pojo.AmountFieldsApp;
import com.bsc.qa.facets.afa.pojo.BORDatabaseApp;
import com.bsc.qa.facets.afa.pojo.BORFileApp;
import com.bsc.qa.facets.afa.pojo.BorAdjustmentDBApp;
import com.bsc.qa.facets.bor_file_generator_stt.util.DatabaseValidationApp;
import com.bsc.qa.facets.bor_file_generator_stt.util.HelperClassApp;

public class TestScenariosApp {
	String[] pharmacies = {"0107777,EXPO PHARMACY",                         
			"5600742,CVS PHARMACY 16748",                         
			"5664316,LOYALTY PHARMACY 2",                         
			"5621188,CVS PHARMACY 08895",                         
			"0107777,EXPO PHARMACY",                         
			"1167976,ROCKDALE PHARMACY",                         
			"1023768,NORTH FLORIDA PHARMA",                         
			"5660584,OMNI FAMILY HEALTH 3",                         
			"0517459,RITE AID PHARMACY 05",                         
			"5619943,CVS PHARMACY 09627",                         
			"2591990,PEOPLE'S PHARMACY",                         
			"5600665,CVS PHARMACY 16040",                         
			"5619690,CVS PHARMACY 09774",                         
			"4408705,LOWE'S DRUGS",                         
			"5633448,RITE AID PHARMACY 06",                         
			"5916258,MANOR PHARMACY 1",                         
			"2803509,WANEK PHARMACY"  };
	String todayDateFake = HelperClassApp.getTodaysDateFake();
	String todayDate = HelperClassApp.getTodaysDate();
	Logger logger = LoggerFactory.getLogger(TestScenariosApp.class);
	public BORFileApp getPaidScenario1(Session session,
			List<BORDatabaseApp> borDatabaseList, String borFileName) {
		// Paid Scenario 1
		Random random = new Random();
		int payeeIndex = random.nextInt(16);
		String[] payee = pharmacies[payeeIndex].split(",");
		String payeeId = payee[0];
		String payeeName = payee[1];
		
		String todayDate = HelperClassApp.getTodaysDateFake();
		BORDatabaseApp borDatabase = borDatabaseList.get(0);
		BORFileApp borfile = new BORFileApp();
		String claimID = DatabaseValidationApp.generateUniqueClaimNumber(session);
		String checkNumber = DatabaseValidationApp.generateCheckNumber(session);
		String fictClmId = claimID.replace("R", "2");
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		AmountFieldsApp amountFields = HelperClassApp.getAmountFields(billedAmount);
		String personNumber = "0" + String.valueOf(borDatabase.getMemSfx());
		borfile.setClaimId(claimID);
		borfile.setFileName(borFileName);
		borfile.setVendorName("ARGS");
		borfile.setGroupNumber(borDatabase.getGrpId());
		borfile.setSubgroupId(borDatabase.getSubgrpId());
		borfile.setSubscriberId(borDatabase.getSubId());
		borfile.setPersonNumber(personNumber);
		borfile.setClaimNumber(fictClmId);
		borfile.setClaimVersionNumber("00");
		borfile.setClaimAmount(amountFields.getClaimAmount());
		borfile.setClientPrice(amountFields.getClientPrice());
		borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borfile.setCheckNumber(checkNumber);
		borfile.setCheckDate(todayDate);
		borfile.setServiceDate(todayDate);
		borfile.setPayeeId(payeeId);
		borfile.setPayeeName(payeeName);
		borfile.setPlanId(borDatabase.getPlanId());
		borfile.setProductId(borDatabase.getPrdId());
		borfile.setProductCategory(borDatabase.getPrdCat());
		borfile.setClassId(borDatabase.getClassId());
		borfile.setProductBusinessCategory(borDatabase.getPrdBusCat());
		borfile.setProductValueCode(borDatabase.getPrdValCD());
		borfile.setLineOfBusinessId(borDatabase.getLobdId());
		borfile.setLegalEntity(borDatabase.getLobType());
		borfile.setBilledAmount(amountFields.getBilledAmount());
		borfile.setAllowedAmount(amountFields.getAllowedAmount());
		borfile.setDeductibleAmount(amountFields.getDeductible());
		borfile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borfile.setCopayAmount(amountFields.getCopay());
		borfile.setDiagnosisCode("9999");
		borfile.setDiagnosisCodeType(" ");
		borfile.setProcedureCode("99199");
		borfile.setHcpcs_id(" ");
		borfile.setClaimTransactionType("P");
		logger.info("Scenario 1 written in BOR File");
		return borfile;
	}

	public BORFileApp getPaidScenario2(Session session,
		List<BORDatabaseApp> borDatabaseList, String borFileName) {
		
		Random random = new Random();
		int payeeIndex = random.nextInt(16);
		String[] payee = pharmacies[payeeIndex].split(",");
		String payeeId = payee[0];
		String payeeName = payee[1];
		String todayDate = HelperClassApp.getTodaysDateFake();

		BORDatabaseApp borDatabase1 = borDatabaseList.get(1);
		BORFileApp borfile1 = new BORFileApp();
		String claimID1 = DatabaseValidationApp.generateUniqueClaimNumber(session);
		String checkNumber1 = DatabaseValidationApp.generateCheckNumber(session);
		String fictClmId1 = claimID1.replace("R", "2");
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		AmountFieldsApp amountFields1 = HelperClassApp.getAmountFields(billedAmount);
		String personNumber1 = "0" + String.valueOf(borDatabase1.getMemSfx());
		borfile1.setClaimId(claimID1);
		borfile1.setFileName(borFileName);
		borfile1.setVendorName("ARGS");
		borfile1.setGroupNumber(borDatabase1.getGrpId());
		borfile1.setSubgroupId(borDatabase1.getSubgrpId());
		borfile1.setSubscriberId(borDatabase1.getSubId());
		borfile1.setPersonNumber(personNumber1);
		borfile1.setClaimNumber(fictClmId1);
		borfile1.setClaimVersionNumber("00");
		borfile1.setClaimAmount(amountFields1.getClientPrice());
		borfile1.setClientPrice(amountFields1.getClientPrice());
		borfile1.setBscRevenueAmount(String.valueOf(new BigDecimal("00")
				.setScale(2, BigDecimal.ROUND_HALF_UP)));
		borfile1.setCheckNumber(checkNumber1);
		borfile1.setCheckDate(todayDate);
		borfile1.setServiceDate(todayDate);
		borfile1.setPayeeId(payeeId);
		borfile1.setPayeeName(payeeName);
		borfile1.setPlanId(borDatabase1.getPlanId());
		borfile1.setProductId(borDatabase1.getPrdId());
		borfile1.setProductCategory(borDatabase1.getPrdCat());
		borfile1.setClassId(borDatabase1.getClassId());
		borfile1.setProductBusinessCategory(borDatabase1.getPrdBusCat());
		borfile1.setProductValueCode(borDatabase1.getPrdValCD());
		borfile1.setLineOfBusinessId(borDatabase1.getLobdId());
		borfile1.setLegalEntity(borDatabase1.getLobType());
		borfile1.setBilledAmount(amountFields1.getBilledAmount());
		borfile1.setAllowedAmount(amountFields1.getAllowedAmount());
		borfile1.setDeductibleAmount(amountFields1.getDeductible());
		borfile1.setCoinsuranceAmount(amountFields1.getCoInsurance());
		borfile1.setCopayAmount(amountFields1.getCopay());
		borfile1.setDiagnosisCode("9999");
		borfile1.setDiagnosisCodeType(" ");
		borfile1.setProcedureCode("99199");
		borfile1.setHcpcs_id(" ");
		borfile1.setClaimTransactionType("P");
		logger.info("Scenario 2 written in BOR File");
		return borfile1;
	}
	
	public BORFileApp getPaidScenario3(Session session,
			List<BORDatabaseApp> borDatabaseList, String borFileName){
		Random random = new Random();
		int payeeIndex = random.nextInt(16);
		String[] payee = pharmacies[payeeIndex].split(",");
		String payeeId = payee[0];
		String payeeName = payee[1];
		String todayDate = HelperClassApp.getTodaysDateFake();

		BORDatabaseApp borDatabase1 = borDatabaseList.get(2);
		BORFileApp borfile1 = new BORFileApp();
		String claimID1 = DatabaseValidationApp.generateUniqueClaimNumber(session);
		String checkNumber1 = DatabaseValidationApp.generateCheckNumber(session);
		String fictClmId1 = claimID1.replace("R", "2");
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		AmountFieldsApp amountFields1 = HelperClassApp.getAmountFields(billedAmount);
		String personNumber1 = "0" + String.valueOf(borDatabase1.getMemSfx());
		borfile1.setClaimId(claimID1);
		borfile1.setFileName(borFileName);
		borfile1.setVendorName("ARGS");
		borfile1.setGroupNumber(borDatabase1.getGrpId());
		borfile1.setSubgroupId(borDatabase1.getSubgrpId());
		borfile1.setSubscriberId(borDatabase1.getSubId());
		borfile1.setPersonNumber(personNumber1);
		borfile1.setClaimNumber(fictClmId1);
		borfile1.setClaimVersionNumber("00");
		borfile1.setClaimAmount(String.valueOf(new BigDecimal("00")
		.setScale(2, BigDecimal.ROUND_HALF_UP)));
		borfile1.setClientPrice(amountFields1.getClientPrice());
		borfile1.setBscRevenueAmount(amountFields1.getClientPrice());
		borfile1.setCheckNumber(checkNumber1);
		borfile1.setCheckDate(todayDate);
		borfile1.setServiceDate(todayDate);
		borfile1.setPayeeId(payeeId);
		borfile1.setPayeeName(payeeName);
		borfile1.setPlanId(borDatabase1.getPlanId());
		borfile1.setProductId(borDatabase1.getPrdId());
		borfile1.setProductCategory(borDatabase1.getPrdCat());
		borfile1.setClassId(borDatabase1.getClassId());
		borfile1.setProductBusinessCategory(borDatabase1.getPrdBusCat());
		borfile1.setProductValueCode(borDatabase1.getPrdValCD());
		borfile1.setLineOfBusinessId(borDatabase1.getLobdId());
		borfile1.setLegalEntity(borDatabase1.getLobType());
		borfile1.setBilledAmount(amountFields1.getBilledAmount());
		borfile1.setAllowedAmount(amountFields1.getAllowedAmount());
		borfile1.setDeductibleAmount(amountFields1.getDeductible());
		borfile1.setCoinsuranceAmount(amountFields1.getCoInsurance());
		borfile1.setCopayAmount(amountFields1.getCopay());
		borfile1.setDiagnosisCode("9999");
		borfile1.setDiagnosisCodeType(" ");
		borfile1.setProcedureCode("99199");
		borfile1.setHcpcs_id(" ");
		borfile1.setClaimTransactionType("P");
		logger.info("Scenario 3 written in BOR File");
		return borfile1;
	}
	
	public BORFileApp getPaidScenario4(Session session,
			List<BORDatabaseApp> borDatabaseList, String borFileName) {
		// Paid Scenario 1
		Random random = new Random();
		int payeeIndex = random.nextInt(16);
		String[] payee = pharmacies[payeeIndex].split(",");
		String payeeId = payee[0];
		String payeeName = payee[1];
		String lastYearsDate = HelperClassApp.getLastYearsDate();
		BORDatabaseApp borDatabase = borDatabaseList.get(3);
		BORFileApp borfile = new BORFileApp();
		String claimID = DatabaseValidationApp.generateUniqueClaimNumber(session);
		String checkNumber = DatabaseValidationApp.generateCheckNumber(session);
		String fictClmId = claimID.replace("R", "2");
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		AmountFieldsApp amountFields = HelperClassApp.getAmountFields(billedAmount);
		String personNumber = "0" + String.valueOf(borDatabase.getMemSfx());
		borfile.setClaimId(claimID);
		borfile.setFileName(borFileName);
		borfile.setVendorName("ARGS");
		borfile.setGroupNumber(borDatabase.getGrpId());
		borfile.setSubgroupId(borDatabase.getSubgrpId());
		borfile.setSubscriberId(borDatabase.getSubId());
		borfile.setPersonNumber(personNumber);
		borfile.setClaimNumber(fictClmId);
		borfile.setClaimVersionNumber("00");
		borfile.setClaimAmount(amountFields.getClaimAmount());
		borfile.setClientPrice(amountFields.getClientPrice());
		borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borfile.setCheckNumber(checkNumber);
		borfile.setCheckDate(lastYearsDate);
		borfile.setServiceDate(lastYearsDate);
		borfile.setPayeeId(payeeId);
		borfile.setPayeeName(payeeName);
		borfile.setPlanId(borDatabase.getPlanId());
		borfile.setProductId(borDatabase.getPrdId());
		borfile.setProductCategory(borDatabase.getPrdCat());
		borfile.setClassId(borDatabase.getClassId());
		borfile.setProductBusinessCategory(borDatabase.getPrdBusCat());
		borfile.setProductValueCode(borDatabase.getPrdValCD());
		borfile.setLineOfBusinessId(borDatabase.getLobdId());
		borfile.setLegalEntity(borDatabase.getLobType());
		borfile.setBilledAmount(amountFields.getBilledAmount());
		borfile.setAllowedAmount(amountFields.getAllowedAmount());
		borfile.setDeductibleAmount(amountFields.getDeductible());
		borfile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borfile.setCopayAmount(amountFields.getCopay());
		borfile.setDiagnosisCode("9999");
		borfile.setDiagnosisCodeType(" ");
		borfile.setProcedureCode("99199");
		borfile.setHcpcs_id(" ");
		borfile.setClaimTransactionType("P");
		logger.info("Scenario 4 written in BOR File");
		return borfile;
	}

	public BORFileApp getPaidScenario5(Session session,
			List<BORDatabaseApp> borDatabaseList, String borFileName) {
		// Paid Scenario 1
		Random random = new Random();
		int payeeIndex = random.nextInt(16);
		String[] payee = pharmacies[payeeIndex].split(",");
		String payeeId = payee[0];
		String payeeName = payee[1];
		
		String todayDate = HelperClassApp.getTodaysDateFake();
		BORDatabaseApp borDatabase = borDatabaseList.get(4);
		BORFileApp borfile = new BORFileApp();
		String claimID = DatabaseValidationApp.generateUniqueClaimNumber(session);
		String checkNumber = DatabaseValidationApp.generateCheckNumber(session);
		String fictClmId = claimID.replace("R", "2");
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		AmountFieldsApp amountFields = HelperClassApp.getAmountFields(billedAmount);
		String personNumber = "0" + String.valueOf(borDatabase.getMemSfx());
		borfile.setClaimId(claimID);
		borfile.setFileName(borFileName);
		borfile.setVendorName("ARGS");
		borfile.setGroupNumber(borDatabase.getGrpId());
		borfile.setSubgroupId(borDatabase.getSubgrpId());
		borfile.setSubscriberId(borDatabase.getSubId());
		borfile.setPersonNumber(personNumber);
		borfile.setClaimNumber(fictClmId);
		borfile.setClaimVersionNumber("00");
		borfile.setClaimAmount(amountFields.getClaimAmount());
		borfile.setClientPrice(amountFields.getClientPrice());
		borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borfile.setCheckNumber(checkNumber);
		borfile.setCheckDate(todayDate);
		borfile.setServiceDate(todayDate);
		borfile.setPayeeId(payeeId);
		borfile.setPayeeName(payeeName);
		borfile.setPlanId(borDatabase.getPlanId());
		borfile.setProductId(borDatabase.getPrdId());
		borfile.setProductCategory(borDatabase.getPrdCat());
		borfile.setClassId(borDatabase.getClassId());
		borfile.setProductBusinessCategory(borDatabase.getPrdBusCat());
		borfile.setProductValueCode(borDatabase.getPrdValCD());
		borfile.setLineOfBusinessId(borDatabase.getLobdId());
		borfile.setLegalEntity(borDatabase.getLobType());
		borfile.setBilledAmount(amountFields.getBilledAmount());
		borfile.setAllowedAmount(amountFields.getAllowedAmount());
		borfile.setDeductibleAmount(amountFields.getDeductible());
		borfile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borfile.setCopayAmount(amountFields.getCopay());
		borfile.setDiagnosisCode("9999");
		borfile.setDiagnosisCodeType(" ");
		borfile.setProcedureCode("99199");
		borfile.setHcpcs_id(" ");
		borfile.setClaimTransactionType("P");
		logger.info("Scenario 5 written in BOR File");
		return borfile;
	}

	public List<BORFileApp> getAdjustmentScenario1(Session session,List<BorAdjustmentDBApp> borAdjustmentDbList,String borFileName){
		BorAdjustmentDBApp adjustmentRecord = borAdjustmentDbList.get(0);
		List<BORFileApp> borFileList = new ArrayList<BORFileApp>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date checkDate = adjustmentRecord.getCHK_DT();
		Date svcDate = adjustmentRecord.getSVC_DT();
		BORFileApp borAdjustmentFile = new BORFileApp();
		borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borAdjustmentFile.setFileName(borFileName);
		borAdjustmentFile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borAdjustmentFile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borAdjustmentFile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borAdjustmentFile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borAdjustmentFile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borAdjustmentFile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borAdjustmentFile.setClaimVersionNumber("01");
		borAdjustmentFile.setClaimAmount("-"+String.valueOf(adjustmentRecord.getCLM_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setClientPrice("-"+String.valueOf(adjustmentRecord.getCLI_PRC_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setBscRevenueAmount("-"+String.valueOf(adjustmentRecord.getBSC_RVNU_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCheckNumber(adjustmentRecord.getCHK_NBR());
		borAdjustmentFile.setCheckDate(todayDateFake);
		borAdjustmentFile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
		borAdjustmentFile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borAdjustmentFile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borAdjustmentFile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borAdjustmentFile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borAdjustmentFile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borAdjustmentFile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borAdjustmentFile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borAdjustmentFile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borAdjustmentFile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borAdjustmentFile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borAdjustmentFile.setBilledAmount("-"+String.valueOf(adjustmentRecord.getBIL_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setAllowedAmount("-"+String.valueOf(adjustmentRecord.getALLOW_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setDeductibleAmount("-"+String.valueOf(adjustmentRecord.getDED_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCoinsuranceAmount("-"+String.valueOf(adjustmentRecord.getCOINS_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCopayAmount("-"+String.valueOf(adjustmentRecord.getCOPAY_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borAdjustmentFile.setDiagnosisCodeType(" ");
		borAdjustmentFile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borAdjustmentFile.setHcpcs_id(" ");
		borAdjustmentFile.setClaimTransactionType("A");
		borFileList.add(borAdjustmentFile);
		logger.info("Adj Scenario 1 written in BOR File - A");
//		logger.info(borAdjustmentFile);
		BORFileApp borfile = new BORFileApp();
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(10, 29 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		AmountFieldsApp amountFields = HelperClassApp.getAmountFields(billedAmount.add(adjustmentRecord.getBIL_AMT()));
		String checkNumber11 = DatabaseValidationApp.generateCheckNumber(session);
		borfile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borfile.setFileName(borFileName);
		borfile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borfile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borfile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borfile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borfile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borfile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borfile.setClaimVersionNumber("00");
		borfile.setClaimAmount(amountFields.getClaimAmount());
		borfile.setClientPrice(amountFields.getClientPrice());
		borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borfile.setCheckNumber(checkNumber11);
		borfile.setCheckDate(todayDateFake);
		borfile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
		borfile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borfile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borfile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borfile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borfile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borfile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borfile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borfile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borfile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borfile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borfile.setBilledAmount(amountFields.getBilledAmount());
		borfile.setAllowedAmount(amountFields.getAllowedAmount());
		borfile.setDeductibleAmount(amountFields.getDeductible());
		borfile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borfile.setCopayAmount(amountFields.getCopay());
		borfile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borfile.setDiagnosisCodeType(" ");
		borfile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borfile.setHcpcs_id(" ");
		borfile.setClaimTransactionType("P");
//		logger.info(borfile);
		logger.info("Adj Scenario 1 written in BOR File - P");
		borFileList.add(borfile);
		
		return borFileList;
	}
	
	public List<BORFileApp> getAdjustmentScenario2(Session session,List<BorAdjustmentDBApp> borAdjustmentDbList,String borFileName){
		BorAdjustmentDBApp adjustmentRecord = borAdjustmentDbList.get(1);
		List<BORFileApp> borFileList = new ArrayList<BORFileApp>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date checkDate = adjustmentRecord.getCHK_DT();
		Date svcDate = adjustmentRecord.getSVC_DT();
		BORFileApp borAdjustmentFile = new BORFileApp();
		borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borAdjustmentFile.setFileName(borFileName);
		borAdjustmentFile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borAdjustmentFile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borAdjustmentFile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borAdjustmentFile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borAdjustmentFile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borAdjustmentFile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borAdjustmentFile.setClaimVersionNumber("01");
		borAdjustmentFile.setClaimAmount("-"+String.valueOf(adjustmentRecord.getCLM_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setClientPrice("-"+String.valueOf(adjustmentRecord.getCLI_PRC_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setBscRevenueAmount("-"+String.valueOf(adjustmentRecord.getBSC_RVNU_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCheckNumber(adjustmentRecord.getCHK_NBR());
		borAdjustmentFile.setCheckDate(todayDateFake);
		borAdjustmentFile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
		borAdjustmentFile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borAdjustmentFile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borAdjustmentFile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borAdjustmentFile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borAdjustmentFile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borAdjustmentFile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borAdjustmentFile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borAdjustmentFile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borAdjustmentFile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borAdjustmentFile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borAdjustmentFile.setBilledAmount("-"+String.valueOf(adjustmentRecord.getBIL_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setAllowedAmount("-"+String.valueOf(adjustmentRecord.getALLOW_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setDeductibleAmount("-"+String.valueOf(adjustmentRecord.getDED_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCoinsuranceAmount("-"+String.valueOf(adjustmentRecord.getCOINS_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCopayAmount("-"+String.valueOf(adjustmentRecord.getCOPAY_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borAdjustmentFile.setDiagnosisCodeType(" ");
		borAdjustmentFile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borAdjustmentFile.setHcpcs_id(" ");
		borAdjustmentFile.setClaimTransactionType("A");
		borFileList.add(borAdjustmentFile);
		logger.info("Adj Scenario 2 written in BOR File - A");
//		logger.info(borAdjustmentFile);
		BORFileApp borfile = new BORFileApp();
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(10, 29 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal amountPassed;
		if(billedAmount.compareTo(adjustmentRecord.getBIL_AMT())==1){
		 amountPassed = billedAmount.subtract(adjustmentRecord.getBIL_AMT());
		}else{
			amountPassed = adjustmentRecord.getBIL_AMT().subtract(billedAmount);
		}
		AmountFieldsApp amountFields = HelperClassApp.getAmountFields(amountPassed);
		String checkNumber11 = DatabaseValidationApp.generateCheckNumber(session);
		borfile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borfile.setFileName(borFileName);
		borfile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borfile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borfile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borfile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borfile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borfile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borfile.setClaimVersionNumber("00");
		borfile.setClaimAmount(amountFields.getClaimAmount());
		borfile.setClientPrice(amountFields.getClientPrice());
		borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borfile.setCheckNumber(checkNumber11);
		borfile.setCheckDate(todayDateFake);
		borfile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
		borfile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borfile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borfile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borfile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borfile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borfile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borfile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borfile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borfile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borfile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borfile.setBilledAmount(amountFields.getBilledAmount());
		borfile.setAllowedAmount(amountFields.getAllowedAmount());
		borfile.setDeductibleAmount(amountFields.getDeductible());
		borfile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borfile.setCopayAmount(amountFields.getCopay());
		borfile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borfile.setDiagnosisCodeType(" ");
		borfile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borfile.setHcpcs_id(" ");
		borfile.setClaimTransactionType("P");
//		logger.info(borfile);
		logger.info("Adj Scenario 2 written in BOR File - P");
		borFileList.add(borfile);
		
		
		return borFileList;
	}
	
	public List<BORFileApp> getAdjustmentScenario3(Session session,List<BorAdjustmentDBApp> borAdjustmentDbList,String borFileName){
		BorAdjustmentDBApp adjustmentRecord = borAdjustmentDbList.get(2);
		List<BORFileApp> borFileList = new ArrayList<BORFileApp>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date checkDate = adjustmentRecord.getCHK_DT();
		BigDecimal billedAmount = new BigDecimal(ThreadLocalRandom.current().nextInt(10	, 29 + 1)).setScale(2, BigDecimal.ROUND_HALF_UP);
		HelperClassApp  helperClass= new HelperClassApp();
		AmountFieldsApp amountFields = helperClass.getAdjustmentAmountFieldsScenario3(billedAmount, adjustmentRecord);
		Date svcDate = adjustmentRecord.getSVC_DT();
		BORFileApp borAdjustmentFile = new BORFileApp();
		borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borAdjustmentFile.setFileName(borFileName);
		borAdjustmentFile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borAdjustmentFile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borAdjustmentFile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borAdjustmentFile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borAdjustmentFile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borAdjustmentFile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borAdjustmentFile.setClaimVersionNumber("01");
		borAdjustmentFile.setClaimAmount("-"+amountFields.getClaimAmount());
		borAdjustmentFile.setClientPrice("-"+amountFields.getClientPrice());
		borAdjustmentFile.setBscRevenueAmount("-"+amountFields.getBscRevenueAmount());
		borAdjustmentFile.setCheckNumber(adjustmentRecord.getCHK_NBR());
		borAdjustmentFile.setCheckDate(todayDateFake);
		borAdjustmentFile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
		borAdjustmentFile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borAdjustmentFile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borAdjustmentFile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borAdjustmentFile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borAdjustmentFile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borAdjustmentFile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borAdjustmentFile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borAdjustmentFile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borAdjustmentFile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borAdjustmentFile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borAdjustmentFile.setBilledAmount("-"+amountFields.getBilledAmount());
		borAdjustmentFile.setAllowedAmount("-"+amountFields.getAllowedAmount());
		borAdjustmentFile.setDeductibleAmount("-"+amountFields.getDeductible());
		borAdjustmentFile.setCoinsuranceAmount("-"+amountFields.getCoInsurance());
		borAdjustmentFile.setCopayAmount("-"+amountFields.getCopay());
		borAdjustmentFile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borAdjustmentFile.setDiagnosisCodeType(" ");
		borAdjustmentFile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borAdjustmentFile.setHcpcs_id(" ");
		borAdjustmentFile.setClaimTransactionType("A");
		borFileList.add(borAdjustmentFile);
//		logger.info(borAdjustmentFile);
		logger.info("Adj Scenario 3 written in BOR File - A");
		return borFileList;
	}
	
	public List<BORFileApp> getAdjustmentScenario4(Session session,List<BorAdjustmentDBApp> borAdjustmentDbList,String borFileName){
		BorAdjustmentDBApp adjustmentRecord = borAdjustmentDbList.get(3);
		List<BORFileApp> borFileList = new ArrayList<BORFileApp>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		BigDecimal billedAmount = new BigDecimal(ThreadLocalRandom.current().nextInt(10	, 29 + 1)).setScale(2, BigDecimal.ROUND_HALF_UP);
		Date checkDate = adjustmentRecord.getCHK_DT();
		Date svcDate = adjustmentRecord.getSVC_DT();
		billedAmount.multiply(new BigDecimal(-1));
		BORFileApp borfile = new BORFileApp();
		AmountFieldsApp amountFields = HelperClassApp.getAdjustmentAmountFields(billedAmount);
//		String checkNumber11 = DatabaseValidation.generateCheckNumber(session);
		borfile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borfile.setFileName(borFileName);
		borfile.setVendorName("ARGS");
		borfile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borfile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borfile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borfile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borfile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borfile.setClaimVersionNumber("00");
		borfile.setClaimAmount(amountFields.getClaimAmount());
		borfile.setClientPrice(amountFields.getClientPrice());
		borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borfile.setCheckNumber(adjustmentRecord.getCHK_NBR());
		borfile.setCheckDate(todayDateFake);
		borfile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
		borfile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borfile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borfile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borfile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borfile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borfile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borfile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borfile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borfile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borfile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borfile.setBilledAmount(amountFields.getBilledAmount());
		borfile.setAllowedAmount(amountFields.getAllowedAmount());
		borfile.setDeductibleAmount(amountFields.getDeductible());
		borfile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borfile.setCopayAmount(amountFields.getCopay());
		borfile.setDiagnosisCode("9999");
		borfile.setDiagnosisCodeType(" ");
		borfile.setProcedureCode("99199");
		borfile.setHcpcs_id(" ");
		borfile.setClaimTransactionType("P");
//		logger.info(borfile);
		logger.info("Adj Scenario 4 written in BOR File - A");
		borFileList.add(borfile);
		
		return borFileList;
	}
	
	public List<BORFileApp> getAdjustmentScenario5(Session session,List<BorAdjustmentDBApp> borAdjustmentDbList,String borFileName){
		BorAdjustmentDBApp adjustmentRecord = borAdjustmentDbList.get(4);
		List<BORFileApp> borFileList = new ArrayList<BORFileApp>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date checkDate = adjustmentRecord.getCHK_DT();
//		Date svcDate = adjustmentRecord.getSVC_DT();
		BORFileApp borAdjustmentFile = new BORFileApp();
		borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borAdjustmentFile.setFileName(borFileName);
		borAdjustmentFile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borAdjustmentFile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borAdjustmentFile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borAdjustmentFile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borAdjustmentFile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borAdjustmentFile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borAdjustmentFile.setClaimVersionNumber("01");
		borAdjustmentFile.setClaimAmount("-"+String.valueOf(adjustmentRecord.getCLM_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setClientPrice("-"+String.valueOf(adjustmentRecord.getCLI_PRC_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setBscRevenueAmount("-"+String.valueOf(adjustmentRecord.getBSC_RVNU_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCheckNumber(adjustmentRecord.getCHK_NBR());
		borAdjustmentFile.setCheckDate(todayDateFake);
		borAdjustmentFile.setServiceDate(dateFormat.format(adjustmentRecord.getSVC_DT()).toUpperCase().trim());
		borAdjustmentFile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borAdjustmentFile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borAdjustmentFile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borAdjustmentFile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borAdjustmentFile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borAdjustmentFile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borAdjustmentFile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borAdjustmentFile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borAdjustmentFile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borAdjustmentFile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borAdjustmentFile.setBilledAmount("-"+String.valueOf(adjustmentRecord.getBIL_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setAllowedAmount("-"+String.valueOf(adjustmentRecord.getALLOW_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setDeductibleAmount("-"+String.valueOf(adjustmentRecord.getDED_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCoinsuranceAmount("-"+String.valueOf(adjustmentRecord.getCOINS_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setCopayAmount("-"+String.valueOf(adjustmentRecord.getCOPAY_AMT().setScale(2, BigDecimal.ROUND_HALF_UP)));
		borAdjustmentFile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borAdjustmentFile.setDiagnosisCodeType(" ");
		borAdjustmentFile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borAdjustmentFile.setHcpcs_id(" ");
		borAdjustmentFile.setClaimTransactionType("A");
		borFileList.add(borAdjustmentFile);
//		logger.info(borAdjustmentFile);
		logger.info("Adj Scenario 5 written in BOR File - A");
		return borFileList;
	}
	
	public List<BORFileApp> getAdjustmentScenario6(Session session,List<BorAdjustmentDBApp> borAdjustmentDbList,String borFileName){
		BorAdjustmentDBApp adjustmentRecord = borAdjustmentDbList.get(5);
		List<BORFileApp> borFileList = new ArrayList<BORFileApp>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date checkDate = adjustmentRecord.getCHK_DT();
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date newCheckDate = new Date(checkDate.getTime() - (2 * DAY_IN_MS));
		AmountFieldsApp amountFields = new AmountFieldsApp();
		amountFields = HelperClassApp.getAdjustmentAmountFields(adjustmentRecord.getBIL_AMT().add(new BigDecimal(3)));
//		Date svcDate = adjustmentRecord.getSVC_DT();
		BORFileApp borAdjustmentFile = new BORFileApp();
		borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borAdjustmentFile.setFileName(borFileName);
		borAdjustmentFile.setVendorName(adjustmentRecord.getVEND_NM().trim());
		borAdjustmentFile.setGroupNumber(adjustmentRecord.getGRP_NBR().trim());
		borAdjustmentFile.setSubgroupId(adjustmentRecord.getSBGRP_ID().trim());
		borAdjustmentFile.setSubscriberId(adjustmentRecord.getSBSCR_ID().trim());
		borAdjustmentFile.setPersonNumber(adjustmentRecord.getPERS_NBR().trim());
		borAdjustmentFile.setClaimNumber(adjustmentRecord.getCLM_NBR().trim());
		borAdjustmentFile.setClaimVersionNumber("00");
		borAdjustmentFile.setClaimAmount(amountFields.getClaimAmount());
		borAdjustmentFile.setClientPrice(amountFields.getClientPrice());
		borAdjustmentFile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
		borAdjustmentFile.setCheckNumber(adjustmentRecord.getCHK_NBR());
		borAdjustmentFile.setCheckDate(dateFormat.format(newCheckDate).toUpperCase().trim());
		borAdjustmentFile.setServiceDate(dateFormat.format(adjustmentRecord.getSVC_DT()).toUpperCase().trim());
		borAdjustmentFile.setPayeeId(adjustmentRecord.getPAYE_ID());
		borAdjustmentFile.setPayeeName(adjustmentRecord.getPAYE_NM());
		borAdjustmentFile.setPlanId(adjustmentRecord.getPLN_ID().trim());
		borAdjustmentFile.setProductId(adjustmentRecord.getPRDCT_ID().trim());
		borAdjustmentFile.setProductCategory(adjustmentRecord.getPRDCT_CATEG_CD().trim());
		borAdjustmentFile.setClassId(adjustmentRecord.getCLS_ID().trim());
		borAdjustmentFile.setProductBusinessCategory(adjustmentRecord.getPRDCT_BUS_CATEG_CD().trim());
		borAdjustmentFile.setProductValueCode(adjustmentRecord.getPRDCT_VAL1_CD().trim());
		borAdjustmentFile.setLineOfBusinessId(adjustmentRecord.getLOB_ID().trim());
		borAdjustmentFile.setLegalEntity(adjustmentRecord.getLGL_ENTY_CD().trim());
		borAdjustmentFile.setBilledAmount(amountFields.getBilledAmount());
		borAdjustmentFile.setAllowedAmount(amountFields.getAllowedAmount());
		borAdjustmentFile.setDeductibleAmount(amountFields.getDeductible());
		borAdjustmentFile.setCoinsuranceAmount(amountFields.getCoInsurance());
		borAdjustmentFile.setCopayAmount(amountFields.getCopay());
		borAdjustmentFile.setDiagnosisCode(adjustmentRecord.getDIAG_CD().trim());
		borAdjustmentFile.setDiagnosisCodeType(" ");
		borAdjustmentFile.setProcedureCode(adjustmentRecord.getPROC_CD().trim());
		borAdjustmentFile.setHcpcs_id(" ");
		borAdjustmentFile.setClaimTransactionType("P");
		borFileList.add(borAdjustmentFile);
//		logger.info(borAdjustmentFile);
		logger.info("Adj Scenario 6 written in BOR File - P");
		return borFileList;
	}
}

