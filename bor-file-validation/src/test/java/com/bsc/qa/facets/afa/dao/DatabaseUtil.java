package com.bsc.qa.facets.afa.dao;

import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bsc.qa.facets.afa.pojo.ErrorStatus;

public class DatabaseUtil {
	Map<String, String> queriesMap;
	QueriesUtil queryUtil = new QueriesUtil();

	public ErrorStatus getErrorMessage(Session session, String claim_id,String fileName) {
		queriesMap = queryUtil.queriesMap();
		String status = "";
		ErrorStatus errorStatus = new ErrorStatus();
		SQLQuery query = session.createSQLQuery(queriesMap.get("ErrorDescriptionLine")
												+ claim_id 
												+queriesMap.get("ErrorDescriptionLine1")
												+fileName
												+"'");
		String errorDescription = (String) query.list().get(0);
//		System.out.println(errorDescription);
		
		if (errorDescription.equalsIgnoreCase("ACCEPTED")) {
			SQLQuery query1 = session.createSQLQuery(queriesMap.get("RecordStatusCodeLine1")
													 + claim_id
													 +queriesMap.get("RecordStatusCodeLine2")
													 +fileName 
													 +"'");
			String recordStatusCode = (String) query1.list().get(0);
			// If its accepted and status code is p then pass with comments-0$
			// claim so data not extracted in keyword
			if ( recordStatusCode.equalsIgnoreCase("P")) {
				errorStatus.setResult("PASS");
				errorStatus.setComments("Zero Dollar claim so data not extracted in keyword");
			}
			// if its y fail with comments - not completed data failed to load
			// in keyword file
			else if (recordStatusCode.equalsIgnoreCase("Y")) {
				errorStatus.setResult("FAIL");
				errorStatus.setComments("Not completed, Data failed to load in keyword file");
			}
			// if its c then fail with comments - not completed ,data failed to
			// load in keyword file
			else if (recordStatusCode.equalsIgnoreCase("C")) {
				errorStatus.setResult("FAIL");
				errorStatus.setComments("Not completed, Data failed to load in keyword file");
			}
			// if its null then fail with comments - not completed ,data failed to
			// load in keyword file
			else if (recordStatusCode.isEmpty()){
				errorStatus.setResult("FAIL");
				errorStatus.setComments("Not completed, Data failed to load in keyword file");
			}
			
		}// if outdated then pass with comments - status updated in bor table as outdated
		else if (errorDescription.equalsIgnoreCase("OUTDATED")) {
			errorStatus.setResult("PASS");
			errorStatus.setComments("Status updated in bor table as outdated");
		}
		// if duplicate them pass with comments - status updated in bor table as duplicate
		else if (errorDescription.equalsIgnoreCase("DUPLICATE")) {
			errorStatus.setResult("PASS");
			errorStatus.setComments("Status updated in bor table as duplicate");
		}
		// if REVERSIONED them pass with comments - status updated in bor table as duplicate
		else if(errorDescription.equalsIgnoreCase("REVERSIONED")){
			errorStatus.setResult("PASS");
			errorStatus.setComments("Status updated in bor table as reversioned");
		}
		
		return errorStatus;
	}

}
