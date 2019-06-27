package com.bsc.qa.facets.afa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Query;

import org.apache.commons.collections4.map.HashedMap;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bsc.qa.facets.afa.excel_reader.AfaInvoiceReportReader;
import com.bsc.qa.facets.afa.pojo.ClaimsActivitySummary;
import com.bsc.qa.facets.afa.pojo.FrontPage;
import com.bsc.qa.facets.afa.pojo.MemberDetails;
import com.bsc.qa.facets.afa.pojo.PlanDetails;
import com.bsc.qa.facets.afa.pojo.ShieldSaving;
import com.bsc.qa.facets.afa.test.AfaInvoiceReportValidationTest;

public class DatabaseUtil {
	/*
	 * GROUPNAME GROUPADDRESS ATTENTION GROUPBILLINGID FUNDINGPERIOD BILDUEDATE
	 * INVOICENO INVOICEDATE CURRENTPERIODCLAIMS BALANCEFORWARD
	 * TOTALDUEFORCLAIMSREIMBURSEMENT BSCACCOUNTANTNAME PHONE FAX EMAIL
	 */
	private Map<String,FrontPage> frontPageDBMap;
	private Map<String,List<ClaimsActivitySummary>> claimsActivitySummaryDBMap;
	private Map<String,List<MemberDetails>> memberDetailsDBMap;
	private List<PlanDetails> planDetailsDBList;
	private Map<String,List<ShieldSaving>> shieldSavingDBMap;
	private Set<String> inputInvoiceNoSet = AfaInvoiceReportReader.getInputSet();
	private Session session = AfaInvoiceReportValidationTest.session;

	public Map<String,FrontPage> getFrontPageData() {
		frontPageDBMap = new HashedMap<String, FrontPage>();
		for (String invoiceId : inputInvoiceNoSet) {
			SQLQuery query = session
					.createSQLQuery("SELECT DISTINCT GROUPNAME,GROUPADDRESS,ATTENTION,GROUPBILLINGID,FUNDINGPERIOD,BILDUEDATE, INVOICENO,INVOICEDATE, ('$'||to_char(NVL(CLAIMEXPENSEAMOUNT,0)+NVL(StopLossAdvancedFunding,0)+NVL(HRA,0)+ NVL(BelowStoplossAmount,0)+ NVL(ASOAMOUNT,0)+ NVL(Extracontractualamount,0)+ NVL(HSAAMOUNT,0)+ NVL(FEEDSICOUNTAMOUNT,0))) AS CURRENTPERIODCLAIMS, ('$'||to_char(NVL(BALANCEFORWARD,0))) AS BALANCEFORWARD, ('$'||to_char(NVL(CLAIMEXPENSEAMOUNT,0)+NVL(StopLossAdvancedFunding,0)+NVL(HRA,0)+NVL(BelowStoplossAmount,0)+ NVL(ASOAMOUNT,0)+ NVL(Extracontractualamount,0)+ NVL(HSAAMOUNT,0)+ NVL(FEEDSICOUNTAMOUNT,0)+NVL(BALANCEFORWARD,0))) AS TOTALDUEFORCLAIMSREIMBURSEMENT, BSCACCOUNTANTNAME,NVL(PHONE,' ') AS PHONE,FAX,EMAIL FROM (SELECT DISTINCT AFAI.AFAI_NAME AS GROUPNAME, CASE WHEN AFAD_ADDR2=' ' THEN (AFAD_ADDR1||'  '||AFAD_CITY||','||' '||AFAD_STATE||' '||AFAD_ZIP) ELSE (AFAD_ADDR1||'  '||AFAD_ADDR2||AFAD_CITY||','||' '||AFAD_STATE||' '||AFAD_ZIP) END AS GROUPADDRESS, AFAD_ADDR3 AS ATTENTION, AFAI.AFAI_ID AS GROUPBILLINGID, ((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS FUNDINGPERIOD, TO_CHAR(INID.BLBL_DUE_DT,'MM/DD/YYYY') as BILDUEDATE, INID.BLIV_ID AS INVOICENO, TO_CHAR(INID.BLIV_CREATE_DTM,'MM/DD/YYYY') AS INVOICEDATE, SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID) AS CLAIMEXPENSEAMOUNT, ((-1)*(SUM(INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID))) AS StopLossAdvancedFunding, SUM(INPS.BLPS_HSA_AMT) OVER (PARTITION BY INPS.BLIV_ID) AS HRA, A.BelowStoplossAmount, A.ASOAMOUNT,  A.Extracontractualamount, A.HSAAMOUNT,  A.FEEDSICOUNTAMOUNT, INID.INID_OUTSTAND_BAL AS BALANCEFORWARD, BLAD_ADDR3 AS BSCACCOUNTANTNAME, CASE WHEN BLAD_PHONE<>' ' THEN ('('||(SUBSTR(BLAD_PHONE,1,3))||')'||' '||(SUBSTR(BLAD_PHONE,4,3))||'-'||(SUBSTR(BLAD_PHONE,7,4))) END AS PHONE, CASE WHEN BLAD_FAX<>' ' THEN ('('||(SUBSTR(BLAD_FAX,1,3))||')'||' '||(SUBSTR(BLAD_FAX,4,3))||'-'||(SUBSTR(BLAD_FAX,7,4))) END AS FAX,BLAD_EMAIL AS EMAIL FROM FC_CMC_AFAI_INDIC AFAI INNER JOIN FC_CMC_AFAD_ADDR AFAD ON AFAI.AFAI_CK=AFAD.AFAI_CK INNER JOIN FC_CDS_INID_INVOICE INID ON AFAI.AFAI_CK=INID.AFAI_CK INNER JOIN FC_CDS_INPS_PYMT_DTL INPS ON INID.BLIV_ID=INPS.BLIV_ID  INNER JOIN FC_CMC_BLEI_ENTY_INFO BLEI ON BLEI.BLEI_BILL_LEVEL_CK=INID.AFAI_CK INNER JOIN FC_CMC_BLAD_ADDR BLAD ON BLEI.BLEI_CK=BLAD.BLEI_CK LEFT OUTER JOIN  (SELECT DISTINCT INDI.BLIV_ID, SUM(INDI.BLDI_SSL_AMT) AS BelowStoplossAmount, SUM(INDI.BLDI_ASO_AMT) AS ASOAMOUNT, SUM(INDI.BLDI_EXTRACONT_AMT) AS Extracontractualamount, SUM(INDI.BLDI_HSA_AMT) AS HSAAMOUNT, SUM(INDI.BLDI_FEE_DISC_AMT) AS FEEDSICOUNTAMOUNT FROM FC_CDS_INDI_DISCRETN INDI group by INDI.BLIV_ID  )A ON A.BLIV_ID=INID.BLIV_ID WHERE INID.BLIV_ID in ('"
							+ invoiceId + "'))");
			FrontPage frontPage = new FrontPage();
			List<Object[]> resultList = new ArrayList<Object[]>();
			resultList = query.list();

			for (Object[] frontPageData : resultList) {
				frontPage.setGroupName((String) frontPageData[0]);
				frontPage.setGroupAddress((String) frontPageData[1]);
				frontPage.setAttention((String) frontPageData[2]);
				frontPage.setGroupBillingId((String) frontPageData[3]);
				frontPage.setFundingPeriod((String) frontPageData[4]);
				frontPage.setBillDueDate((String) frontPageData[5]);
				frontPage.setInvoiceNo((String) frontPageData[6]);
				frontPage.setInvoiceDate((String) frontPageData[7]);
				frontPage.setCurrentPeriodClaims((String) frontPageData[8]);
				frontPage.setBalanceForward((String) frontPageData[9]);
				frontPage.setTotalDueForClaimsReimbursement((String) frontPageData[10]);
				frontPage.setBscAccountantName((String) frontPageData[11]);
				frontPage.setPhone((String) frontPageData[12]);
				frontPage.setFax((String) frontPageData[13]);
				frontPage.setEmail((String) frontPageData[14]);
				
				if(frontPage.getBscAccountantName()==null || frontPage.getBscAccountantName().equalsIgnoreCase(" ")){
					frontPage.setBscAccountantName("");
				}
				if(frontPage.getAttention()==null || frontPage.getAttention().equalsIgnoreCase(" ")){
					frontPage.setAttention("");
				}
				if(frontPage.getFax()==null || frontPage.getFax().equalsIgnoreCase(" ")){
					frontPage.setFax("");
				}
				if(frontPage.getPhone()==null || frontPage.getPhone().equalsIgnoreCase(" ")){
					frontPage.setPhone("");
				}
				if(frontPage.getEmail()==null || frontPage.getEmail().equalsIgnoreCase(" ")){
					frontPage.setEmail("");
				}
				
			}
			frontPageDBMap.put(invoiceId,frontPage);
			System.out.println("DataBase Row: "+frontPage);
		}
		return frontPageDBMap;

	}

