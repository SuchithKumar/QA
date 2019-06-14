package com.bsc.qa.facets.afa.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QueriesUtil {
	
	public Map<String, String> queriesMap(){
		File file = new File(new File("").getAbsolutePath()+"\\src\\test\\resources\\bor-queries.csv");
		Map<String, String> map=new HashMap<String, String>();
		List<List<String>> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(file);) {
			while (scanner.hasNextLine()) {
				records.add(getRecordFromLine(scanner.nextLine()));
			}
			for (List<String> list : records) {
				map.put(list.get(0), list.get(1));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	public List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter("-");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());	
			}
			
		}
		return values;
	}
	
}
