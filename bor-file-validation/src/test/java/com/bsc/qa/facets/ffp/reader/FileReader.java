package com.bsc.qa.facets.ffp.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bsc.qa.facets.afa.dao.DatabaseUtil;
import com.bsc.qa.facets.afa.pojo.BORFile;
import com.bsc.qa.facets.afa.pojo.ErrorStatus;
import com.bsc.qa.facets.afa.pojo.KeywordDB;
import com.bsc.qa.facets.afa.pojo.KeywordFile;
import com.bsc.qa.facets.afa.test.BorFileValidationTest;
import com.github.ffpojo.file.reader.FileSystemFlatFileReader;
import com.github.ffpojo.file.reader.FlatFileReader;
import com.github.ffpojo.file.reader.FlatFileReaderDefinition;

public class FileReader {
	String keywordFilePath = BorFileValidationTest.keywordFilePath;
	String borFilePath = BorFileValidationTest.borFilePath;
	File keywordFile = new File(keywordFilePath);
	File borFile = new File(borFilePath);
	List<KeywordFile> keywordFilelist;
	List<BORFile> borFileList;
	List<BORFile> commonClaimIdList;
	List<KeywordDB> keywordDBList;
	ArrayList<BORFile> finalBorList = new ArrayList<BORFile>();
	DatabaseUtil util = new DatabaseUtil();
	Session sessionObj = BorFileValidationTest.session;
	Set<String> lobIdSet = new HashSet<String>();
	
	public List<KeywordFile> parseKeywordFile() {
		keywordFilelist = new ArrayList<KeywordFile>();
		FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(
				KeywordFile.class);
		FlatFileReader ffReader = null;
		try {
			ffReader = new FileSystemFlatFileReader(keywordFile, ffDefinition);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Object record : ffReader) {
			KeywordFile body = (KeywordFile) record;
			keywordFilelist.add(body);

		}
		return keywordFilelist;
	}

	public List<BORFile> parseBORFile() {
		borFileList = new ArrayList<BORFile>();
		FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(
				BORFile.class);
		FlatFileReader ffReader = null;
		try {
			ffReader = new FileSystemFlatFileReader(borFile, ffDefinition);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Object record : ffReader) {
			BORFile body = (BORFile) record;
			borFileList.add(body);
			
		}
		
		return borFileList;

	}

	public List<BORFile> getCommonClaimId() throws Exception{
		Map<String , BORFile> map = new HashMap<String, BORFile>();

		commonClaimIdList=new ArrayList<BORFile>();
		for (int i = 0; i < borFileList.size(); i++) {
			boolean present = false;
			for (KeywordFile keywordFile: keywordFilelist) {
				if(borFileList.get(i).getClaimNumber().equalsIgnoreCase(keywordFile.getClaim_number())){
					commonClaimIdList.add(borFileList.get(i));
					present = true;
					break;
				}
			}
			if(present==false){
				ErrorStatus errorMessage = new ErrorStatus();
				lobIdSet.add("0006");
				lobIdSet.add("0018");
				lobIdSet.add("0011");
				lobIdSet.add("0012");
				
//				System.out.println(borFileList.get(i).getLineOfBusinessId()+" borFileList.get(i).getLineOfBusinessId()");
				if(!lobIdSet.contains(borFileList.get(i).getLineOfBusinessId())){
					
					errorMessage.setResult("PASS");
					errorMessage.setComments("Non Self funded LOB ID");
					
					if(!BorFileValidationTest.testResults.containsKey(borFileList.get(i).getClaimNumber().trim())){
						BorFileValidationTest.testResults.put(borFileList.get(i).getClaimNumber(),errorMessage);
						}
					
				}else{
//				String status;
				DatabaseUtil util = new DatabaseUtil();
					errorMessage = util.getErrorMessage(BorFileValidationTest.session ,borFileList.get(i).getClaimNumber().trim(),borFileList.get(i).getFileName().trim());
//				System.out.println("Error Message :"+errorMessage.getComments() );
				if(!BorFileValidationTest.testResults.containsKey(borFileList.get(i).getClaimNumber())){
					BorFileValidationTest.testResults.put(borFileList.get(i).getClaimNumber(),errorMessage);
					}
				}
			}	
		}
		
		for (BORFile borFile : commonClaimIdList) {
			if(map.containsKey(borFile.getClaimNumber())){
			    BORFile file = map.get(borFile.getClaimNumber());
			    
			    MathContext mc = new MathContext(2);
			    BigDecimal one = new BigDecimal(file.getClientPrice());
				BigDecimal two = new BigDecimal(borFile.getClientPrice());
			   
			    if(!one.equals(two)){
			    BigDecimal addition = one.add(two);
				file.setClientPrice(addition.toEngineeringString());
				map.replace(borFile.getClaimNumber(), file);
			    }
			}else{
				map.put(borFile.getClaimNumber(), borFile);
			}
		}
		for (Map.Entry<String,BORFile> entry : map.entrySet()){ 
			
            finalBorList.add(entry.getValue());
		}
		
		return finalBorList;
	}

	
	
	public List<KeywordFile> getSortedKeywordClaimId() {
		List<KeywordFile> sortedKeywordClaimIdList = new ArrayList<KeywordFile>();
		for (int i = 0; i < finalBorList.size(); i++) {
			for (KeywordFile keywordFile : keywordFilelist) {
				if (finalBorList.get(i).getClaimNumber()
						.equalsIgnoreCase(keywordFile.getClaim_number())) {
					sortedKeywordClaimIdList.add(keywordFile);
				}
			}

		}
		
		return sortedKeywordClaimIdList;
	}
	
	public List<KeywordDB> getKeywordDBList(List<BORFile> borList){
		keywordDBList = new ArrayList<KeywordDB>();
		
		for(BORFile borfile: borList){
		KeywordDB keywordDB= new KeywordDB();
		SQLQuery query =  sessionObj.createSQLQuery("Select MEME.MEME_REL from FC_CMC_MEME_MEMBER  MEME join FC_CMC_SBSB_SUBSC SBSB on SBSB.SBSB_CK =MEME.SBSB_CK where SBSB.SBSB_ID = '"+borfile.getSubscriberId()+"' and MEME.MEME_SFX='"+borfile.getPersonNumber()+"'");
		keywordDB.setRelationshipCode((String) query.list().get(0));
		keywordDB.setClaimId(borfile.getClaimNumber());
		keywordDBList.add(keywordDB);
		
		}
		
		List<KeywordDB> sortedKeywordDBList = new ArrayList<KeywordDB>();
		for (int i = 0; i < borList.size(); i++) {
			for (KeywordDB keywordDB : keywordDBList) {
				if (borList.get(i).getClaimNumber()
						.equalsIgnoreCase(keywordDB.getClaimId())) {
					sortedKeywordDBList.add(keywordDB);
				}
			}

		}
		return sortedKeywordDBList;
	}
	
	public static File getMatchingAndLatestFile(String directoryName,String wildcard) throws Exception{
		{
		    File directory = new File(directoryName);
		    Collection<File> files = FileUtils.listFiles(directory, new WildcardFileFilter(wildcard), null);
		    if(files.size()==0){
		    	Exception e = new Exception("No files matching specified pattern -" + wildcard );
		    	throw new Exception(e);
		    }
		    File latestFile = null;
		    Long lastModified = new Long(0) ;
		    for (File file : files) {
		    	if(lastModified<file.lastModified()){
				lastModified = file.lastModified();
				latestFile = file;
		    	}
			}
		    return latestFile;
		}
	}
}
