package com.bsc.qa.lacare.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 */

/**
 * @author plambh01
 * @date May 22, 2019
 * 
 */
public class FileUtil {

	public static void main(String[] args) {
		String dest = "\\bsc\\it\\VDI_Home_SAC\\plambh01\\My Documents\\Automation git\\Hospital-STT\\Output\\";
		File file = new File(dest + "Report.html");
		//zipFile(file, dest+"ab.zip");
	
	}

	public static void zipFile(File inputFile,File excelfile, String zipFilePath) {
		try {

			// Wrap a FileOutputStream around a ZipOutputStream
			// to store the zip stream to a file. Note that this is
			// not absolutely necessary
			FileOutputStream fileOutputStream = new FileOutputStream(
					zipFilePath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(
					fileOutputStream);

			// a ZipEntry represents a file entry in the zip archive
			// We name the ZipEntry after the original file's name
			ZipEntry zipEntry = new ZipEntry(inputFile.getName());
			ZipEntry zipEntry2=new ZipEntry(excelfile.getName());
			
			zipOutputStream.putNextEntry(zipEntry);
			zipOutputStream.putNextEntry(zipEntry2);

			FileInputStream fileInputStream = new FileInputStream(inputFile);
			FileInputStream fileInputStream2= new FileInputStream(excelfile);
			byte[] buf = new byte[1024];
			int bytesRead;
			byte[] buf1 = new byte[1024];
			int bytesRead1;

			// Read the input file by chucks of 1024 bytes
			// and write the read bytes to the zip stream
			while ((bytesRead = fileInputStream.read(buf)) > 0) {
				zipOutputStream.write(buf, 0, bytesRead);
			}
			fileInputStream.close();
			while ((bytesRead1 = fileInputStream2.read(buf1)) > 0) {
				zipOutputStream.write(buf1, 0, bytesRead1);
			}
			fileInputStream2.close();
			// close ZipEntry to store the stream to the file
			zipOutputStream.closeEntry();

			zipOutputStream.close();
			fileOutputStream.close();
		
			
			System.out.println("Regular file :" + inputFile.getCanonicalPath()
					+ " is zipped to archive :" + zipFilePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void zipHtmlFile(File inputFile, String zipFilePath) {
		try {

			// Wrap a FileOutputStream around a ZipOutputStream
			// to store the zip stream to a file. Note that this is
			// not absolutely necessary
			FileOutputStream fileOutputStream = new FileOutputStream(
					zipFilePath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(
					fileOutputStream);

			// a ZipEntry represents a file entry in the zip archive
			// We name the ZipEntry after the original file's name
			ZipEntry zipEntry = new ZipEntry(inputFile.getName());
			
			zipOutputStream.putNextEntry(zipEntry);

			FileInputStream fileInputStream = new FileInputStream(inputFile);
			byte[] buf = new byte[1024];
			int bytesRead;

			// Read the input file by chucks of 1024 bytes
			// and write the read bytes to the zip stream
			while ((bytesRead = fileInputStream.read(buf)) > 0) {
				zipOutputStream.write(buf, 0, bytesRead);
			}

			// close ZipEntry to store the stream to the file
			zipOutputStream.closeEntry();

			zipOutputStream.close();
			fileOutputStream.close();
			fileInputStream.close();
			System.out.println("Regular file :" + inputFile.getCanonicalPath()
					+ " is zipped to archive :" + zipFilePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void zipFiles(List<String> files ,String zipname){
        
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(System.getProperty("user.dir")+"\\Output\\"+zipname+".zip");
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for(String filePath:files){
                File input = new File(filePath);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                System.out.println("Zipping the file: "+input.getName());
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4*1024];
                int size = 0;
                while((size = fis.read(tmp)) != -1){
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            System.out.println("Done... Zipped the files...");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try{
                if(fos != null) fos.close();
            } catch(Exception ex){
                 
            }
        }
    }
}
