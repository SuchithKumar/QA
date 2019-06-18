package com.bsc.qa.facets.afa.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bsc.qa.facets.afa.pojo.BORFile;
import com.bsc.qa.facets.afa.pojo.DatabaseBOR;
import com.bsc.qa.facets.afa.pojo.ErrorStatus;

public class DatabaseUtil {
	Map<String, String> queriesMap;
	List<DatabaseBOR> databaseBorList = new ArrayList<DatabaseBOR>();;
	QueriesUtil queryUtil = new QueriesUtil();

	public ErrorStatus getErrorMessage(Session session, String claim_id,
			String fileName) {
		queriesMap = queryUtil.queriesMap();
		String status = "";
		ErrorStatus errorStatus = new ErrorStatus();
		SQLQuery query = (SQLQuery) session
				.createSQLQuery(queriesMap.get("ErrorDescriptionQuery"))
				.setParameter("clm", claim_id).setParameter("file", fileName);
		String errorDescription = (String) query.list().get(0);

		if (errorDescription.equalsIgnoreCase("ACCEPTED")) {
			SQLQuery query1 = (SQLQuery) session
					.createSQLQuery(queriesMap.get("RecordStatusCodeQuery"))
					.setParameter("clm", claim_id)
					.setParameter("file", fileName);
			String recordStatusCode = (String) query1.list().get(0);
			// If its accepted and status code is p then pass with comments-0$
			// claim so data not extracted in keyword
			if (recordStatusCode.equalsIgnoreCase("P")) {
				errorStatus.setResult("PASS");
				errorStatus
						.setComments("Zero Dollar claim so data not extracted in keyword");
			}
			// if its y fail with comments - not completed data failed to load
			// in keyword file
			else if (recordStatusCode.equalsIgnoreCase("Y")) {
				errorStatus.setResult("FAIL");
				errorStatus
						.setComments("Not completed, Data failed to load in keyword file");
			}
			// if its c then fail with comments - not completed ,data failed to
			// load in keyword file
			else if (recordStatusCode.equalsIgnoreCase("C")) {
				errorStatus.setResult("FAIL");
				errorStatus
						.setComments("Not completed, Data failed to load in keyword file");
			}
			// if its null then fail with comments - not completed ,data failed
			// to
			// load in keyword file
			else if (recordStatusCode.isEmpty()) {
				errorStatus.setResult("FAIL");
				errorStatus
						.setComments("Not completed, Data failed to load in keyword file");
			}

		}// if outdated then pass with comments - status updated in bor table as
			// outdated
		else if (errorDescription.equalsIgnoreCase("OUTDATED")) {
			errorStatus.setResult("PASS");
			errorStatus.setComments("Status updated in bor table as outdated");
		}
		// if duplicate them pass with comments - status updated in bor table as
		// duplicate
		else if (errorDescription.equalsIgnoreCase("DUPLICATE")) {
			errorStatus.setResult("PASS");
			errorStatus.setComments("Status updated in bor table as duplicate");
		}
		// if REVERSIONED them pass with comments - status updated in bor table
		// as duplicate
		else if (errorDescription.equalsIgnoreCase("REVERSIONED")) {
			errorStatus.setResult("PASS");
			errorStatus
					.setComments("Status updated in bor table as reversioned");
		}

		return errorStatus;
	}