	public Map<String,List<ClaimsActivitySummary>> getClaimsActivitySummary() {
		claimsActivitySummaryDBMap = new HashMap<String, List<ClaimsActivitySummary>>();
		for(String invoiceId:inputInvoiceNoSet){
		SQLQuery query = session
				.createSQLQuery("SELECT distinct ClaimPaymentPeriod,INVOICEDATE,GROUPNAME, GROUPBILLINGID, CLAIMSCYCLE, BILDUEDATE,INVOICENO, BILLINGCATEGORY,   ('$'||(SUM(NVL(MedicalAmount,0))-SUM(NVL(Interestamount,0))-SUM(NVL(BlueCardAccessFees,0)))) AS MEDICAL,  	 ('$'||SUM(NVL(Costcontainment,0))) As Costcontainment,  	 ('$'||SUM(NVL(Interestamount,0))) As Interest,  	 ('$'||SUM(NVL(DentalAmount,0))) As Dental, ('$'||SUM(NVL(PharmacyAmount,0))) AS Pharmacy,('$'||SUM(NVL(BlueCardAccessFees,0))) AS BlueCardAccessFees, ('$'||SUM(NVL(StopLossAdvancedFunding,0))) AS StopLossAdvancedFunding,  	 ('$'||SUM(NVL(HRA,0))) AS HEALTHREIMBURSEMENTACCOUNT, 	 ('$'||(NVL(SubTotalClaimsActivity,0))) AS SubTotalClaimsActivity,  	 ('$'||(NVL(Total,0))) AS Total  	 FROM  	 (  	 select distinct  	 ((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS ClaimPaymentPeriod,  	 TO_CHAR(INID.BLIV_CREATE_DTM,'MM/DD/YYYY') AS INVOICEDATE,  	 AFAI.AFAI_NAME AS GROUPNAME,  	 AFAI.AFAI_ID AS GROUPBILLINGID,  	 ((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS CLAIMSCYCLE, 	 TO_CHAR(INID.BLBL_DUE_DT,'MM/DD/YYYY') as BILDUEDATE,  	 INID.BLIV_ID AS INVOICENO,  	 CASE WHEN GRPATRB.ATRB_VAL_TXT IS NULL THEN 'XXX' ELSE GRPATRB.ATRB_VAL_TXT END AS BILLINGCATEGORY,  	 CASE WHEN INPS.BLPS_SOURCE ='1' OR BLXP.BLXP_ACCT_CAT IN ('HRAA','HRAF','COBA','COBF')  THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_AMT,GRPATRB.ATRB_VAL_TXT)) END AS MedicalAmount,  	 CASE WHEN (BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF') AND INPS.BLPS_SOURCE='4') THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,INPS.BLPS_SOURCE,GRPATRB.ATRB_VAL_TXT)) END Costcontainment,  	 (SUM(INPS.BLPS_INT_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT)) AS Interestamount,  	 CASE WHEN BLXP.BLXP_ACCT_CAT IN ('DENT','DENF') THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,BLXP.CSPD_CAT,INPS.BLPS_SOURCE)) END DentalAmount,  	 CASE WHEN BLXP.BLXP_ACCT_CAT IN ('DRUG','DRUF') THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,BLXP.CSPD_CAT,INPS.BLPS_SOURCE)) END PharmacyAmount,  	 CASE WHEN  INPS.BLPS_SOURCE ='1' THEN (SUM(INPS.BLPS_ITS_FEE) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT)) END AS BlueCardAccessFees,  	 ((-1)*(SUM(INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_SSL_EXC_AMT,GRPATRB.ATRB_VAL_TXT))) AS StopLossAdvancedFunding,  	 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('HRAA','HRAF')) THEN (SUM(INPS.BLPS_HSA_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_SOURCE,GRPATRB.ATRB_VAL_TXT)) END AS HRA,   	 (SUM(INPS.BLPS_AMT+INPS.BLPS_HSA_AMT+INPS.BLPS_ITS_FEE-INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT)) AS SubTotalClaimsActivity,  	 (SUM(INPS.BLPS_AMT+INPS.BLPS_HSA_AMT+INPS.BLPS_ITS_FEE-INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID)) AS Total  	 FROM FC_CMC_AFAI_INDIC AFAI  	 INNER JOIN FC_CDS_INID_INVOICE INID ON AFAI.AFAI_CK=INID.AFAI_CK  	 INNER JOIN FC_CDS_INPS_PYMT_DTL INPS ON INID.BLIV_ID=INPS.BLIV_ID  	 LEFT OUTER JOIN FC_CMC_BLXP_EXT_PYMT BLXP ON BLXP.BLXP_CLCL_ID=INPS.CLCL_ID  	 LEFT OUTER JOIN FC_CMC_GRGR_GROUP GRGR ON INPS.GRGR_ID=GRGR.GRGR_ID  	 LEFT OUTER JOIN CU_CDM_GRP_STRUCT_MAIN GRPSTRT ON INPS.GRGR_CK=GRPSTRT.GRP_CK AND GRPSTRT.PLN_TERM_DT>INPS.BLPS_PAID_DT  AND INPS.CSPI_ID=GRPSTRT.PLN_ID  	 LEFT OUTER JOIN CU_CDM_GRP_STRUCT_ATRB GRPATRB ON GRPSTRT.GRP_STRUCT_CK=GRPATRB.GRP_STRUCT_CK AND GRPATRB.ATRB_NM='CBC'  	 LEFT OUTER JOIN CU_AFA_HRA_FALLOUT_XWALK XWALK ON INPS.CLCL_ID=XWALK.NEW_CLM_ID AND XWALK.CLM_ACCT_CATEG_CD=BLXP.BLXP_ACCT_CAT  	 WHERE INID.BLIV_ID in ('"
						+ invoiceId
						+ "')) Group by ClaimPaymentPeriod,INVOICEDATE,GROUPNAME, GROUPBILLINGID, CLAIMSCYCLE, BILDUEDATE,INVOICENO,BILLINGCATEGORY,SubTotalClaimsActivity,Total Order by BILLINGCATEGORY");
		
		List<Object[]> resultList = new ArrayList<Object[]>();
		resultList = query.list();
		List<ClaimsActivitySummary> claimsActivityList = new ArrayList<ClaimsActivitySummary>();
		
		for (Object[] claimsActivitySummaryData : resultList) {
			ClaimsActivitySummary claimsActivitySummary = new ClaimsActivitySummary();
			claimsActivitySummary
					.setClaimPaymentPeriod((String) claimsActivitySummaryData[0]);
			claimsActivitySummary
					.setInvoiceDate((String) claimsActivitySummaryData[1]);
			claimsActivitySummary
					.setGroupName((String) claimsActivitySummaryData[2]);
			claimsActivitySummary
					.setGroupBillingId((String) claimsActivitySummaryData[3]);
			claimsActivitySummary
					.setClaimsCycle((String) claimsActivitySummaryData[4]);
			claimsActivitySummary
					.setBillDueDate((String) claimsActivitySummaryData[5]);
			claimsActivitySummary
					.setInvoiceNo((String) claimsActivitySummaryData[6]);
			claimsActivitySummary
					.setBillingCategory((String) claimsActivitySummaryData[7]);
			claimsActivitySummary
					.setMedical((String) claimsActivitySummaryData[8]);
			claimsActivitySummary
					.setCostContainment((String) claimsActivitySummaryData[9]);
			claimsActivitySummary
					.setInterest((String) claimsActivitySummaryData[10]);
			claimsActivitySummary
					.setDental((String) claimsActivitySummaryData[11]);
			claimsActivitySummary
					.setPharmacy((String) claimsActivitySummaryData[12]);
			claimsActivitySummary
					.setBlueCardAccessFees((String) claimsActivitySummaryData[13]);
			claimsActivitySummary
					.setStopLossAdvancedFunding((String) claimsActivitySummaryData[14]);
			claimsActivitySummary
					.setHealthReimbursementAccount((String) claimsActivitySummaryData[15]);
			claimsActivitySummary
					.setSubTotalClaimsActivity((String) claimsActivitySummaryData[16]);
			claimsActivitySummary
					.setTotal((String) claimsActivitySummaryData[17]);
			if(!claimsActivitySummary.getSubTotalClaimsActivity().trim().equalsIgnoreCase("$0")){
				System.out.println("DataBase Row: "+claimsActivitySummary);
				claimsActivityList.add(claimsActivitySummary);
			}
		}
			claimsActivitySummaryDBMap.put(invoiceId, claimsActivityList);
		}
		return claimsActivitySummaryDBMap;
	}

