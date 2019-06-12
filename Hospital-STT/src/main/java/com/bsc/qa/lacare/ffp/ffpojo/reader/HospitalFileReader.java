package com.bsc.qa.lacare.ffp.ffpojo.reader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bsc.qa.lacare.db.DatabaseQueries;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospBody;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospHeader;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospTrailer;
import com.github.ffpojo.file.reader.FileSystemFlatFileReader;
import com.github.ffpojo.file.reader.FlatFileReader;
import com.github.ffpojo.file.reader.FlatFileReaderDefinition;
import com.github.ffpojo.file.reader.RecordType;

/**
 * @author jgupta03
 *
 */
public class HospitalFileReader {

	String dir = "\\\\bsc/it/VDI_Home_SAC/plambh01/My Documents/LA Care PIMS Validation/inputfiles";
	public String file = lastFileModified(dir).getAbsolutePath();
	//public String file = "\\\\bsc\\it\\VDI_Home_SAC\\plambh01\\Desktop\\TCFST201906.HOSP.txt";
	FlatFileReader ffReader = null;
	File inputFile = new File(file);
	List<HospBody> list;
	static Logger logger = LogManager.getLogger(HospitalFileReader.class);
	// String filename = new File("").getAbsolutePath() + "\\input\\Input.xlsx";
	// String filename = UI.INPUT_EXCEL_FILE_PATH;

	List<HospBody> inputBodyList = new ArrayList();
	public List<String> fileList = new ArrayList();

	

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	public HospHeader getHospitalHeaderData() {
		HospHeader header = null;
		FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(
				HospBody.class);
		ffDefinition.setHeader(HospHeader.class);
		ffDefinition.setTrailer(HospTrailer.class);
		FlatFileReader ffReader = null;
		try {
			ffReader = new FileSystemFlatFileReader(inputFile, ffDefinition);

			for (Object record : ffReader) {
				RecordType recordType = ffReader.getRecordType();

				if (recordType == RecordType.HEADER) {
					header = (HospHeader) record;

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return header;
	}

	public HospTrailer getHospitalTrailerData() {
		HospTrailer trailer = null;
		FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(
				HospBody.class);
		ffDefinition.setHeader(HospHeader.class);
		ffDefinition.setTrailer(HospTrailer.class);
		FlatFileReader ffReader = null;
		try {
			ffReader = new FileSystemFlatFileReader(inputFile, ffDefinition);
			for (Object record : ffReader) {
				RecordType recordType = ffReader.getRecordType();

				if (recordType == RecordType.TRAILER) {
					trailer = (HospTrailer) record;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return trailer;
	}

	public List<HospBody> getHospitalBodyData() {
		list = new ArrayList<HospBody>();
		FlatFileReaderDefinition ffDefinition = new FlatFileReaderDefinition(
				HospBody.class);
		ffDefinition.setHeader(HospHeader.class);
		ffDefinition.setTrailer(HospTrailer.class);
		try {
			ffReader = new FileSystemFlatFileReader(inputFile, ffDefinition);
			for (Object record : ffReader) {
				RecordType recordType = ffReader.getRecordType();

				if (recordType == RecordType.BODY) {
					HospBody body = (HospBody) record;
					list.add(body);
				}
			}
			if(list.isEmpty())
				throw new Exception("Detailed Recored not found in the file"+inputFile.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(inputFile + "file not found");
		} catch (IllegalArgumentException e) {
			System.out.println(inputFile + "file not found");
			// TODO: handle exception
		} catch (Exception e) {
			logger.error(e);
		}

		return list;
	}

}