	public Map<String, DatabaseBOR> getDatabaseBorFileHistRecords(
			Session session, List<BORFile> borList) throws Exception {

		queriesMap = queryUtil.queriesMap();
		Map<String, DatabaseBOR> databaseBorMap = new HashMap<String, DatabaseBOR>();

		for (BORFile borFile : borList) {
			DatabaseBOR databaseBor = new DatabaseBOR();

			Query query = session
					.createSQLQuery(queriesMap.get("DatabaseBORQuery"))
					.setParameter("clm", borFile.getClaimNumber())
					.setParameter("file", borFile.getFileName())
					.setParameter("version", borFile.getClaimVersionNumber());
			// System.out.println("Claim ID : "+borFile.getClaimNumber());
			try {
				List<Object[]> list = (List<Object[]>) query.list();
				for (Object[] object : list) {
					databaseBor
							.setFICT_CLM_ID(String.valueOf(object[0]).trim());
					databaseBor.setFIL_NM(String.valueOf(object[1]).trim());
					databaseBor.setVEND_NM(String.valueOf(object[2]).trim());
					databaseBor.setGRP_NBR(String.valueOf(object[3]).trim());
					databaseBor.setSBGRP_ID(String.valueOf(object[4]).trim());
					databaseBor.setSBSCR_ID(String.valueOf(object[5]).trim());
					databaseBor.setPERS_NBR(String.valueOf(object[6]).trim());
					databaseBor.setCLM_NBR(String.valueOf(object[7]).trim());
					databaseBor
							.setCLM_VER_NBR(String.valueOf(object[8]).trim());
					databaseBor.setCLM_AMT((BigDecimal) object[9]);
					databaseBor.setCLI_PRC_AMT((BigDecimal) object[10]);
					databaseBor.setBSC_RVNU_AMT((BigDecimal) object[11]);
					databaseBor.setCHK_NBR(String.valueOf(object[12]));
					databaseBor.setCHK_DT((Date) object[13]);
					databaseBor.setSVC_DT((Date) object[14]);
					databaseBor.setPAYE_ID(String.valueOf(object[15]).trim());
					databaseBor.setPAYE_NM(String.valueOf(object[16]).trim());
					databaseBor.setPLN_ID(String.valueOf(object[17]).trim());
					databaseBor.setPRDCT_ID(String.valueOf(object[18]).trim());
					databaseBor.setPRDCT_CATEG_CD(String.valueOf(object[19])
							.trim());
					databaseBor.setCLS_ID(String.valueOf(object[20]).trim());
					databaseBor.setPRDCT_BUS_CATEG_CD(String
							.valueOf(object[21]).trim());
					databaseBor.setPRDCT_VAL1_CD(String.valueOf(object[22])
							.trim());
					databaseBor.setLOB_ID(String.valueOf(object[23]).trim());
					databaseBor.setLGL_ENTY_CD(String.valueOf(object[24])
							.trim());
					databaseBor.setBIL_AMT((BigDecimal) object[25]);
					databaseBor.setALLOW_AMT((BigDecimal) object[26]);
					databaseBor.setDED_AMT((BigDecimal) object[27]);
					databaseBor.setCOINS_AMT((BigDecimal) object[28]);
					databaseBor.setCOPAY_AMT((BigDecimal) object[29]);
					databaseBor.setDIAG_CD(String.valueOf(object[30]).trim());
					databaseBor.setDIAG_TYP_CD(String.valueOf(object[31])
							.trim());
					databaseBor.setPROC_CD(String.valueOf(object[32]).trim());
					databaseBor.setHCPCS_ID(String.valueOf(object[33]).trim());
					databaseBor.setCLM_TRANS_TYP_CD(String.valueOf(object[34])
							.trim());
					databaseBor.setERR_CD(String.valueOf(object[35]).trim());
					databaseBor.setERR_DESC(String.valueOf(object[36]).trim());
					databaseBor.setSEQ_KEY((BigDecimal) object[39]);
					databaseBorList.add(databaseBor);
					if (!databaseBorMap.containsKey(borFile.getClaimNumber()
							.trim()
							+ ","
							+ borFile.getClaimVersionNumber().trim())) {
						// System.out.println(databaseBor);
						databaseBorMap.put(borFile.getClaimNumber().trim()
								+ "," + borFile.getClaimVersionNumber().trim(),
								databaseBor);
					}

				}
			} catch (Exception e) {
				databaseBor.setCLM_NBR(borFile.getClaimNumber());
				databaseBor.setFIL_NM(borFile.getFileName());
				if (!databaseBorMap.containsKey(borFile.getClaimNumber().trim()
						+ "," + borFile.getClaimVersionNumber().trim())) {
					// System.out.println(databaseBor);
					databaseBorMap.put(borFile.getClaimNumber().trim() + ","
							+ borFile.getClaimVersionNumber().trim(),
							databaseBor);
				}
			}
		}

		return databaseBorMap;
	}

}
