package com.bsc.qa.facets.ffp.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.omg.CORBA.OMGVMCID;

import com.bsc.qa.facets.afa.pojo.DatabaseKeyword;
import com.bsc.qa.facets.afa.pojo.Demographic;
import com.bsc.qa.facets.afa.pojo.KeywordFile;
import com.bsc.qa.facets.afa.test.KeywordDBValidationTest;
import com.bsc.qa.facets.afa.utility.QueriesUtil;
import com.github.ffpojo.file.reader.FileSystemFlatFileReader;
import com.github.ffpojo.file.reader.FlatFileReader;
import com.github.ffpojo.file.reader.FlatFileReaderDefinition;

public class FileReader {

	private static Set<String> inputClaimSet = new HashSet<String>();
	List<KeywordFile> keywordlist;
	List<DatabaseKeyword> databaselist;
	List<Demographic> demographiclist;
	String file = KeywordDBValidationTest.keyword_filename;// argus_afa_20190506024506.txt";
	static String inputFileName = new File("").getAbsolutePath()
			+ "\\input\\Input.xlsx";
	File inputFile = new File(file);
	List<KeywordFile> list;
	Map<String, String> queryMap = new HashMap<String, String>();
	public List<DatabaseKeyword> getDatabaseKeywords(Session session,
			List<KeywordFile> keywordlist) throws Exception {
		QueriesUtil util = new QueriesUtil();
		queryMap = util.queriesMap();
		databaselist = new ArrayList<DatabaseKeyword>();
		for (int i = 0; i < keywordlist.size(); i++) {
			String batchid = keywordlist.get(i).getRun_date()
					+ keywordlist.get(i).getBatch_name()
					+ keywordlist.get(i).getSequence_number();
			String claimid = keywordlist.get(i).getClaim_number();
			DatabaseKeyword dbkey;
			try {
				Query query = session.createQuery(queryMap.get("blxpQuery"))
							  .setParameter("batch", batchid)
							  .setParameter("clmid", claimid);
				dbkey = (DatabaseKeyword) query.list().get(0);
				databaselist.add(dbkey);
				} catch (Exception e) {
				e.getCause();
				}

		}
		if(databaselist.size()==0){
			throw new Exception("No Data in BLXP Table !");
		}
		return databaselist;
	}

	public List<KeywordFile> parseFile() throws Exception {

		list = new ArrayList<KeywordFile>();
		FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(
				KeywordFile.class);
		FlatFileReader ffReader = null;
		try {
			ffReader = new FileSystemFlatFileReader(inputFile, ffDefinition);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object record : ffReader) {
			KeywordFile body = (KeywordFile) record;
			list.add(body);

		}
		if(list.size()==0){
			throw new Exception("No records found in Keyword File !");
		}
		return list;
	}

	

	public List<Demographic> getDemographic(List<KeywordFile> keywordlist,
			Session session) throws Exception{
		QueriesUtil util = new QueriesUtil();
		queryMap = util.queriesMap();
		
		demographiclist = new ArrayList<Demographic>();
		for (int i = 0; i < keywordlist.size(); i++) {
			String sbsb_Id = keywordlist.get(i).getSubscriber_id();
			String cspi_id = keywordlist.get(i).getPlan_id();
			String grgr_id = keywordlist.get(i).getGroup_id();
			String clm_id = keywordlist.get(i).getClaim_number();
			String membersufix = keywordlist.get(i).getMember_suffix();
//			System.out.println(queryMap.get("demographicQuery"));
			Query q = (Query) session.createSQLQuery(queryMap.get("demographicQuery"))
							.setParameter("sbsb", sbsb_Id)
							.setParameter("cscs", cspi_id)
							.setParameter("grgr", grgr_id)
							.setParameter("clmid", clm_id)
							.setParameter("memsfx", membersufix);
			List<Object[]> list = (List<Object[]>) q.list();
			for (Object[] objects : list) {
				Demographic demographic = new Demographic();
				demographic.setSBSB_CK((BigDecimal) objects[0]);
				demographic.setMEME_CK((BigDecimal) objects[1]);
				demographic.setGRGR_CK((BigDecimal) objects[2]);
				demographic.setPRDCT_ID((String) objects[3]);
				demographic.setLOB_ID((String) objects[4]);
				demographic.setMEME_SFX((BigDecimal) objects[5]);
				demographic.setMEME_REL((String) objects[6]);
				demographiclist.add(demographic);
			}

		}
		return demographiclist;
	}

	
	public static File getMatchingAndLatestFile(String directoryName,
			String wildcard) throws Exception {
		{
			File directory = new File(directoryName);
			Collection<File> files = FileUtils.listFiles(directory,
					new WildcardFileFilter(wildcard), null);
			File latestFile = null;
			Long lastModified = new Long(0);
			for (File file : files) {
				if (lastModified < file.lastModified()) {
					lastModified = file.lastModified();
					latestFile = file;
				}
			}
			return latestFile;
		}
	}
	
	public void getqueries(){
		QueriesUtil util = new QueriesUtil();
		queryMap = util.queriesMap();
		
	}

}
