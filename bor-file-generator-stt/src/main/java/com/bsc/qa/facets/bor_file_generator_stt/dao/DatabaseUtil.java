package com.bsc.qa.facets.bor_file_generator_stt.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.swing.border.Border;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bsc.qa.facets.bor_file_generator_stt.pojo.AmountFields;
import com.bsc.qa.facets.bor_file_generator_stt.pojo.BORDatabase;
import com.bsc.qa.facets.bor_file_generator_stt.pojo.BORFile;
import com.bsc.qa.facets.bor_file_generator_stt.pojo.BorAdjustmentDB;
import com.bsc.qa.facets.bor_file_generator_stt.pojo.Connection;
import com.bsc.qa.facets.bor_file_generator_stt.util.DatabaseValidation;
import com.bsc.qa.facets.bor_file_generator_stt.util.HelperClass;
import com.bsc.qa.facets.bor_file_generator_stt.util.HibernateUtil;

public class DatabaseUtil {

	List<BORFile>  borFileList ;
	List<BORDatabase> borDatabaseList;
	List<BorAdjustmentDB> borAdjustmentDbList;
	
	public List<BORDatabase> getBorFileListFromDB(Session session){
		borDatabaseList = new ArrayList<BORDatabase>();
		
		SQLQuery query = session.createSQLQuery("SELECT DISTINCT BORFILESUBLIST.GRPID,BORFILESUBLIST.SUBGRPID,BORFILESUBLIST.SUBID,BORFILESUBLIST.MEMSFX,BORFILESUBLIST.PLANID,BORFILESUBLIST.PRDID,BORFILESUBLIST.PRDCAT, BORFILESUBLIST.CLASSID,BORFILESUBLIST.PRDBUSCAT,BORFILESUBLIST.PRDVALCD,BORFILESUBLIST.LOBDID,BORFILESUBLIST.LOBDTYPE FROM  ( SELECT DISTINCT GRGR.GRGR_ID AS GRPID,SGSG.SGSG_ID AS SUBGRPID,SBSB.SBSB_ID AS SUBID,MEME.MEME_SFX AS MEMSFX,MEPE.CSPI_ID AS PLANID ,MEPE.PDPD_ID AS PRDID, MEPE.CSPD_CAT AS PRDCAT,MEPE.CSCS_ID AS CLASSID,PDDS.PDDS_MCTR_BCAT AS PRDBUSCAT,PDDS.PDDS_MCTR_VAL1 AS PRDVALCD,PDPD.LOBD_ID AS LOBDID,LOBD.LOBD_MCTR_TYPE AS LOBDTYPE ,ROW_NUMBER() OVER (PARTITION BY GRGR.GRGR_CK ORDER BY SBSB.SBSB_CK DESC) as ROW_NUM1  FROM FC_CMC_GRGR_GROUP GRGR INNER JOIN FC_CMC_SBSB_SUBSC SBSB ON GRGR.GRGR_CK=SBSB.GRGR_CK INNER JOIN FC_CMC_MEME_MEMBER MEME ON SBSB.SBSB_CK=MEME.SBSB_CK INNER JOIN FC_CMC_MEPE_PRCS_ELIG MEPE ON MEME.MEME_CK=MEPE.MEME_CK AND MEPE.MEPE_TERM_DT > SYSDATE and MEPE.MEPE_ELIG_IND ='Y' AND MEPE.CSPD_CAT='M'  INNER JOIN FC_CMC_PDPD_PRODUCT PDPD ON MEPE.PDPD_ID=PDPD.PDPD_ID AND PDPD.LOBD_ID='0006' INNER JOIN FC_CMC_PDDS_PROD_DESC PDDS ON PDPD.PDPD_ID=PDDS.PDPD_ID AND PDDS.PDDS_MCTR_BCAT NOT IN ('0025') INNER JOIN FC_CMC_LOBD_LINE_BUS LOBD ON PDPD.LOBD_ID=LOBD.LOBD_ID  INNER JOIN FC_CMC_PDBC_PROD_COMP PDBC ON PDPD.PDPD_ID=PDDS.PDPD_ID AND PDBC_TYPE='BSBS' INNER JOIN FC_CMC_BSDL_DETAILS BSDL ON PDBC.PDBC_PFX=BSDL.PDBC_PFX AND BSDL_TYPE IN ('PRBM','PRGM') INNER JOIN FC_CMC_SGSG_SUB_GROUP SGSG ON GRGR.GRGR_CK=SGSG.GRGR_CK WHERE GRGR.GRGR_CK IN ( SELECT DISTINCT AFEP.GRGR_CK FROM FC_CMC_AFEP_ENTY_PLAN AFEP  INNER JOIN FC_CMC_AFCP_CONT_PER AFCP ON AFEP.AFAI_CK=AFCP.AFAI_CK  INNER JOIN FC_CMC_AFAI_INDIC AFAI ON AFEP.AFAI_CK=AFAI.AFAI_CK AND AFAI_MCTR_TYPE='F001' WHERE AFCP.AFCP_PAID_END_DT > SYSDATE AND AFCP_INCUR_END_DT > SYSDATE FETCH FIRST 25 ROWS ONLY )) BORFILESUBLIST WHERE ROW_NUM1 = 1 FETCH FIRST 5 ROWS ONLY");
//		borDatabaseList = query.list();
		/*
		 * GRPID SUBGRPID SUBID MEMSFX PLANID PRDID PRDCAT CLASSID PRDBUSCAT
		 * PRDVALCD LOBDID LOBDTYPE
		 */
		List<Object[]> list = (List<Object[]>) query.list();
		for (Object[] objects : list) {
			BORDatabase borDB = new BORDatabase();
			borDB.setGrpId((String) objects[0]);
			borDB.setSubgrpId((String) objects[1]);
			borDB.setSubId((String) objects[2]);
			borDB.setMemSfx((BigDecimal) objects[3]);
			borDB.setPlanId((String) objects[4]);
			borDB.setPrdId((String) objects[5]);
			borDB.setPrdCat((String) objects[6]);
			borDB.setClassId((String) objects[7]);
			borDB.setPrdBusCat((String) objects[8]);
			borDB.setPrdValCD((String) objects[9]);
			borDB.setLobdId((String) objects[10]);
			borDB.setLobType((String) objects[11]);
			borDatabaseList.add(borDB);
			
		}

		return borDatabaseList;
	}
	
