package com.bsc.qa.facets.afa_invoice_report_validator;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Map<String, String> map = System.getenv();
    	for (Map.Entry<String, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() +" : " +entrySet.getValue());
		} 

    }
}
