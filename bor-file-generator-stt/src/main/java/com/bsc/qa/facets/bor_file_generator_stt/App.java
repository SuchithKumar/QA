package com.bsc.qa.facets.bor_file_generator_stt;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
		SessionFactory sessionFactory ;
		Session session;
		String oracleUser = decoder.decryptValue(System.getenv("ORACLE_USER"));
		String oraclePassword = decoder.decryptValue(System.getenv("ORACLE_PASSWORD"));
		String oracleServer = System.getenv("ORACLE_SERVER");
		String oraclePort = System.getenv("ORACLE_PORT");
		String oracleDB = System.getenv("ORACLE_DB");
		String oracleUrl = "jdbc:oracle:thin:@" + oracleServer + ":" + oraclePort + ":" + oracleDB ;
		
		Connection conn = new Connection();
		conn.setUsername(oracleUser);
		conn.setPassword(oraclePassword);
		conn.setUrl(oracleUrl);
		sessionFactory = HibernateUtil.createSessionFactory(conn);
		session = sessionFactory.openSession();
		
		List<BORFile> list = util.getBorFileList(session,util.getBorFileListFromDB(session),util.getBorAdjustmentRecords(session));
		String filename = list.get(0).getFileName();
		File file = new File(new File("").getAbsolutePath()+"\\Output files\\"+filename);
		FlatFileWriter ffWriter = new FileSystemFlatFileWriter(file, true);
		ffWriter.writeRecordList(list);
		ffWriter.close();
		
		if(session.isConnected()){
			session.close();
		}
		System.exit(0);

	}
}