	public Map<String,List<MemberDetails>> getMemberDetails() {
		memberDetailsDBMap = new HashMap<String, List<MemberDetails>>();
		for(String invoiceId:inputInvoiceNoSet){
		SQLQuery query = session
				.createSQLQuery("SELECT DISTINCT GROUPNAME,GROUPBILLINGID,CLAIMSCYCLE,BILDUEDATE,INVOICENO,GROUPIDNAME,BILLINGCATEGORY, PLANID,ClASSID,SUBSCRIBERID,SSN,SUBSCRIBERNAME,PATIENT_NAME,RELATIONSHIP,DEPT,CLAIMID,    				 NVL(CHECKNUMBER,0) AS CHECKNUMBER,      			 PAIDDATE,FROMDATE,TODATE,PAYEENAME,PAYEEID,COVERAGE,  				 ('$'||(SUM(NVL(DEDUCTIBLE,0)))) AS DEDUCTIBLE,('$'||(SUM(NVL(COINSURANCE,0)))) AS COINSURANCE,('$'||(SUM(NVL(COPAY,0)))) AS COPAY, 				 ('$'||(SUM(NVL(MedicalAmount,0))-SUM(NVL(Interestamount,0))-SUM(NVL(BlueCardAccessFees,0)))) AS MEDICAL ,  				 ('$'||SUM(NVL(Costcontainment,0))) As Costcontainment,   				 ('$'||SUM(NVL(Interestamount,0))) As Interest,  				 ('$'||SUM(NVL(DentalAmount,0))) As Dental, ('$'||SUM(NVL(PharmacyAmount,0))) AS Pharmacy,('$'||SUM(NVL(BlueCardAccessFees,0))) AS BlueCard,('$'||SUM(NVL(StopLossAdvancedFunding,0))) AS StopLoss,  				 ('$'||SUM(NVL(HRA,0))) AS HRA,  				 ('$'||SUM(NVL(TotalClaims,0))) AS TotalPaid      			 FROM      			 (     			 SELECT DISTINCT      			 AFAI.AFAI_NAME AS GROUPNAME,  				 AFAI.AFAI_ID AS GROUPBILLINGID,  				 ((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS CLAIMSCYCLE,  				 TO_CHAR(INID.BLBL_DUE_DT,'MM/DD/YYYY') as BILDUEDATE,  				 INID.BLIV_ID AS INVOICENO,  				 INPS.GRGR_ID ||'/'|| GRGR.GRGR_NAME AS GROUPIDNAME,  				 CASE WHEN GRPATRB.ATRB_VAL_TXT IS NULL THEN 'XXX' ELSE GRPATRB.ATRB_VAL_TXT END AS BILLINGCATEGORY,  				 INPS.CSPI_ID AS PLANID,  				 CSPI.CSCS_ID AS ClASSID,  				 INPS.SBSB_ID AS SUBSCRIBERID,  				 MEME.MEME_SSN AS SSN,  				 ((INPS.SBSB_LAST_NAME)||' '||(INPS.SBSB_FIRST_NAME)) AS SUBSCRIBERNAME,  				 ((MEME.MEME_LAST_NAME)||' '||(MEME.MEME_FIRST_NAME)) AS PATIENT_NAME,  				 CASE WHEN INPS.MEME_REL='M' THEN 'SELF'           		 WHEN INPS.MEME_REL IN ('D','S') THEN 'DEPENDENT'       	   		 WHEN INPS.MEME_REL IN ('H','W') THEN 'SPOUSE'           		 ELSE 'OTHER' END AS RELATIONSHIP,  				 NVL(SBEM.SBEM_DEPT,' ') AS DEPT,  				 CASE WHEN (BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF','MEDF','HRAF','HRAA','CCMF','MEDR','COBA','COBF') AND INPS.BLPS_SOURCE='4') THEN ROW_TABLE1.OLDCLMID           		 ELSE INPS.CLCL_ID END AS CLAIMID,   				 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DRUG')) THEN TO_NUMBER(PMTHIST.CHK_NBR_TXT)           		 WHEN (BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF','MEDF','HRAF','HRAA','CCMF','MEDR','COBA','COBF') AND INPS.BLPS_SOURCE='4') THEN ROW_TABLE1.EXTCKCKNO           		 ELSE  ROW_TABLE.CKCKNO END AS CHECKNUMBER,  				 (TO_CHAR(INPS.BLPS_PAID_DT,'MM/DD/YYYY')) as PAIDDATE,    				 MIN(TO_CHAR(INPS.BLPS_FROM_DT,'MM/DD/YYYY')) OVER (PARTITION BY INPS.BLIV_ID) AS FROMDATE,  			 MAX(TO_CHAR(INPS.BLPS_TO_DT,'MM/DD/YYYY')) OVER (PARTITION BY INPS.BLIV_ID) AS TODATE,  	 			 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DRUG')) THEN PMTHIST.PAYE_NM           		 WHEN (ROW_TABLE.PAYEEIND='P' OR ROW_TABLE1.EXTPAYEEIND ='P') THEN ROW_TABLE.PRPRNAME           		 WHEN (ROW_TABLE.PAYEEIND='S' OR ROW_TABLE1.EXTPAYEEIND ='S') THEN ((INPS.SBSB_LAST_NAME)||' '||(INPS.SBSB_FIRST_NAME))           		 ELSE 'NOT ASSIGNED' END AS PAYEENAME,                        			 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DRUG')) THEN PMTHIST.PAYE_ID           		 WHEN (ROW_TABLE.PAYEEIND='P' OR ROW_TABLE1.EXTPAYEEIND ='P') THEN ROW_TABLE.PAYEEID      	   		 WHEN (ROW_TABLE.PAYEEIND='S' OR ROW_TABLE1.EXTPAYEEIND ='S') THEN INPS.SBSB_ID      	   		 ELSE ' ' END AS PAYEEID,     			 CASE WHEN (INPS.BLPS_SOURCE ='1'  OR BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF','MEDF','HRAF','HRAA','CCMF','MEDR','COBA','COBF') AND INPS.BLPS_SOURCE='4') THEN 'HEALTH'      	  		 WHEN BLXP.BLXP_ACCT_CAT IN ('DRUG','DRUF') THEN 'PHARMACY'           		 WHEN BLXP.BLXP_ACCT_CAT IN ('DENT','DENF') THEN 'DENTAL'      	   		 ELSE ' ' END AS COVERAGE,      			 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DRUG')) THEN PMTHIST.DED_AMT           			   WHEN  INPS.BLPS_SOURCE ='1'  THEN ((SUM(CASE WHEN ROW_TABLE.CDMLCLMID = INPS.CLCL_ID THEN ROW_TABLE.DEDAMT ELSE 0 END) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT))-                                              (SUM(CASE WHEN ROW_TABLE.CDMLCLMID = ROW_TABLE.ADJCLMID THEN ROW_TABLE.DEDAMT ELSE 0 END) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)))                                               END AS DEDUCTIBLE  ,    			 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DRUG')) THEN PMTHIST.COINS_AMT  			   		   WHEN  INPS.BLPS_SOURCE ='1' THEN ((SUM(CASE WHEN ROW_TABLE.CDMLCLMID = INPS.CLCL_ID THEN  ROW_TABLE.COINSAMT ELSE 0 END) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)) -                                             (SUM(CASE WHEN ROW_TABLE.CDMLCLMID =ROW_TABLE.ADJCLMID THEN ROW_TABLE.COINSAMT ELSE 0 END) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)))                                            END AS COINSURANCE,      			 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DRUG')) THEN PMTHIST.COPAY_AMT           			   WHEN  INPS.BLPS_SOURCE ='1' THEN ((SUM(CASE WHEN ROW_TABLE.CDMLCLMID = INPS.CLCL_ID THEN ROW_TABLE.COPAYAMT ELSE 0 END) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)) -                                             (SUM(CASE WHEN ROW_TABLE.CDMLCLMID = ROW_TABLE.ADJCLMID THEN  ROW_TABLE.COPAYAMT ELSE 0 END) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)))                                            END AS COPAY,  				 CASE WHEN (INPS.BLPS_SOURCE ='1' OR BLXP.BLXP_ACCT_CAT IN ('COBA','COBF','MEDF','MEDR','CCMD')) THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE)) END AS MedicalAmount,  				 CASE WHEN (BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF') AND INPS.BLPS_SOURCE='4') THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE)) END AS Costcontainment,      			 (SUM(INPS.BLPS_INT_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)) AS Interestamount,  				 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DENF')) THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE)) END AS DentalAmount,  				 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DRUG','DRUF')) THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE)) END AS PharmacyAmount,  			 CASE WHEN INPS.BLPS_SOURCE ='1' THEN (SUM(INPS.BLPS_ITS_FEE) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE)) END AS BlueCardAccessFees,  				 ((-1)*(SUM(INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE))) AS StopLossAdvancedFunding,  				 CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('HRAA','HRAF')) THEN (SUM(INPS.BLPS_HSA_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.CLCL_ID,GRPATRB.ATRB_VAL_TXT)) END AS HRA,  				 (SUM(INPS.BLPS_AMT+INPS.BLPS_HSA_AMT+INPS.BLPS_ITS_FEE-INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,INPS.CLCL_ID,INPS.BLPS_SOURCE)) AS TotalClaims      			 FROM FC_CMC_AFAI_INDIC AFAI  				 INNER JOIN FC_CDS_INID_INVOICE INID ON AFAI.AFAI_CK=INID.AFAI_CK  			 INNER JOIN FC_CDS_INPS_PYMT_DTL INPS ON INID.BLIV_ID=INPS.BLIV_ID      			 LEFT OUTER JOIN FC_CMC_MEME_MEMBER MEME ON INPS.SBSB_CK=MEME.SBSB_CK      			 LEFT OUTER JOIN FC_CMC_BLXP_EXT_PYMT BLXP ON BLXP.BLXP_CLCL_ID=INPS.CLCL_ID   				 LEFT OUTER JOIN FC_CMC_SBEM_EMPLOY SBEM ON INPS.SBSB_CK=SBEM.SBSB_CK AND SBEM.SBEM_EFF_DT = (SELECT MAX(B.SBEM_EFF_DT) FROM FACETS.CMC_SBEM_EMPLOY B WHERE B.SBSB_CK = SBEM.SBSB_CK AND INPS.BLPS_PAID_DT BETWEEN B.SBEM_EFF_DT AND B.SBEM_TERM_DT)  				 LEFT OUTER JOIN FC_CMC_GRGR_GROUP GRGR ON INPS.GRGR_ID=GRGR.GRGR_ID  				 LEFT OUTER JOIN FC_CMC_CSPI_CS_PLAN CSPI ON GRGR.GRGR_CK=CSPI.GRGR_CK AND CSPI.CSPI_ID=INPS.CSPI_ID  				 LEFT OUTER JOIN CU_CDM_GRP_STRUCT_MAIN GRPSTRT ON INPS.GRGR_CK=GRPSTRT.GRP_CK AND INPS.CSPI_ID=GRPSTRT.PLN_ID AND CSPI.CSCS_ID=GRPSTRT.CLS_ID  				 LEFT OUTER JOIN CU_CDM_GRP_STRUCT_ATRB GRPATRB ON GRPSTRT.GRP_STRUCT_CK=GRPATRB.GRP_STRUCT_CK AND GRPATRB.ATRB_NM='CBC'  				 LEFT OUTER JOIN CU_ARGUS_DBP_AFA_PMT_HIST PMTHIST ON BLXP.BLXP_CLCL_ID=PMTHIST.CLM_ID    			 LEFT OUTER JOIN (SELECT DISTINCT SUM(DED_AMT) AS DEDAMT, SUM(COINS_AMT) AS COINSAMT,SUM(COPAY_AMT) AS COPAYAMT , CLCLCLMID,ADJCLMID,CDMLCLMID,CLCKCLMID,CKCKNO,PAYEEIND,PRPRNAME,PAYEEID  	                   			 FROM (SELECT distinct                       			 CLCL.CLCL_ID AS CLCLCLMID,CLCL.CLCL_ID_ADJ_FROM AS ADJCLMID, CDML.CLCL_ID AS CDMLCLMID,CLCK.CLCL_ID AS CLCKCLMID, CDML.CDML_DED_AMT AS DED_AMT,CDML.CDML_COINS_AMT AS COINS_AMT,CDML.CDML_COPAY_AMT AS COPAY_AMT,CKCK.CKCK_CK_NO AS CKCKNO,                       			 CLCK.CLCK_PAYEE_IND AS PAYEEIND,PRPR.PRPR_NAME AS PRPRNAME, CLCK.CLCK_PAYEE_PR_ID AS PAYEEID,                       			 ROW_NUMBER() OVER (PARTITION BY CDML.CLCL_ID,CDML.CDML_DED_AMT,CDML.CDML_COINS_AMT,CDML.CDML_COPAY_AMT ORDER BY CLCK.CLCK_PAYEE_IND DESC) AS ROW_NUM1                      			 FROM FC_CDS_INPS_PYMT_DTL INPS1                       			 LEFT OUTER JOIN FC_CMC_CLCL_CLAIM CLCL ON INPS1.CLCL_ID = CLCL.CLCL_ID                       			 LEFT OUTER JOIN FC_CMC_CDML_CL_LINE CDML ON CLCL.CLCL_ID = CDML.CLCL_ID                       			 LEFT OUTER JOIN FC_CMC_CLCK_CLM_CHECK CLCK ON CDML.CLCL_ID = CLCK.CLCL_ID                       			 LEFT OUTER JOIN FC_CMC_CKCK_CHECK CKCK ON CLCK.CKPY_REF_ID = CKCK.CKPY_REF_ID                       			 LEFT OUTER JOIN FC_CMC_PRPR_PROV PRPR ON CLCK.CLCK_PAYEE_PR_ID = PRPR.PRPR_ID WHERE INPS1.BLIV_ID IN ('"
						+ invoiceId
						+ "'))                       			 WHERE ROW_NUM1 ='1' GROUP BY CLCLCLMID,ADJCLMID,CDMLCLMID,CLCKCLMID,CKCKNO,PAYEEIND,PRPRNAME,PAYEEID) ROW_TABLE ON INPS.CLCL_ID=ROW_TABLE.CLCLCLMID                        			 LEFT OUTER JOIN (SELECT DISTINCT * FROM (SELECT DISTINCT XWALK.OLD_CLM_ID AS OLDCLMID,XWALK.NEW_CLM_ID AS NEWCLMID,XWALK.CLM_ACCT_CATEG_CD AS EXTCATEGCD, CKCK1.CKCK_CK_NO AS EXTCKCKNO,                       			 CLCK1.CLCK_PAYEE_IND AS EXTPAYEEIND,PRPR1.PRPR_NAME AS EXTPRPRNAME, CLCK1.CLCK_PAYEE_PR_ID AS EXTPAYEEID,INPS2.BLPS_PAID_DT AS CLMPAIDDT,INPS2.BLPS_FROM_DT AS CLMFROMDT, INPS2.BLPS_TO_DT AS CLMTODT,                       			 ROW_NUMBER() OVER (PARTITION BY  XWALK.NEW_CLM_ID ORDER BY CLCK1.CLCK_PAYEE_IND DESC) as ROW_NUM2                       			 FROM FC_CDS_INPS_PYMT_DTL INPS2                       			 LEFT OUTER JOIN CU_AFA_HRA_FALLOUT_XWALK XWALK ON INPS2.CLCL_ID=XWALK.NEW_CLM_ID                       			 LEFT OUTER JOIN FC_CMC_CLCK_CLM_CHECK CLCK1 ON XWALK.OLD_CLM_ID = CLCK1.CLCL_ID                       			 LEFT OUTER JOIN FC_CMC_CKCK_CHECK CKCK1 ON CLCK1.CKPY_REF_ID = CKCK1.CKPY_REF_ID                       			 LEFT OUTER JOIN FC_CMC_PRPR_PROV PRPR1 ON CLCK1.CLCK_PAYEE_PR_ID = PRPR1.PRPR_ID WHERE INPS2.BLIV_ID IN ('"
						+ invoiceId
						+ "')) WHERE ROW_NUM2='1') ROW_TABLE1 ON INPS.CLCL_ID=ROW_TABLE1.NEWCLMID AND BLXP.BLXP_ACCT_CAT=ROW_TABLE1.EXTCATEGCD WHERE INID.BLIV_ID IN ('"
						+ invoiceId
						+ "')) GROUP BY GROUPNAME,GROUPBILLINGID,CLAIMSCYCLE,BILDUEDATE,INVOICENO,GROUPIDNAME,BILLINGCATEGORY,PLANID,ClassID,SUBSCRIBERID,SSN,SUBSCRIBERNAME,PATIENT_NAME,RELATIONSHIP,Dept,CLAIMID, CHECKNUMBER,PAIDDATE,FROMDATE,TODATE,PAYEENAME,PAYEEID,COVERAGE  				 ORDER BY (CASE WHEN COVERAGE='HEALTH' THEN 1  WHEN COVERAGE='PHARMACY' THEN 2  WHEN COVERAGE='DENTAL' THEN 3 END),CLAIMID ");
		List<Object[]> resultList = new ArrayList<Object[]>();
		resultList = query.list();
		List<MemberDetails> memberDetailsList = new ArrayList<MemberDetails>();
		for (Object[] memberDetailsData : resultList) {
			MemberDetails memberDetails = new MemberDetails();
			memberDetails.setGroupName(String.valueOf(memberDetailsData[0]).trim());
			memberDetails.setGroupBillingId(String.valueOf(memberDetailsData[1]).trim());
			memberDetails.setClaimsCycle(String.valueOf(memberDetailsData[2]).trim());
			memberDetails.setBillDueDate(String.valueOf(memberDetailsData[3]).trim());
			memberDetails.setInvoiceNo(String.valueOf(memberDetailsData[4]).trim());
			memberDetails.setGroupIdName(String.valueOf(memberDetailsData[5]).trim());
			memberDetails.setBillingCategory(String.valueOf(memberDetailsData[6]).trim());
			memberDetails.setPlanId(String.valueOf(memberDetailsData[7]).trim());
			memberDetails.setClassId(String.valueOf(memberDetailsData[8]).trim());
			memberDetails.setSubscriberId(String.valueOf(memberDetailsData[9]).trim());
			memberDetails.setSsn(String.valueOf(memberDetailsData[10]).trim());
			memberDetails.setSubscriberName(String.valueOf(memberDetailsData[11]).trim());
			memberDetails.setPatientName(String.valueOf(memberDetailsData[12]).trim());
			memberDetails.setRelationship(String.valueOf(memberDetailsData[13]).trim());
			memberDetails.setDept(String.valueOf(memberDetailsData[14]).trim());
			memberDetails.setClaimId(String.valueOf(memberDetailsData[15]).trim());
			memberDetails.setCheckNumber((BigDecimal)memberDetailsData[16]);
			memberDetails.setPaidDate(String.valueOf(memberDetailsData[17]).trim());
			memberDetails.setFromDate(String.valueOf(memberDetailsData[18]).trim());
			memberDetails.setToDate(String.valueOf(memberDetailsData[19]).trim());
			memberDetails.setPayeeName(String.valueOf(memberDetailsData[20]).trim());
			memberDetails.setPayeeId(String.valueOf(memberDetailsData[21]).trim());
			memberDetails.setCoverage(String.valueOf(memberDetailsData[22]).trim());
			memberDetails.setDeductible(String.valueOf(memberDetailsData[23]).trim());
			memberDetails.setCoinsurance(String.valueOf(memberDetailsData[24]).trim());
			memberDetails.setCopay(String.valueOf(memberDetailsData[25]).trim());
			memberDetails.setMedical(String.valueOf(memberDetailsData[26]).trim());
			memberDetails.setCostContainment(String.valueOf(memberDetailsData[27]).trim());
			memberDetails.setInterest(String.valueOf(memberDetailsData[28]).trim());
			memberDetails.setDental(String.valueOf(memberDetailsData[29]).trim());
			memberDetails.setPharmacy(String.valueOf(memberDetailsData[30]).trim());
			memberDetails.setBluecard(String.valueOf(memberDetailsData[31]).trim());
			memberDetails.setStoploss(String.valueOf(memberDetailsData[32]).trim());
			memberDetails.setHra(String.valueOf(memberDetailsData[33]).trim());
			memberDetails.setTotalPaid(String.valueOf(memberDetailsData[34]).trim());
			memberDetailsList.add(memberDetails);
			System.out.println(memberDetails);
		}
		memberDetailsDBMap.put(invoiceId, memberDetailsList);
		}
		return memberDetailsDBMap;
	}

