package com.bsc.qa.facets.afa_invoice_report_validator;

import java.util.Map;

import com.bsc.bqsa.AutomationStringUtilities;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println(System.getenv("ORACLE_USER"));
    	Map<String, String> map = System.getenv();
    	AutomationStringUtilities util = new AutomationStringUtilities();
    	System.out.println(util.decryptValue(System.getenv("ORACLE_USER")));
    	for (Map.Entry<String, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() +" : " +entrySet.getValue());
		} 

    }
}
