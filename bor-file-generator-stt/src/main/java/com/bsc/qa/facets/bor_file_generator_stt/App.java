package com.bsc.qa.facets.bor_file_generator_stt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.facets.bor_file_generator_stt.dao.DatabaseUtil;
import com.bsc.qa.facets.bor_file_generator_stt.pojo.BORFile;
import com.bsc.qa.facets.bor_file_generator_stt.pojo.Connection;
import com.bsc.qa.facets.bor_file_generator_stt.util.HibernateUtil;
import com.github.ffpojo.exception.FFPojoException;
import com.github.ffpojo.file.writer.FileSystemFlatFileWriter;
import com.github.ffpojo.file.writer.FlatFileWriter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws FFPojoException, IOException {
		DatabaseUtil util = new DatabaseUtil();
		AutomationStringUtilities decoder = new AutomationStringUtilities();
		SessionFactory sessionFactory = null;
		Session session;
		Logger logger1 = LoggerFactory.getLogger(App.class);
		String oracleUser = decoder.decryptValue(System.getenv("ORACLE_USER"));
		String oraclePassword = decoder.decryptValue(System.getenv("ORACLE_PASSWORD"));
		String oracleServer = System.getenv("ORACLE_SERVER");
		String oraclePort = System.getenv("ORACLE_PORT");
		
		String oracleDB = System.getenv("ORACLE_DB");
		String oracleUrl = "jdbc:oracle:thin:@" + oracleServer + ":" + oraclePort + ":" + oracleDB ;
		String destFolder = System.getenv("BOR_DEST_PATH");
		Connection conn = new Connection();
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		logger1.info("BOR File will be generated here --> "+destFolder);
		
		try {
			logger1.info("Connecting to Database...");
			logger1.info("Establishing DB connection with the URL - "+conn.getUrl());
			sessionFactory = HibernateUtil.createSessionFactory(conn);
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
		
		List<BORFile> list = util.getBorFileList(session,util.getBorFileListFromDB(session),util.getBorAdjustmentRecords(session));
		String filename = list.get(0).getFileName();
		
		File file = new File(destFolder+filename);
		System.out.println(file.getAbsolutePath());
		FlatFileWriter ffWriter = new FileSystemFlatFileWriter(file, true);
		
		ffWriter.writeRecordList(list);
		ffWriter.close();
		
		try {
			logger1.info("Modifying EOL characters from Windows to UNIX format!");
			logger1.info("Modifying file - " + destFolder+list.get(0).getFileName());
			App.modifyFile(destFolder+list.get(0).getFileName());
		} catch (Exception e) {
			logger1.info("Modifying EOL characters from Windows to UNIX format!");
		}
		
		if(session.isConnected()){
			session.close();
		}
		System.exit(0);

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