	public Map<String,List<ShieldSaving>> getShieldSaving() {
		shieldSavingDBMap = new HashMap<String, List<ShieldSaving>>();
		List<ShieldSaving> shieldSavingsList = new ArrayList<ShieldSaving>();
		for(String invoiceId:inputInvoiceNoSet){
		
		SQLQuery query = session
				.createSQLQuery("SELECT distinct ClaimPaymentPeriod,GROUPNAME, GROUPBILLINGID, CLAIMSCYCLE, BILDUEDATE,INVOICENO,					 BILLINGCATEGORY, ('$'||SUM(NVL(ProviderChargedAmount,0))) As ProviderChargedAmount,     			 	  	 ('$'||SUM(NVL(SAVINGS,0))) As SAVINGS, ('$'||SUM(NVL(DISALLOWED,0))) As DISALLOWED,    				  	 ('$'||(NVL(SUM(ProviderChargedAmount- (SAVINGS+DISALLOWED)),0))) AS ALLOWEDAMOUNT,     					 ('$'||SUM(NVL(Costcontainment,0))) As Costcontainment ,  	 				 ('$'||(NVL(SUM((ProviderChargedAmount- (SAVINGS+DISALLOWED)) + Costcontainment),0))) AS Total  					 FROM  					 (    					 Select distinct ((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS ClaimPaymentPeriod,  					 AFAI.AFAI_NAME AS GROUPNAME, 	AFAI.AFAI_ID AS GROUPBILLINGID,  					 ((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS CLAIMSCYCLE,  					 TO_CHAR(INID.BLBL_DUE_DT,'MM/DD/YYYY') as BILDUEDATE,  					 INID.BLIV_ID AS INVOICENO,  					 CASE WHEN GRPATRB.ATRB_VAL_TXT IS NULL THEN 'XXX' ELSE GRPATRB.ATRB_VAL_TXT END AS BILLINGCATEGORY,  					 CASE WHEN (INPS.BLPS_SOURCE='4' AND BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF')) THEN (SUM(CLCL_TOT_CHG) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT)) END ProviderChargedAmount,    					 CASE WHEN (CCF.SHLD_SAV_USE_IND ='Y' AND CCF.INVOC_EXCL_IND ='N') THEN ((SUM(CDML.CDML_CONSIDER_CHG) OVER (PARTITION BY INPS.BLIV_ID))-(SUM(CDML.CDML_ALLOW) OVER (PARTITION BY INPS.BLIV_ID)))              		 ELSE 0 END AS SAVINGS,    					 CASE WHEN (CCF.SHLD_SAV_USE_IND ='N' AND CCF.INVOC_EXCL_IND ='Y')  THEN ((SUM(CDML.CDML_ALLOW) OVER (PARTITION BY INPS.BLIV_ID))-(SUM(CDML.CDML_CONSIDER_CHG) OVER (PARTITION BY INPS.BLIV_ID)))              		 ELSE 0 END AS DISALLOWED,            			 CASE WHEN (INPS.BLPS_SOURCE='4' AND BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF')) THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_SOURCE)) END Costcontainment,          			 row_number() OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT ORDER BY  INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT DESC) as ROW_NUM     					 FROM FC_CMC_AFAI_INDIC AFAI   					 INNER JOIN FC_CDS_INID_INVOICE INID ON AFAI.AFAI_CK=INID.AFAI_CK  					 INNER JOIN FC_CDS_INPS_PYMT_DTL INPS ON INID.BLIV_ID=INPS.BLIV_ID  					 LEFT OUTER JOIN FC_CMC_BLXP_EXT_PYMT BLXP ON BLXP.BLXP_CLCL_ID=INPS.CLCL_ID AND (BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF') AND INPS.BLPS_SOURCE='4')  					 LEFT OUTER JOIN FC_CMC_GRGR_GROUP GRGR ON INPS.GRGR_ID=GRGR.GRGR_ID  					 LEFT OUTER JOIN CU_CDM_GRP_STRUCT_MAIN GRPSTRT ON INPS.GRGR_CK=GRPSTRT.GRP_CK AND GRPSTRT.PLN_TERM_DT>INPS.BLPS_PAID_DT  AND INPS.CSPI_ID=GRPSTRT.PLN_ID   					 LEFT OUTER JOIN CU_CDM_GRP_STRUCT_ATRB GRPATRB ON GRPSTRT.GRP_STRUCT_CK=GRPATRB.GRP_STRUCT_CK AND GRPATRB.ATRB_NM='CBC'    					 LEFT OUTER JOIN FC_CMC_CLCL_CLAIM CLCL ON INPS.CLCL_ID = CLCL.CLCL_ID    					 LEFT OUTER JOIN FC_CMC_CDML_CL_LINE CDML ON CDML.CLCL_ID = INPS.CLCL_ID    					 LEFT OUTER JOIN CU_AFA_HRA_FALLOUT_XWALK XWALK ON INPS.CLCL_ID=XWALK.NEW_CLM_ID AND XWALK.CLM_ACCT_CATEG_CD=BLXP.BLXP_ACCT_CAT    					 LEFT OUTER JOIN CU_AFA_CCF_CLM_AMT CCF ON XWALK.OLD_CLM_ID=CCF.CLM_ID    					 WHERE INID.BLIV_ID in ('"
						+ invoiceId
						+ "')) WHERE ROW_NUM=1 GROUP BY ClaimPaymentPeriod,GROUPNAME, GROUPBILLINGID, CLAIMSCYCLE, BILDUEDATE,INVOICENO,BILLINGCATEGORY,ProviderChargedAmount,SAVINGS,DISALLOWED,Costcontainment Order by BILLINGCATEGORY ");
		List<Object[]> resultList = new ArrayList<Object[]>();
		resultList = query.list();
		for (Object[] shieldSavingData : resultList) {
			ShieldSaving shieldSaving = new ShieldSaving();
			shieldSaving.setClaimPaymentPeriod((String) shieldSavingData[0]);
			shieldSaving.setGroupName((String) shieldSavingData[1]);
			shieldSaving.setGroupBillingId((String) shieldSavingData[2]);
			shieldSaving.setClaimsCycle((String) shieldSavingData[3]);
			shieldSaving.setBillDueDate((String) shieldSavingData[4]);
			shieldSaving.setInvoiceNo((String) shieldSavingData[5]);
			shieldSaving.setBillingCategory((String) shieldSavingData[6]);
			shieldSaving.setProviderChargedAmount((String) shieldSavingData[7]);
			shieldSaving.setSavings((String) shieldSavingData[8]);
			shieldSaving.setDisallowed((String) shieldSavingData[9]);
			shieldSaving.setAllowedAmount((String) shieldSavingData[10]);
			shieldSaving.setCostContainment((String) shieldSavingData[11]);
			shieldSaving.setTotal((String) shieldSavingData[12]);
			
			if(!shieldSaving.getTotal().equalsIgnoreCase("$0")){
				shieldSavingsList.add(shieldSaving);
				System.out.println("DataBase Row: "+shieldSaving);
			}
			
		}
		
		shieldSavingDBMap.put(invoiceId,shieldSavingsList);
		}
		
		return shieldSavingDBMap;
	}