	public List<BorAdjustmentDB> getBorAdjustmentRecords(Session session){
		borAdjustmentDbList = new ArrayList<BorAdjustmentDB>();
		SQLQuery query = session.createSQLQuery("SELECT * FROM (SELECT DISTINCT FICT_CLM_ID, FIL_NM, VEND_NM, GRP_NBR, SBGRP_ID, SBSCR_ID, PERS_NBR, CLM_NBR, CLM_VER_NBR, CLM_AMT, CLI_PRC_AMT, BSC_RVNU_AMT, CHK_NBR, CHK_DT, SVC_DT, PAYE_ID, PAYE_NM,  PLN_ID, PRDCT_ID, PRDCT_CATEG_CD, CLS_ID,PRDCT_BUS_CATEG_CD, PRDCT_VAL1_CD, LOB_ID, LGL_ENTY_CD, BIL_AMT, ALLOW_AMT, DED_AMT, COINS_AMT, COPAY_AMT, DIAG_CD, DIAG_TYP_CD, PROC_CD, HCPCS_ID, CLM_TRANS_TYP_CD  ,ROW_NUMBER() OVER (PARTITION BY BLXP.GRGR_CK ORDER BY BLXP.SBSB_CK DESC) as ROW_NUM1   FROM FACETS_CUSTOM.ARGUS_GL_BOR_FIL_HIST BOR   INNER JOIN FC_CMC_BLXP_EXT_PYMT BLXP ON BOR.CLM_NBR=BLXP.BLXP_CLCL_ID WHERE BLXP_ACCT_CAT='DRUG'  AND VEND_NM='ARGS' AND CLM_VER_NBR='00' AND PRDCT_BUS_CATEG_CD NOT IN ('0025')  AND BLXP_STS='3' AND BLXP.LOBD_ID='0006' AND CLM_TRANS_TYP_CD='P')BORFILESUBADJLIST WHERE ROW_NUM1 = 1 FETCH FIRST 5 ROWS ONLY");
		List<Object[]> list = (List<Object[]>) query.list();
		for (Object[] objects : list) {
			BorAdjustmentDB borAdjustmentDB = new BorAdjustmentDB();
		
			borAdjustmentDB.setFICT_CLM_ID((String)objects[0]);
			borAdjustmentDB.setFIL_NM((String)objects[1]);
			borAdjustmentDB.setVEND_NM((String)objects[2]);
			borAdjustmentDB.setGRP_NBR((String)objects[3]);
			borAdjustmentDB.setSBGRP_ID((String)objects[4]);
			borAdjustmentDB.setSBSCR_ID((String)objects[5]);
			borAdjustmentDB.setPERS_NBR((String)objects[6]);
			borAdjustmentDB.setCLM_NBR((String)objects[7]);
			borAdjustmentDB.setCLM_VER_NBR((String)objects[8]);
			borAdjustmentDB.setCLM_AMT((BigDecimal)objects[9]);
			borAdjustmentDB.setCLI_PRC_AMT((BigDecimal)objects[10]);
			borAdjustmentDB.setBSC_RVNU_AMT((BigDecimal)objects[11]);
			borAdjustmentDB.setCHK_NBR((String)objects[12]);
			borAdjustmentDB.setCHK_DT((Date)objects[13]);
			borAdjustmentDB.setSVC_DT((Date)objects[14]);
			borAdjustmentDB.setPAYE_ID((String)objects[15]);
			borAdjustmentDB.setPAYE_NM((String)objects[16]);
			borAdjustmentDB.setPLN_ID((String)objects[17]);
			borAdjustmentDB.setPRDCT_ID((String)objects[18]);
			borAdjustmentDB.setPRDCT_CATEG_CD((String)objects[19]);
			borAdjustmentDB.setCLS_ID((String)objects[20]);
			borAdjustmentDB.setPRDCT_BUS_CATEG_CD((String)objects[21]);
			borAdjustmentDB.setPRDCT_VAL1_CD((String)objects[22]);
			borAdjustmentDB.setLOB_ID((String)objects[23]);
			borAdjustmentDB.setLGL_ENTY_CD((String)objects[24]);
			borAdjustmentDB.setBIL_AMT((BigDecimal)objects[25]);
			borAdjustmentDB.setALLOW_AMT((BigDecimal)objects[26]);
			borAdjustmentDB.setDED_AMT((BigDecimal)objects[27]);
			borAdjustmentDB.setCOINS_AMT((BigDecimal)objects[28]);
			borAdjustmentDB.setCOPAY_AMT((BigDecimal)objects[29]);
			borAdjustmentDB.setDIAG_CD((String)objects[30]);
			borAdjustmentDB.setDIAG_TYP_CD((String)objects[31]);
			borAdjustmentDB.setPROC_CD((String)objects[32]);
			borAdjustmentDB.setHCPCS_ID((String)objects[33]);
			borAdjustmentDB.setCLM_TRANS_TYP_CD((String)objects[34]);
			borAdjustmentDB.setROW_NUM1((BigDecimal)objects[35]);
			borAdjustmentDbList.add(borAdjustmentDB);
		}
		
		return borAdjustmentDbList;
	}
	
