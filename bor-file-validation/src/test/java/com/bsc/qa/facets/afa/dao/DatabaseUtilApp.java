package com.bsc.qa.facets.afa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bsc.qa.facets.afa.pojo.BORDatabaseApp;
import com.bsc.qa.facets.afa.pojo.BORFileApp;
import com.bsc.qa.facets.afa.pojo.BorAdjustmentDBApp;
import com.bsc.qa.facets.bor_file_generator_stt.scenarios.TestScenariosApp;
import com.bsc.qa.facets.bor_file_generator_stt.util.HelperClassApp;

public class DatabaseUtilApp {

	List<BORFileApp>  borFileList ;
	List<BORDatabaseApp> borDatabaseList;
	List<BorAdjustmentDBApp> borAdjustmentDbList;
	Map<String, String> queriesMap;
	QueriesUtilApp queryUtil = new QueriesUtilApp();
	public List<BORDatabaseApp> getBorFileListFromDB(Session session){
		borDatabaseList = new ArrayList<BORDatabaseApp>();
		queriesMap =  queryUtil.queriesMap();
		SQLQuery query = session.createSQLQuery(queriesMap.get("BorFileListFromDB"));
		/*
		 * GRPID SUBGRPID SUBID MEMSFX PLANID PRDID PRDCAT CLASSID PRDBUSCAT
		 * PRDVALCD LOBDID LOBDTYPE
		 */
		List<Object[]> list = (List<Object[]>) query.list();
		for (Object[] objects : list) {
			BORDatabaseApp borDB = new BORDatabaseApp();
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
	
	public List<BorAdjustmentDBApp> getBorAdjustmentRecords(Session session){
		borAdjustmentDbList = new ArrayList<BorAdjustmentDBApp>();
		queriesMap =  queryUtil.queriesMap();
		SQLQuery query = session.createSQLQuery(queriesMap.get("BorAdjustmentRecords"));
		List<Object[]> list = (List<Object[]>) query.list();
		for (Object[] objects : list) {
			BorAdjustmentDBApp borAdjustmentDB = new BorAdjustmentDBApp();
		
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
	
	public List<BORFileApp> getBorFileList(Session session,List<BORDatabaseApp> borDatabaseList,List<BorAdjustmentDBApp> borAdjustmentDbList){
		borFileList = new ArrayList<BORFileApp>();
		String borFileName = HelperClassApp.getBorFileName();
		
		
		TestScenariosApp getBorRecords = new TestScenariosApp();
		//Paid Scenario 1
		BORFileApp borfilePaidScenario1 = getBorRecords.getPaidScenario1(session, borDatabaseList,borFileName);
		borFileList.add(borfilePaidScenario1);
		borFileList.add(borfilePaidScenario1);
		//Paid Scenario 2
		borFileList.add(getBorRecords.getPaidScenario2(session, borDatabaseList,borFileName));
		//Paid Scenario 3
		borFileList.add(getBorRecords.getPaidScenario3(session, borDatabaseList,borFileName));
		//Paid Scenario 4
		borFileList.add(getBorRecords.getPaidScenario4(session, borDatabaseList,borFileName));
		//Paid Scenario 5
		borFileList.add(getBorRecords.getPaidScenario5(session, borDatabaseList, borFileName));
		
		//Adjustment scenario 1
		borFileList.addAll(getBorRecords.getAdjustmentScenario1(session, borAdjustmentDbList, borFileName));
		//Adjustment scenario 2
		borFileList.addAll(getBorRecords.getAdjustmentScenario2(session, borAdjustmentDbList, borFileName));
		//Adjustment scenario 3
		borFileList.addAll(getBorRecords.getAdjustmentScenario3(session, borAdjustmentDbList, borFileName));
		//Adjustment scenario 4
		borFileList.addAll(getBorRecords.getAdjustmentScenario4(session, borAdjustmentDbList, borFileName));
		//Adjustment scenario 5
		borFileList.addAll(getBorRecords.getAdjustmentScenario5(session, borAdjustmentDbList, borFileName));
		//Adjustment scenario 6
		borFileList.addAll(getBorRecords.getAdjustmentScenario6(session, borAdjustmentDbList, borFileName));
		
		return borFileList;
	}
	
}
