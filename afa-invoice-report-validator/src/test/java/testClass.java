import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;

import com.monitorjbl.xlsx.StreamingReader;

public class testClass {

	public static void main(String[] args) {
//		try {
//			InputStream is = new FileInputStream(
//					new File(
//							new File("").getAbsoluteFile()
//									+ "\\src\\test\\resources\\input\\BRD543709-AFA Claims Billing Invoice report.xlsx"));
//			Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
//			Sheet sheet = workbook.getSheetAt(0);
//			Sheet frontPage = workbook.getSheetAt(0);
//			for (int i = 1; i <= 21; i++) {
//				Row row = frontPage.getRow(i);
//				Cell cell = row.getCell(1);
//				
//				String celldata = getCellValue(cell);
//				if(celldata.equalsIgnoreCase("191480000075") ){
//					System.out.println(cell.getRowIndex()+"-- Invoice ID present here");
//				}else{
//					System.out.println(celldata+" -- Cell data");
//				}
//			}
//			
//			for (Row r : sheet) {
//				for (Cell c : r) {
//					System.out.println(c.getAddress());
//					System.out.println(getCellValue(c));
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
		String string = "Billing Category XXX";
		System.out.println(string.replace("Billing Category", ""));
//		}
	}
	
	private static String getCellValue(Cell cell) {
		String celldata = "";
	    switch (cell.getCellTypeEnum()) {
	        case STRING:
	            celldata = cell.getRichStringCellValue().getString().trim();
	            break;
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                celldata = cell.getDateCellValue().toString();
	            } else {
	            	BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
	            	celldata = bigDecimal.toEngineeringString().trim();
	            }
	            break;
	        default:
	            celldata = "";
	           
	    }

	   return celldata;
	
	}
}