	public List<PlanDetails> getPlanDetails() {
		planDetailsDBList = new ArrayList<PlanDetails>();
		for(String invoiceId:inputInvoiceNoSet){
		PlanDetails planDetails = new PlanDetails();
		SQLQuery query = session
				.createSQLQuery("SELECT DISTINCT GROUPNAME,AFAID,CLAIMSCYCLE,BILDUEDATE,INVOICENO,GROUPIDNAME,PLN,CBC,GRPID,COVERAGE, CASE WHEN COL='PLANID' THEN 'Subtotal for Plan: ' || PLN  WHEN COL='BILLINGCATEGORY' THEN 'Total by Billing Category: ' || CBC  WHEN COL='GROUPID' THEN 'Total by Group: ' || GRPID  WHEN COL='GROUPBILLINGID' THEN 'Total by Group Billing ID: ' || AFAID END AS PlandetailsTotals, MEDICAL,Costcontainment,Interest,Dental,Pharmacy,StopLoss,BlueCard,HRA, TotalPaid,CASE WHEN COL='PLANID' THEN 1  WHEN COL='BILLINGCATEGORY' THEN 2  WHEN COL='GROUPID' THEN 3 WHEN COL='GROUPBILLINGID' THEN 4 End As Sortorder1,CASE WHEN COVERAGE='HEALTH' THEN 1  WHEN COVERAGE='PHARMACY' THEN 2  WHEN COVERAGE='DENTAL' THEN 3 END AS Sortorder2 FROM ( Select GROUPNAME,GROUPBILLINGID,CLAIMSCYCLE,BILDUEDATE,INVOICENO,GROUPIDNAME,BILLINGCATEGORY,PLANID,GROUPID,PLANID AS PLN,BILLINGCATEGORY AS CBC,GROUPID AS GRPID,GROUPBILLINGID AS AFAID,COVERAGE,('$'||(SUM(NVL(MedicalAmount,0))-SUM(NVL(Interestamount,0))-SUM(NVL(BlueCardAccessFees,0)))) AS Medical,('$'||SUM(NVL(Costcontainment,0))) As Costcontainment,('$'||SUM(NVL(Interestamount,0))) As Interest,('$'||SUM(NVL(DentalAmount,0))) As Dental, ('$'||SUM(NVL(PharmacyAmount,0))) AS Pharmacy,('$'||SUM(NVL(BlueCardAccessFees,0))) AS BlueCard,('$'||SUM(NVL(StopLossAdvancedFunding,0))) AS StopLoss,('$'||SUM(NVL(HRA,0))) AS HRA,('$' || SUM(NVL(TotalClaims,0))) AS TotalPaid FROM (SELECT DISTINCT AFAI.AFAI_NAME AS GROUPNAME,AFAI.AFAI_ID AS GROUPBILLINGID,((TO_CHAR(INPS.BLPS_FNDG_FROM_DT,'MM/DD/YYYY'))||'-'||(TO_CHAR(INPS.BLPS_FNDG_THRU_DT,'MM/DD/YYYY'))) AS CLAIMSCYCLE,TO_CHAR(INID.BLBL_DUE_DT,'MM/DD/YYYY') as BILDUEDATE,INID.BLIV_ID AS INVOICENO,INPS.GRGR_ID ||'/'|| GRGR.GRGR_NAME AS GROUPIDNAME, CASE WHEN GRPATRB.ATRB_VAL_TXT IS NULL THEN 'XXX' ELSE GRPATRB.ATRB_VAL_TXT END AS BILLINGCATEGORY, INPS.CSPI_ID AS PLANID, GRGR.GRGR_ID AS GROUPID, CASE WHEN INPS.BLPS_SOURCE ='1' OR BLXP.BLXP_ACCT_CAT IN ('HRAA','HRAF','CCFA','CCFF','COBA','COBF') THEN 'HEALTH' WHEN BLXP.BLXP_ACCT_CAT IN ('DRUG','DRUF') THEN 'PHARMACY' WHEN BLXP.BLXP_ACCT_CAT IN ('DENT','DENF') THEN 'DENTAL' ELSE '0' END AS COVERAGE, CASE WHEN INPS.BLPS_SOURCE ='1' OR BLXP.BLXP_ACCT_CAT IN ('HRAA','HRAF','COBA','COBF')  THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_AMT,GRPATRB.ATRB_VAL_TXT)) END AS MedicalAmount, CASE WHEN (BLXP.BLXP_ACCT_CAT IN ('CCFA','CCFF') AND INPS.BLPS_SOURCE='4') THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_SOURCE,GRPATRB.ATRB_VAL_TXT)) END Costcontainment,(SUM(INPS.BLPS_INT_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT)) AS Interestamount, CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DENT','DENF')) THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,BLXP.CSPD_CAT,INPS.BLPS_SOURCE)) END DentalAmount,CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('DRUG','DRUF')) THEN (SUM(INPS.BLPS_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,BLXP.CSPD_CAT,INPS.BLPS_SOURCE)) END PharmacyAmount, CASE WHEN  INPS.BLPS_SOURCE ='1' THEN (SUM(INPS.BLPS_ITS_FEE) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,BLXP.CSPD_CAT,INPS.BLPS_SOURCE)) END AS BlueCardAccessFees,((-1)*(SUM(INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_SSL_EXC_AMT,GRPATRB.ATRB_VAL_TXT))) AS StopLossAdvancedFunding,CASE WHEN (INPS.BLPS_SOURCE ='4' AND BLXP.BLXP_ACCT_CAT IN ('HRAA','HRAF'))THEN (SUM(INPS.BLPS_HSA_AMT) OVER (PARTITION BY INPS.BLIV_ID,INPS.BLPS_SOURCE,GRPATRB.ATRB_VAL_TXT)) END AS HRA,(SUM(INPS.BLPS_AMT+INPS.BLPS_HSA_AMT+INPS.BLPS_ITS_FEE-INPS.BLPS_SSL_EXC_AMT) OVER (PARTITION BY INPS.BLIV_ID,GRPATRB.ATRB_VAL_TXT,INPS.BLPS_SOURCE,INPS.BLPS_SSL_EXC_AMT,INPS.BLPS_HSA_AMT)) AS TotalClaims FROM FC_CMC_AFAI_INDIC AFAI INNER JOIN FC_CDS_INID_INVOICE INID ON AFAI.AFAI_CK=INID.AFAI_CK INNER JOIN FC_CDS_INPS_PYMT_DTL INPS ON INID.BLIV_ID=INPS.BLIV_ID LEFT OUTER JOIN FC_CMC_BLXP_EXT_PYMT BLXP ON BLXP.BLXP_CLCL_ID=INPS.CLCL_ID LEFT OUTER JOIN FC_CMC_GRGR_GROUP GRGR ON INPS.GRGR_ID=GRGR.GRGR_ID LEFT OUTER JOIN CU_CDM_GRP_STRUCT_MAIN GRPSTRT ON INPS.GRGR_CK=GRPSTRT.GRP_CK AND GRPSTRT.PLN_TERM_DT>INPS.BLPS_PAID_DT AND INPS.CSPI_ID=GRPSTRT.PLN_ID LEFT OUTER JOIN CU_CDM_GRP_STRUCT_ATRB GRPATRB ON GRPSTRT.GRP_STRUCT_CK=GRPATRB.GRP_STRUCT_CK AND GRPATRB.ATRB_NM='CBC'LEFT OUTER JOIN CU_AFA_HRA_FALLOUT_XWALK XWALK ON INPS.CLCL_ID=XWALK.NEW_CLM_ID  WHERE INID.BLIV_ID in ('"
						+ invoiceId
						+ "')) GROUP BY GROUPNAME,GROUPBILLINGID,CLAIMSCYCLE,BILDUEDATE,INVOICENO,GROUPIDNAME,BILLINGCATEGORY,PLANID,GROUPID,COVERAGE ) UNPIVOT EXCLUDE NULLS (  PlandetailsTotals FOR COL IN  (  PLANID,BILLINGCATEGORY,GROUPID,GROUPBILLINGID)) ORDER BY Sortorder2,PLN,CBC,GRPID,Sortorder1 ");
		List<Object[]> resultList = new ArrayList<Object[]>();
		resultList = query.list();

		for (Object[] planDetailsData : resultList) {
			planDetails.setGroupName((String) planDetailsData[0]);
			planDetails.setAfaId((String) planDetailsData[1]);
			planDetails.setClaimsCycle((String) planDetailsData[2]);
			planDetails.setBillDueDate((String) planDetailsData[3]);
			planDetails.setInvoiceNo((String) planDetailsData[4]);
				planDetails.setGroupIdName((String) planDetailsData[5]);
				planDetails.setPln((String) planDetailsData[6]);
				planDetails.setCbc((String) planDetailsData[7]);
				planDetails.setGrpId((String) planDetailsData[8]);
				planDetails.setCoverage((String) planDetailsData[9]);
				planDetails.setPlanDetailsTotals((String) planDetailsData[10]);
				planDetails.setMedical((String) planDetailsData[12]);
				planDetails.setCostContainment((String) planDetailsData[12]);
				planDetails.setInterest((String) planDetailsData[13]);
				planDetails.setDental((String) planDetailsData[14]);
				planDetails.setPharmacy((String) planDetailsData[15]);
				planDetails.setStoploss((String) planDetailsData[16]);
				planDetails.setBluecard((String) planDetailsData[17]);
				planDetails.setHra((String) planDetailsData[18]);
				planDetails.setTotalPaid((String) planDetailsData[19]);
				planDetails.setSortOrder1((BigDecimal) planDetailsData[20]);
				planDetails.setSortOrder2((BigDecimal) planDetailsData[21]);

			}
			planDetailsDBList.add(planDetails);
			System.out.println(planDetails);
		}
		return planDetailsDBList;
	}
}
