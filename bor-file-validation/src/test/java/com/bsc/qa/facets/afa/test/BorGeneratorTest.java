package com.bsc.qa.facets.afa.test;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.facets.afa.dao.DatabaseUtilApp;
import com.bsc.qa.facets.afa.pojo.BORFileApp;
import com.bsc.qa.facets.afa.pojo.ConnectionApp;
import com.bsc.qa.facets.bor_file_generator_stt.util.HibernateUtilApp;
import com.github.ffpojo.file.writer.FileSystemFlatFileWriter;
import com.github.ffpojo.file.writer.FlatFileWriter;

public class BorGeneratorTest {
	DatabaseUtilApp util = new DatabaseUtilApp();
	AutomationStringUtilities decoder = new AutomationStringUtilities();
	SessionFactory sessionFactory = null;
	Session session;
	Logger logger1 = LoggerFactory.getLogger(BorGeneratorTest.class);
	String oracleUser = decoder.decryptValue(System.getenv("ORACLE_USER"));
	String oraclePassword = decoder.decryptValue(System.getenv("ORACLE_PASSWORD"));
	String oracleServer = System.getenv("ORACLE_SERVER");
	String oraclePort = System.getenv("ORACLE_PORT");
	
	String oracleDB = System.getenv("ORACLE_DB");
	String oracleUrl = "jdbc:oracle:thin:@" + oracleServer + ":" + oraclePort + ":" + oracleDB ;
	String destFolder = System.getenv("BOR_DEST_PATH");
	List<BORFileApp> list = new ArrayList<BORFileApp>();
	@BeforeSuite
	public void getConnection() {
		
		ConnectionApp conn = new ConnectionApp();
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		logger1.info("BOR File will be generated here --> "+destFolder);
		
		try {
			logger1.info("Connecting to Database...");
			logger1.info("Establishing DB connection with the URL - "+conn.getUrl());
			sessionFactory = HibernateUtilApp.createSessionFactory(conn);
		} catch (Exception e) {
			logger1.error( "Provided invalid database environment variables, Unable to Connect to DB");
			logger1.info( "Ending test execution...");
			System.exit(0);
		}
		session = sessionFactory.openSession();
		Transaction txn = session.beginTransaction();
		boolean success = session.isConnected();
		txn.commit();
		if (success == true){
			logger1.info("Succesfully connected to DB!");
		}
	}
	
	@BeforeTest
	public void getData() throws Exception {
		list = util.getBorFileList(session,util.getBorFileListFromDB(session),util.getBorAdjustmentRecords(session));
		String filename = list.get(0).getFileName();
		
		File file = new File(destFolder+filename);
		System.out.println(file.getAbsolutePath());
		FlatFileWriter ffWriter = new FileSystemFlatFileWriter(file, true);
		
		ffWriter.writeRecordList(list);
		ffWriter.close();
	}
	
	@Test
	public void testAll(){
		
	}
	
	@AfterTest
	public void changeFileFormatToUnix(){
		try {
			logger1.info("Modifying EOL characters from Windows to UNIX format!");
			logger1.info("Modifying file - " + destFolder+list.get(0).getFileName());
			BorGeneratorTest.modifyFile(destFolder+list.get(0).getFileName());
		} catch (Exception e) {
			logger1.info("Modifying EOL characters from Windows to UNIX format!");
		}
		
		if(session.isConnected()){
			session.close();
		}
	}
	
	
	@AfterSuite
	public void closeConnection() {
		if (session != null) {
		logger1.info( "Closing DB Connection...");
		logger1.info( "DB Connection Succesfully closed!");
		session.close();
		}
		logger1.info( "Completed test execution...");
	}
	
	
	 private static void modifyFile(String fileName) throws IOException
	    {

	        Path path = Paths.get(fileName);
	        Charset charset = StandardCharsets.UTF_8;

	        String content = new String(Files.readAllBytes(path), charset);
	        content = content.replaceAll("\r\n", "\n");
	        content = content.replaceAll("\r", "\n");
	        Files.write(path, content.getBytes(charset));
	    }
}