	public List<BORFile> getBorFileList(Session session,List<BORDatabase> borDatabaseList,List<BorAdjustmentDB> borAdjustmentDbList){
		borFileList = new ArrayList<BORFile>();
		String borFileName = HelperClass.getBorFileName();
		String todayDate = HelperClass.getTodaysDate();
		
		for (BORDatabase borDatabase: borDatabaseList) {
			BORFile borfile = new BORFile();
			String claimID = DatabaseValidation.generateUniqueClaimNumber(session);
			String checkNumber = DatabaseValidation.generateCheckNumber(session);
			String fictClmId = claimID.replace("R", "2");
			AmountFields amountFields = HelperClass.getAmountFields();
			String personNumber = "0"+String.valueOf(borDatabase.getMemSfx());
			borfile.setClaimId(fictClmId);
			borfile.setFileName(borFileName);
			borfile.setVendorName("ARGS");
			borfile.setGroupNumber(borDatabase.getGrpId());
			borfile.setSubgroupId(borDatabase.getSubgrpId());
			borfile.setSubscriberId(borDatabase.getSubId());
			borfile.setPersonNumber(personNumber);
			borfile.setClaimNumber(claimID);
			borfile.setClaimVersionNumber("00");
			borfile.setClaimAmount(amountFields.getClaimAmount());
			borfile.setClientPrice(amountFields.getClientPrice());
			borfile.setBscRevenueAmount(amountFields.getBscRevenueAmount());
			borfile.setCheckNumber(checkNumber);
			borfile.setCheckDate(todayDate);
			borfile.setServiceDate(todayDate);
			borfile.setPayeeId("0107777");
			borfile.setPayeeName("EXPO PHARMACY");
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
			System.out.println(borfile);
			borFileList.add(borfile);
			
		}
		
		for(int i=1; i<borAdjustmentDbList.size();i++){
			BorAdjustmentDB adjustmentRecord = borAdjustmentDbList.get(i);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
			Date checkDate = adjustmentRecord.getCHK_DT();
			Date svcDate = adjustmentRecord.getSVC_DT();
			BORFile borAdjustmentFile = new BORFile();
			borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
			borAdjustmentFile.setFileName(borFileName);
			borAdjustmentFile.setVendorName("ARGS");
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
			borAdjustmentFile.setCheckDate(dateFormat.format(checkDate).toUpperCase().trim());
			borAdjustmentFile.setServiceDate(dateFormat.format(svcDate).toUpperCase().trim());
			borAdjustmentFile.setPayeeId("0107777");
			borAdjustmentFile.setPayeeName("EXPO PHARMACY");
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
			borAdjustmentFile.setDiagnosisCode("9999");
			borAdjustmentFile.setDiagnosisCodeType(" ");
			borAdjustmentFile.setProcedureCode("99199");
			borAdjustmentFile.setHcpcs_id(" ");
			borAdjustmentFile.setClaimTransactionType("A");
			System.out.println(borAdjustmentFile);
			borFileList.add(borAdjustmentFile);
			
			BORFile borfile = new BORFile();
			AmountFields amountFields = HelperClass.getAmountFields();
			String checkNumber = DatabaseValidation.generateCheckNumber(session);
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
			borfile.setCheckNumber(checkNumber);
			borfile.setCheckDate(todayDate);
			borfile.setServiceDate(todayDate);
			borfile.setPayeeId("0107777");
			borfile.setPayeeName("EXPO PHARMACY");
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
			System.out.println(borfile);
			borFileList.add(borfile);
			
		}
		
		BorAdjustmentDB adjustmentRecord = borAdjustmentDbList.get(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date checkDate = adjustmentRecord.getCHK_DT();
		Date svcDate = adjustmentRecord.getSVC_DT();
		BORFile borAdjustmentFile = new BORFile();
		borAdjustmentFile.setClaimId(adjustmentRecord.getFICT_CLM_ID().trim());
		borAdjustmentFile.setFileName(borFileName);
		borAdjustmentFile.setVendorName("ARGS");
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
		borAdjustmentFile.setCheckDate(dateFormat.format(checkDate).trim());
		borAdjustmentFile.setServiceDate(dateFormat.format(svcDate).trim());
		borAdjustmentFile.setPayeeId("0107777");
		borAdjustmentFile.setPayeeName("EXPO PHARMACY");
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
		borAdjustmentFile.setDiagnosisCode("9999");
		borAdjustmentFile.setDiagnosisCodeType(" ");
		borAdjustmentFile.setProcedureCode("99199");
		borAdjustmentFile.setHcpcs_id(" ");
		borAdjustmentFile.setClaimTransactionType("A");
		System.out.println(borAdjustmentFile);
		borFileList.add(borAdjustmentFile);
		
		return borFileList;
	}
	
}
